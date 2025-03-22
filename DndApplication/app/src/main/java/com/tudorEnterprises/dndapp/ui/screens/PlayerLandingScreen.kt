package com.tudorEnterprises.dndapp.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.tudorEnterprises.dndapp.ui.dialogs.CreateCampaignDialog
import com.tudorEnterprises.dndapp.ui.navigation.GetAppBarTopLoggedIn
import com.tudorEnterprises.dndapp.ui.navigation.GetBottomAppBar
import com.tudorEnterprises.dndapp.ui.theme.DndApplicationTheme

@Composable
fun PlayerLandingScreen(navController: NavController) {
    DndApplicationTheme {

        var showDialog by remember { mutableStateOf(false) }
        var playerName by remember { mutableStateOf("") }

        LaunchedEffect(Unit) {
//            while (true) {
//                CampaignRefreshService(context, sql).fetchFromServerAndUpdatedDb()
//                delay(5000)
//            }
        }

        LaunchedEffect(Unit) {
//            sql.getAllCampaigns().collect { campaigns ->
//                Log.d("DMLandingScreen", "Campaign list updated: ${campaigns.size}")
//            }
        }

        Scaffold(topBar = {
            GetAppBarTopLoggedIn(navController)
        }, bottomBar = {
            GetBottomAppBar("Test")
        }) { innerPadding ->
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
                    text = "Player",
                )
                LazyColumn(
                    modifier = Modifier.weight(1f).fillMaxWidth()
                ) {
                    //TODO fill with players from DB
                    item {
                        Spacer(modifier = Modifier.height(16.dp))
                    }
                }
                Column(
                    Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    CreateCampaignButton { showDialog = true } //TODO create player button
                }
            }
        }
        if (showDialog) {
            CreateCampaignDialog(
                onDismiss = { showDialog = false },
                onConfirm = { enteredName ->
                    playerName = enteredName
                    showDialog = false
                    //launchCampaignCreation(campaignName, context, sql) //TODO create private fun to launch Player creation
                }
            )
        }
    }
}

@Preview
@Composable
private fun GetScreen() {
    val navController = rememberNavController()
    PlayerLandingScreen(navController)
}