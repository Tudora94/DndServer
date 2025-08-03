package com.tudorEnterprises.dndapp.ui.screens

import android.content.Context
import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.tudorEnterprises.dndapp.constants.Screen
import com.tudorEnterprises.dndapp.constants.UserRole
import com.tudorEnterprises.dndapp.dataModels.responses.Player
import com.tudorEnterprises.dndapp.dataStorage.CharacterSqlActivity
import com.tudorEnterprises.dndapp.dataStorage.tables.CharacterNameData
import com.tudorEnterprises.dndapp.networking.CharacterHttp
import com.tudorEnterprises.dndapp.ui.dialogs.JoinCampaignDialog
import com.tudorEnterprises.dndapp.ui.navigation.GetAppBarTopLoggedIn
import com.tudorEnterprises.dndapp.ui.navigation.GetBottomAppBar
import com.tudorEnterprises.dndapp.ui.theme.DndApplicationTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.Instant

@Composable
fun GetCharacterBaseScreen(navController: NavController, characterId: Int) {
    val context = LocalContext.current
    val characterSql = CharacterSqlActivity(context)

    DndApplicationTheme {

    var showDialog by remember { mutableStateOf(false) }
    var roomCode by remember { mutableStateOf("") }

    val character by characterSql.getCharacterByIdFlow(characterId)
        .collectAsStateWithLifecycle(initialValue = CharacterNameData(characterName = "", syncCharacterId = 0, userId = 0, updateTime = 0, campaignId = null))

    Scaffold(
        topBar = { GetAppBarTopLoggedIn(navController) },
        bottomBar = { GetBottomAppBar("Test") }
    ) { innerPadding ->
                Column(
                    modifier = Modifier.padding(innerPadding) .fillMaxWidth() .fillMaxHeight(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Column(
                        modifier = Modifier.fillMaxWidth().padding(horizontal = 8.dp),
                        horizontalAlignment = Alignment.Start
                    ) {
                        Text(
                            text = "Character Name",
                            style = MaterialTheme.typography.labelLarge,
                            modifier = Modifier.padding(top = 8.dp)
                        )
                    }
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = character.characterName,
                            maxLines = 1,
                            modifier = Modifier
                                .weight(5f),
                            style = MaterialTheme.typography.headlineMedium,
                            overflow = TextOverflow.Ellipsis
                        )
                        Spacer(modifier = Modifier.weight(1f))
                        ElevatedButton(
                            onClick = { },
                        ) {
                            Text(text = "Edit")
                        }
                    }
                    Column(
                        modifier = Modifier.fillMaxWidth().padding(horizontal = 8.dp),
                        horizontalAlignment = Alignment.Start
                    ) {
                        Text(
                            text = "Campaign Name",
                            style = MaterialTheme.typography.labelLarge,
                            modifier = Modifier.padding(top = 8.dp)
                        )
                    }
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = character.campaignName?:"",
                            maxLines = 1,
                            modifier = Modifier
                                .weight(5f),
                            style = MaterialTheme.typography.headlineMedium,
                            overflow = TextOverflow.Ellipsis
                        )
                        Spacer(modifier = Modifier.weight(1f))
                        ElevatedButton(
                            onClick = { showDialog = true },
                        ) {
                            Text(text = "Join")
                        }
                    }
                    ElevatedButton(
                        onClick = {
                            Log.d("CharacterBaseScreen", "Navigating to Campaign Inventory for characterId: ${character.campaignId}")
                            navController.navigate(Screen.CampaignInventory.route + "/${characterId}/${character.characterName}/${UserRole.PLAYER.role}?playerCampaignId=${character.campaignId}")},
                        modifier = Modifier.fillMaxWidth().padding(start = 8.dp, top = 16.dp, end = 8.dp)
                    ) {
                        Text(text = "Inventory")
                    }

                    //TODO add inventory button - Inventory should be new table containing character ID, ItemID, name, descr, detail, call should return list of items for characterID and campaignCharacterId
                }
    }
    if (showDialog) {
        JoinCampaignDialog(
            onDismiss = { showDialog = false },
            onConfirm = { enteredName ->
                roomCode = enteredName
                showDialog = false
                launchJoinCampaign(roomCode, context, characterSql, characterId, character.characterName)
            }
        )
    }
    }
}

private fun launchJoinCampaign(
    roomCode: String,
    context: Context,
    sql: CharacterSqlActivity,
    characterId: Int,
    characterName: String
) {
    CoroutineScope(Dispatchers.IO).launch {
        val updateTime = Instant.now().epochSecond

        val characterCampaignDetails = CharacterHttp(context).addCharacterToCampaign(updateTime, characterId, roomCode)
        if(characterCampaignDetails != null && characterCampaignDetails.success){
            //add name and ID to characterNameData
            val player = Player(characterId, characterCampaignDetails.campaignId, characterName, updateTime, characterCampaignDetails.campaignName)
            sql.upsertCharacter(player)
        }
    }
}

@Preview
@Composable
private fun GetCharacterScreen() {
    val navController = rememberNavController()
    GetCharacterBaseScreen(navController, 18)
}