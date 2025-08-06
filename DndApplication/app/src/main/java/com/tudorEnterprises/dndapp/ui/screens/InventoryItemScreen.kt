package com.tudorEnterprises.dndapp.ui.screens

import android.content.Context
import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.tudorEnterprises.dndapp.constants.UserRole
import com.tudorEnterprises.dndapp.dataStorage.CampaignCharacterSqlActivity
import com.tudorEnterprises.dndapp.dataStorage.InventorySqlActivity
import com.tudorEnterprises.dndapp.dataStorage.tables.CampaignCharactersData
import com.tudorEnterprises.dndapp.dataStorage.tables.InventoryItemData
import com.tudorEnterprises.dndapp.networking.InventoryHttp
import com.tudorEnterprises.dndapp.ui.navigation.GetAppBarTopLoggedIn
import com.tudorEnterprises.dndapp.ui.navigation.GetBottomAppBar
import com.tudorEnterprises.dndapp.ui.theme.DndApplicationTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.Instant

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GetInventoryItemScreen(
    inventoryItemId: Int,
    campaignId: Int? = null, // Default to null if not provided, i.e. when a player accesses the screen
//    inventoryName: String,
//    inventoryItemDescription: String,
//    inventoryItemDetail: String,
    userRole: String,
    navController: NavController,
    characterId: Int? = null //this is used when the item is assigned to a character, otherwise it will be null
) {
    //THIS PAGE IS NOT REAL TIME SO NO LAUNCHED EFFECTS ARE NEEDED
    val context = LocalContext.current
    val characterSql = CampaignCharacterSqlActivity(context)
    val inventorySql = InventorySqlActivity(context) //to get inventory items for the campaign

    DndApplicationTheme {

        var expanded by remember { mutableStateOf(false) }
        var selectedOption by remember { mutableStateOf(-1) }

        val inventory by inventorySql.getInventoryItemById(inventoryItemId)
            .collectAsStateWithLifecycle(initialValue = InventoryItemData(
                itemId = 0,
                itemName = "",
                description = "",
                detail = "",
                characterId = null,
                campaignId = campaignId ?: 0,
                updateTime = 0
            ))


        Scaffold(
            topBar = { GetAppBarTopLoggedIn(navController) },
            bottomBar = { GetBottomAppBar() }
        ) { innerPadding ->
            Column(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxWidth()
                    .fillMaxHeight(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
//                Column(
//                    modifier = Modifier.fillMaxWidth().padding(horizontal = 8.dp),
//                    horizontalAlignment = Alignment.Start
//                ) {
//                    Text(
//                        text = "Inventory Item",
//                        style = MaterialTheme.typography.labelLarge,
//                        modifier = Modifier.padding(top = 8.dp)
//                    )
//                }
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp),
                ) {
                    Text(
                        text = "Inventory Item",
                        style = MaterialTheme.typography.titleLarge,
                        modifier = Modifier.padding(top = 16.dp)
                    )
                }
                if (userRole == UserRole.DUNGEON_MASTER.role) //will only display if the user is a DM
                {
                    // Fetch the list of characters for the campaign
                    val characters by characterSql.getPlayersForCampaign(campaignId ?: 0)
                        .collectAsStateWithLifecycle(initialValue = emptyList())


                    val charactersWithDefault = listOf(
                        CampaignCharactersData(
                            playerId = null, // -1 is used as a default value for "None" in the dropdown
                            characterName = "None",
                            id = -1,
                            userId = "System",
                            campaignId = campaignId
                        )
                    ) + characters

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Assigned to:",
                            maxLines = 1,
                            modifier = Modifier
                                .weight(5f),
                            style = MaterialTheme.typography.headlineMedium,
                            overflow = TextOverflow.Ellipsis
                        )
                        Spacer(modifier = Modifier.weight(1f))

                        ExposedDropdownMenuBox(
                            expanded = expanded,
                            onExpandedChange = { expanded = !expanded },
                            modifier = Modifier.weight(6f)
                        ) {
                            TextField(
                                value = getCurrentCharacterName(characterId, selectedOption,  charactersWithDefault) ?: "None", //TODO this needs to check selectedOption first, if null then use function to identify it
                                onValueChange = {},
                                readOnly = true,
                                label = { Text("Select player") },
                                trailingIcon = {
                                    ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
                                },
                                modifier = Modifier.menuAnchor()
                            )

                            ExposedDropdownMenu(
                                expanded = expanded,
                                onDismissRequest = { expanded = false }
                            ) {
                                charactersWithDefault.forEach { option ->
                                    DropdownMenuItem(
                                        text = { Text(option.characterName ?: "None") },
                                        onClick = {
                                            selectedOption = option.playerId ?: 0 // -1 is used as a default value for "None" in the dropdown
                                            expanded = false
                                            onItemSelected(
                                                option.playerId,
                                                inventoryItemId,
                                                campaignId ?: 0,
                                                context,
                                                inventorySql
                                            )
                                        }
                                    )
                                }
                            }

                        }
                    }
                }
                LazyColumn(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth()
                ) {
                    item {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 8.dp),
                            horizontalAlignment = Alignment.Start
                        ) {
                            Text(
                                text = "Item Name",
                                style = MaterialTheme.typography.labelLarge,
                                modifier = Modifier.padding(top = 8.dp, bottom = 8.dp)
                            )

                            Text(
                                text = inventory?.itemName ?: "",
                                style = MaterialTheme.typography.bodyMedium,
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                            )
                            Spacer(modifier = Modifier.height(16.dp))
                        }
                    }
                    item {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 8.dp),
                            horizontalAlignment = Alignment.Start
                        ) {
                            Text(
                                text = "Item Description",
                                style = MaterialTheme.typography.labelLarge,
                                modifier = Modifier.padding(top = 8.dp, bottom = 8.dp)
                            )

                            Text(
                                text = inventory?.description ?: "",
                                style = MaterialTheme.typography.bodyMedium,
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                            )
                            Spacer(modifier = Modifier.height(16.dp))
                        }
                    }
                    item {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 8.dp),
                            horizontalAlignment = Alignment.Start
                        ) {
                            Text(
                                text = "Item Details",
                                style = MaterialTheme.typography.labelLarge,
                                modifier = Modifier.padding(top = 8.dp, bottom = 8.dp)
                            )

                            Text(
                                text = parseStyledText(inventory?.detail?:""),     //inventory?.detail?.replace("\\n", "\n") ?: "",
                                style = MaterialTheme.typography.bodyMedium,
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                            )
                            Spacer(modifier = Modifier.height(16.dp))
                        }
                    }
                }

            }
        }

    }
}

private fun onItemSelected(
    playerId: Int?,
    itemId: Int,
    campaignId: Int,
    context: Context,
    sql: InventorySqlActivity
) {
    // Handle the selection of the item
    // This function can be used to update the state or perform an action based on the selected item
    Log.d(
        "InventoryItemScreen",
        "Selected character ID: $playerId for item ID: $itemId, campaign ID: $campaignId"
    )

    CoroutineScope(Dispatchers.IO).launch {
        val updateTime = Instant.now().epochSecond

        try {
            val success = InventoryHttp(context).assignItemToPlayer(
                itemId = itemId,
                playerId = playerId,
                campaignId = campaignId,
                updateTime = updateTime
            )


        if (success) {
            //update playerId in sql
            sql.assignItemToPlayer(
                itemId = itemId,
                campaignId = campaignId,
                characterId = playerId,
                updateTime = updateTime
            )
        }
        } catch (exception: Exception) {
            Log.e("InventoryItemScreen", "Error assigning item to player: ${exception.message}")
        }
    }
}

private fun getCurrentCharacterName(
    characterId: Int?,
    selectedOption: Int,
    characters: List<CampaignCharactersData>
) : String? {
    Log.d("GetCurrentCharacterName", "characterId: $characterId, selectedOption: $selectedOption, characters size: ${characters.size}")
    if((characterId == null && selectedOption == -1) || characterId == -1)  // -1 is used as a default value for "None" in the dropdown
    {
        return null
    }
    if( selectedOption != -1) {
        return characters.find { it.playerId == selectedOption }?.characterName ?: "None"

    }
    return characters.find { it.playerId == characterId }?.characterName ?: "None"
}

fun parseStyledText(input: String): AnnotatedString {
    val builder = AnnotatedString.Builder()
    var index = 0

    while (index < input.length) {
        when {
            input.startsWith("<b>", index) -> {
                val end = input.indexOf("</b>", index)
                if (end != -1) {
                    val boldText = input.substring(index + 3, end)
                    builder.withStyle(SpanStyle(fontWeight = FontWeight.Bold)) {
                        append(boldText)
                    }
                    index = end + 4
                } else {
                    builder.append("<b>")
                    index += 3
                }
            }

            input.startsWith("<i>", index) -> {
                val end = input.indexOf("</i>", index)
                if (end != -1) {
                    val italicText = input.substring(index + 3, end)
                    builder.withStyle(SpanStyle(fontStyle = FontStyle.Italic)) {
                        append(italicText)
                    }
                    index = end + 4
                } else {
                    builder.append("<i>")
                    index += 3
                }
            }

            input.startsWith("\\n", index) -> {
                builder.append("\n")
                index += 2
            }

            else -> {
                builder.append(input[index])
                index++
            }
        }
    }

    return builder.toAnnotatedString()
}