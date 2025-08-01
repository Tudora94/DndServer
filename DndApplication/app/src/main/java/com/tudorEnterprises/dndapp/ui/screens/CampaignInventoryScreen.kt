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
import com.tudorEnterprises.dndapp.dataStorage.CampaignCharacterSqlActivity
import com.tudorEnterprises.dndapp.dataStorage.CampaignSqlActivity
import com.tudorEnterprises.dndapp.objects.InventoryItem
import com.tudorEnterprises.dndapp.services.GenericRefreshService
import com.tudorEnterprises.dndapp.ui.dialogs.CreateInventoryItemDialog
import com.tudorEnterprises.dndapp.ui.navigation.GetAppBarTopLoggedIn
import com.tudorEnterprises.dndapp.ui.navigation.GetBottomAppBar
import com.tudorEnterprises.dndapp.ui.navigation.GetInventoryButton
import com.tudorEnterprises.dndapp.ui.theme.DndApplicationTheme
import kotlinx.coroutines.delay
import java.time.Instant

@Composable
fun GetCampaignInventoryScreen(
    campaignId: Int,
    navController: NavController,
    campaignName: String
) {

    val context = LocalContext.current
    val campaignSql = CampaignSqlActivity(context) //to get campaign details if needed - name most likely
    val characterSql = CampaignCharacterSqlActivity(context) //to get characters for the campaign

    DndApplicationTheme {
        var showDialog by remember { mutableStateOf(false) }
        var inventoryItem by remember { mutableStateOf(InventoryItem()) }

        val inventoryItems = mutableListOf<InventoryItem>() //TODO replace with actual inventory items from the database


        val characters by characterSql.getPlayersForCampaign(campaignId)
            .collectAsStateWithLifecycle(initialValue = emptyList())

        LaunchedEffect(Unit) {
            while (true) {
                GenericRefreshService(context).fetchAndUpdateCampaignCharacters(characterSql, campaignId)
                delay(5000)
            }
        }

        Scaffold(
            topBar = { GetAppBarTopLoggedIn(navController) },
            bottomBar = { GetBottomAppBar("Test") }
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
                    text = "Inventory for $campaignName",
                )

                LazyColumn(
                    modifier = Modifier
                        .weight(1f) // Take up available space
                        .fillMaxWidth()
                ) {
                    items(inventoryItems) { campaignName ->
                        GetInventoryButton(
                            inventoryItem.name,
                            { },
                            navController,
                            true
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
                    CreateInventoryItemButton { showDialog = true }
                }
            }

        }

        if (showDialog) {
            CreateInventoryItemDialog(
                onDismiss = { showDialog = false },
                onConfirm = { enteredItem ->
                    inventoryItem = enteredItem
                    showDialog = false
//                    launchInventoryItemCreation(inventoryItem, context, sql) //TODO: Implement this function to handle the creation of the inventory item and create inventory database
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
    sql: CampaignCharacterSqlActivity
) {

    val updateTime = Instant.now().epochSecond



    Log.d("CampaignInventory", "Launching inventory item creation for: ${inventoryItem.name}")
}