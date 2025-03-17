package com.tudorEnterprises.dndapp.ui.screens

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.tudorEnterprises.dndapp.ui.navigation.GetAppBarTopLoggedIn
import com.tudorEnterprises.dndapp.ui.navigation.GetBottomAppBar

@Composable
fun GetCampaignBaseScreen(navController: NavController, campaignId: Int) {
    Scaffold(
        topBar = { GetAppBarTopLoggedIn(navController) },
        bottomBar = { GetBottomAppBar("Test") }
    ) { innerPadding ->
        Text(text = "CampaignId = $campaignId",
            modifier = Modifier
                .padding(innerPadding))
    }
}

@Preview
@Composable
private fun GEtCampaignScreen() {
    val navController = rememberNavController()
    GetCampaignBaseScreen(navController, 1)
}