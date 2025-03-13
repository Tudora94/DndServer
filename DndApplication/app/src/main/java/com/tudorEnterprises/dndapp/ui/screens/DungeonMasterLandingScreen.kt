package com.tudorEnterprises.dndapp.ui.screens

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.tudorEnterprises.dndapp.ui.dialogs.CreateCampaignDialog
import com.tudorEnterprises.dndapp.ui.navigation.GetAppBarTopLoggedIn
import com.tudorEnterprises.dndapp.ui.navigation.GetBottomAppBar
import com.tudorEnterprises.dndapp.ui.theme.DndApplicationTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun DMLandingScreen(navController: NavController) {

    //TODO setup call to sqlLite DB to check for stored campaigns and make call to online thing async

    DndApplicationTheme {
        var showDialog by remember { mutableStateOf(false) }
        var campaignName by remember { mutableStateOf("") }

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
                    text = "Dungeon Master",
                )
                Column(
                    modifier = Modifier
                        .verticalScroll(rememberScrollState())
//                        .weight(0.1f, false)
                ) {

                }
                Spacer(modifier = Modifier.weight(10f))
                Column(
                    Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    CreateCampaignButton {showDialog = true}
                }
                Spacer(modifier = Modifier.padding(16.dp))
            }
        }

        if (showDialog) {
            CreateCampaignDialog(
                onDismiss = { showDialog = false },
                onConfirm = { enteredName ->
                    campaignName = enteredName
                    showDialog = false
                    launchCampaignCreation(campaignName)
                }
            )
        }
    }
}

@Composable
fun CreateCampaignButton(onClick: () -> Unit) {

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
                    text = "Create Campaign",
                    style = MaterialTheme.typography.headlineMedium,
                )
            }
        }
    }
}

private fun launchCampaignCreation(campaignName: String) {
    CoroutineScope(Dispatchers.IO).launch {
        // Simulate API call (replace with real API call)
        delay(1000)
        Log.d("DMLandingScreen", "Campaign Created: $campaignName")
    }
}


@Preview
@Composable
private fun GetDMScreen() {
    val navController = rememberNavController()
    DMLandingScreen(navController)
}