package com.tudorEnterprises.dndapp.ui.screens

import android.content.Context
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.tudorEnterprises.dndapp.constants.UserRole
import com.tudorEnterprises.dndapp.dataStorage.CampaignCharacterSqlActivity
import com.tudorEnterprises.dndapp.dataStorage.InventorySqlActivity
import com.tudorEnterprises.dndapp.dataStorage.tables.InventoryItemData
import com.tudorEnterprises.dndapp.networking.InventoryHttp
import com.tudorEnterprises.dndapp.objects.InventoryItem
import com.tudorEnterprises.dndapp.services.GenericRefreshService
import com.tudorEnterprises.dndapp.ui.dialogs.CreateInventoryItemDialog
import com.tudorEnterprises.dndapp.ui.navigation.GetAppBarTopLoggedIn
import com.tudorEnterprises.dndapp.ui.navigation.GetBottomAppBar
import com.tudorEnterprises.dndapp.ui.navigation.GetInventoryButton
import com.tudorEnterprises.dndapp.ui.theme.DndApplicationTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.time.Instant

@Composable
fun GetCampaignInventoryScreen(
    id: Int,
    navController: NavController,
    name: String,
    userRole: String,
    playerCampaignId: Int? = null // This is used when the user is a player, to get their specific campaign inventory
) {

    val context = LocalContext.current
    val characterSql = CampaignCharacterSqlActivity(context) //to get characters for the campaign
    val inventorySql = InventorySqlActivity(context) //to get inventory items for the campaign

    DndApplicationTheme {
        var showDialog by remember { mutableStateOf(false) }
        var inventoryItem by remember { mutableStateOf(InventoryItem()) }

        // Get the inventory items for the campaign or player - this needs to be able to handle campaignId and playerId when playerId is null
        // when userRole is DUNGEON_MASTER, we assume ID is the campaign ID, otherwise it is the player ID, and we need to pass in the campaignId separately

        //pass in playerCampaignId if present, otherwise it will be null

        val inventory by inventorySql.getInventoryItemsById(id, userRole, playerCampaignId)
            .collectAsStateWithLifecycle(initialValue = emptyList())

        if( userRole == UserRole.DUNGEON_MASTER.role) {

            LaunchedEffect(Unit) {
                while (true) {
                    GenericRefreshService(context).fetchAndUpdateCampaignCharacters(
                        characterSql,
                        id
                    )
                    delay(5000)
                }
            }
        }

        //pass in playerCampaignId if present, otherwise it will be null

        LaunchedEffect(Unit) {
            while (true) {
                GenericRefreshService(context).fetchAndUpdateInventoryItems(
                    inventorySql,
                    id,
                    userRole,
                    playerCampaignId
                )
                delay(5000)
            }
        }

        Scaffold(
            topBar = { GetAppBarTopLoggedIn(navController) },
            bottomBar = { GetBottomAppBar() }
        ) {innerPadding ->
            Column(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxWidth()
                    .fillMaxHeight(),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.padding(top = 16.dp),
                    text = "Inventory for $name",
                )

                LazyColumn(
                    modifier = Modifier
                        .weight(1f) // Take up available space
                        .fillMaxWidth()
                ) {
                    items(inventory) { item ->
                        GetInventoryButton(
                            item,
                            { deleteInventoryItem(item, inventorySql, context) },
                            navController,
                            userRole, // Enable delete button for DM
                        )
                    }

                    // Use an item in LazyColumn to add spacing
                    item {
                        Spacer(modifier = Modifier.height(16.dp))
                    }
                }

                // CreateInventoryItemButton at the bottom of the screen
                Column(
                    Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    if( userRole == UserRole.DUNGEON_MASTER.role) {
                        CreateInventoryItemButton { showDialog = true }
                    }
                }
            }

        }

        if (showDialog) {
            CreateInventoryItemDialog(
                onDismiss = { showDialog = false },
                onConfirm = { enteredItem ->
                    inventoryItem = enteredItem
                    showDialog = false
                    launchInventoryItemCreation(inventoryItem, context, inventorySql, id)
                    Log.d("CreateInventoryItemDialog", "Item created: ${inventoryItem.name}")
                }
            )
        }

    }

}

@Composable
fun CreateInventoryItemButton(onClick: () -> Unit) {

    ElevatedButton(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 4.dp, bottom = 4.dp, start = 30.dp, end = 30.dp)
            .height(60.dp),
        shape = RoundedCornerShape(25),
        elevation = ButtonDefaults.elevatedButtonElevation(3.dp),
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(4.dp),
        ) {
            Row {
                Text(
                    text = "Create Inventory Item",
                    style = MaterialTheme.typography.headlineMedium,
                )
            }
        }
    }
}

private fun launchInventoryItemCreation(
    inventoryItem: InventoryItem,
    context: Context,
    sql: InventorySqlActivity,
    campaignId: Int
) {

    Log.d("CampaignInventory", "Launching inventory item creation for: ${inventoryItem.name}")

    CoroutineScope(Dispatchers.IO).launch {
        val updateTime = Instant.now().epochSecond

        val syncItemId = InventoryHttp(context).createNewItem(
            campaignId,
            inventoryItem,
            updateTime
        )

        if( syncItemId != 0) {
            sql.checkAndInsertInventoryItem(campaignId, inventoryItem.name, inventoryItem.description, inventoryItem.detail, syncItemId, updateTime)
        }

    }
}

private fun deleteInventoryItem(
    item: InventoryItemData,
    sql: InventorySqlActivity,
    context: Context
) {
    CoroutineScope(Dispatchers.IO).launch {
        if (InventoryHttp(context).deleteItem(item.itemId)) {
            sql.deleteItemById(item.itemId)
            Log.d("CampaignInventory", "Deleted inventory item with ID: ${item.itemId}")
        }
    }
}