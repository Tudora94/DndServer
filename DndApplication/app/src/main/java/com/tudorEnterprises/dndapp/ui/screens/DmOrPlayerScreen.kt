package com.tudorEnterprises.dndapp.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.tudorEnterprises.dndapp.ui.navigation.GetAppBarTopLoggedIn
import com.tudorEnterprises.dndapp.ui.navigation.GetBottomAppBar
import com.tudorEnterprises.dndapp.ui.theme.DndApplicationTheme

@Composable
fun DmOrPlayerScreen(navController: NavController) {
    DndApplicationTheme {
        DndApplicationTheme {
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
                ) {}
            }

        }
    }
}

@Preview
@Composable
private fun GetScreen() {
    val navController = rememberNavController()
    DmOrPlayerScreen(navController)
}