package com.tudorEnterprises.dndapp.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
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
import com.tudorEnterprises.dndapp.ui.navigation.GetAppBarTop
import com.tudorEnterprises.dndapp.ui.navigation.GetBottomAppBar

@Composable
fun CreateUserScreen(debugVersion: String? = null, navController: NavController) {
    Scaffold(topBar = {
        GetAppBarTop()
    }, bottomBar = {
        GetBottomAppBar(debugVersion)
    }) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxWidth()
                .fillMaxHeight(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Create a New User",
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(top = 16.dp)
            )

            // Add your input fields for creating a user here

            Spacer(modifier = Modifier.height(18.dp))

            Button(
                onClick = { navController.popBackStack() }, // Go back to previous screen
                modifier = Modifier.padding(16.dp)
            ) {
                Text("Back")
            }
        }
    }
}

@Preview
@Composable
private fun ViewCreateUserScreen() {
    val navController = rememberNavController()
    CreateUserScreen("TestVersion", navController)
}