package com.tudorEnterprises.dndapp.ui.screens

import android.content.Context
import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.tudorEnterprises.dndapp.constants.Screen
import com.tudorEnterprises.dndapp.constants.UserRole
import com.tudorEnterprises.dndapp.dataStorage.CampaignCharacterSqlActivity
import com.tudorEnterprises.dndapp.dataStorage.CampaignSqlActivity
import com.tudorEnterprises.dndapp.dataStorage.tables.CampaignNameData
import com.tudorEnterprises.dndapp.networking.CampaignHttp
import com.tudorEnterprises.dndapp.services.GenericRefreshService
import com.tudorEnterprises.dndapp.ui.navigation.GetAppBarTopLoggedIn
import com.tudorEnterprises.dndapp.ui.navigation.GetBottomAppBar
import com.tudorEnterprises.dndapp.ui.theme.DndApplicationTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun GetCampaignBaseScreen(navController: NavController, campaignId: Int) {
    val context = LocalContext.current
    val campaignSql = CampaignSqlActivity(context)
    val characterSql = CampaignCharacterSqlActivity(context)

    DndApplicationTheme {

        val campaign by campaignSql.getCampaignByIdFlow(campaignId)
            .collectAsStateWithLifecycle(initialValue = CampaignNameData(""))

        var roomCode by remember { mutableStateOf("") }

        val characters by characterSql.getPlayersForCampaign(campaignId)
            .collectAsStateWithLifecycle(initialValue = emptyList())

        fun setRoomCode() {
            CoroutineScope(Dispatchers.IO).launch {
                roomCode = ""
                roomCode = getRoomCode(context, campaignId)
            }
        }

        LaunchedEffect(Unit) {
            while (true) {
                GenericRefreshService(context).fetchAndUpdateCampaignCharacters(characterSql, campaignId)
                delay(5000)
            }
        }

        Scaffold(
            topBar = { GetAppBarTopLoggedIn(navController) },
            bottomBar = { GetBottomAppBar("Test") }
        ) { innerPadding ->
            Column(
                modifier = Modifier.padding(innerPadding).fillMaxWidth().fillMaxHeight(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
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
                        text = campaign.campaignName,
                        maxLines = 1,
                        modifier = Modifier
                            .weight(5f),
                        style = MaterialTheme.typography.headlineMedium,
                        overflow = TextOverflow.Ellipsis
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    ElevatedButton(
                        onClick = { },
                        modifier = Modifier.size(width = 108.dp, height = 40.dp)
                    ) {
                        Text(text = "Edit")
                    }
                }
                Column(
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 8.dp),
                    horizontalAlignment = Alignment.Start
                ) {
                    Text(
                        text = "Room code",
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
                        text = roomCode,
                        maxLines = 1,
                        modifier = Modifier
                            .weight(5f),
                        style = MaterialTheme.typography.headlineMedium,
                        overflow = TextOverflow.Ellipsis
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    ElevatedButton(
                        onClick = { setRoomCode() },
                        modifier = Modifier.size(width = 108.dp, height = 40.dp)
                    ) {
                        Text(text = "Generate")
                    }
                }
                Column(
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 8.dp),
                    horizontalAlignment = Alignment.Start
                ) {
                    Text(
                        text = "Players",
                        style = MaterialTheme.typography.labelLarge,
                        modifier = Modifier.padding(top = 8.dp)
                    )
                }
                Box(
                    modifier = Modifier
                        .height(160.dp) // Fixed height for the LazyColumn
                        .fillMaxWidth()
                        .padding(top = 8.dp)

                ) {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                    ) {
                        items(characters) { character ->
                            Text(
                                text = character.characterName.toString(),
                                modifier = Modifier.padding(16.dp),
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }
                    }
                }
                ElevatedButton(
                    onClick = {navController.navigate(Screen.CampaignInventory.route + "/${campaignId}/${campaign.campaignName}/${UserRole.DUNGEON_MASTER.role}")},
                    modifier = Modifier.fillMaxWidth().padding(start = 8.dp, top = 16.dp, end = 8.dp)
                ) {
                    Text(text = "Inventory")
                }
            }
        }
    }
}

private suspend fun getRoomCode(context: Context, campaignId: Int) : String {
    var response : String? = ""
    try {
        response = CampaignHttp(context).getRoomCode(campaignId)
    } catch (ex: Exception) {
        Log.d("CampaignBaseScreen", "exception calling roomcode: $ex")
    }
    if(response == null)
        return ""
    return response
}


@Preview
@Composable
private fun GEtCampaignScreen() {
    val navController = rememberNavController()
    GetCampaignBaseScreen(navController, 1)
}