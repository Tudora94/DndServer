package com.tudorEnterprises.dndapp.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.tudorEnterprises.dndapp.constants.Buttons
import com.tudorEnterprises.dndapp.ui.navigation.GetAppBarTopLoggedIn
import com.tudorEnterprises.dndapp.ui.navigation.GetBottomAppBar
import com.tudorEnterprises.dndapp.ui.navigation.GetGenericNavButton
import com.tudorEnterprises.dndapp.ui.theme.DndApplicationTheme

@Composable
fun DmOrPlayerScreen(navController: NavController) {
    DndApplicationTheme {
        Scaffold(topBar = {
            GetAppBarTopLoggedIn(navController)
        }, bottomBar = {
            GetBottomAppBar()
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
                    text = "Player or Dungeon Master?",
                )
                Spacer(modifier = Modifier.height(18.dp))
                Column(modifier = Modifier.verticalScroll(rememberScrollState()),
                    verticalArrangement = Arrangement.spacedBy(18.dp)) {
                    GetGenericNavButton(navController, Buttons.DungeonMaster)
                    GetGenericNavButton(navController, Buttons.Player)
                }
            }
        }
    }
}

//test comment for push

@Preview
@Composable
private fun GetScreen() {
    val navController = rememberNavController()
    DmOrPlayerScreen(navController)
}