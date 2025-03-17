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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.tudorEnterprises.dndapp.dataStorage.CampaignSqlActivity
import com.tudorEnterprises.dndapp.dataStorage.tables.CampaignNameData
import com.tudorEnterprises.dndapp.networking.CampaignHttp
import com.tudorEnterprises.dndapp.services.CampaignRefreshService
import com.tudorEnterprises.dndapp.ui.dialogs.CreateCampaignDialog
import com.tudorEnterprises.dndapp.ui.navigation.GetAppBarTopLoggedIn
import com.tudorEnterprises.dndapp.ui.navigation.GetBottomAppBar
import com.tudorEnterprises.dndapp.ui.navigation.GetCampaignButtons
import com.tudorEnterprises.dndapp.ui.theme.DndApplicationTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun DMLandingScreen(navController: NavController) {
    val context = LocalContext.current
    val sql = CampaignSqlActivity(context)

    DndApplicationTheme {
        var showDialog by remember { mutableStateOf(false) }
        var campaignName by remember { mutableStateOf("") }

        val campaigns by sql.getAllCampaigns()
            .collectAsStateWithLifecycle(initialValue = emptyList())

        LaunchedEffect(Unit) {
//            val workRequest = PeriodicWorkRequestBuilder<CampaignRefreshWorker>(5, TimeUnit.SECONDS).build()
//            WorkManager.getInstance(context).enqueue(workRequest)
            while (true) {
                CampaignRefreshService(context, sql).fetchFromServerAndUpdatedDb()
                delay(5000)
            }
        }

        LaunchedEffect(Unit) {
            sql.getAllCampaigns().collect { campaigns ->
                Log.d("DMLandingScreen", "Campaign list updated: ${campaigns.size}")
            }
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
                    text = "Dungeon Master",
                )

                // LazyColumn for handling large lists and keeping the button fixed
                LazyColumn(
                    modifier = Modifier
                        .weight(1f) // Take up available space
                        .fillMaxWidth()
                ) {
                    items(campaigns) { campaignName ->
                        GetCampaignButtons(
                            campaignName,
                            { deleteCampaign(campaignName, sql) },
                            navController
                        )
                    }

                    // Use an item in LazyColumn to add spacing
                    item {
                        Spacer(modifier = Modifier.height(16.dp))
                    }
                }

                // CreateCampaignButton at the bottom of the screen
                Column(
                    Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    CreateCampaignButton { showDialog = true }
                }
            }
        }

        if (showDialog) {
            CreateCampaignDialog(
                onDismiss = { showDialog = false },
                onConfirm = { enteredName ->
                    campaignName = enteredName
                    showDialog = false
                    launchCampaignCreation(campaignName, context, sql)
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

private fun deleteCampaign(campaignNameData: CampaignNameData, sql: CampaignSqlActivity) {
    CoroutineScope(Dispatchers.IO).launch {
        //TODO make delete HTTP Call
        sql.deleteCampaignById(campaignNameData.syncCampaignId)
        Log.d("DMLandingScreen", "campaign to delete is: ${campaignNameData.syncCampaignId}")
    }

}

private fun launchCampaignCreation(
    campaignName: String,
    context: Context,
    sql: CampaignSqlActivity
) {
    CoroutineScope(Dispatchers.IO).launch {

        CampaignHttp(context).getCampaigns()

        val syncCampaignId = CampaignHttp(context).newCampaign(
            campaignName,
        )

        if (syncCampaignId != 0) {
            sql.insertAndRetrieveCampaignData(campaignName, syncCampaignId)
        }
    }
}


@Preview
@Composable
private fun GetDMScreen() {
    val navController = rememberNavController()
    DMLandingScreen(navController)
}