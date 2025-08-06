package com.tudorEnterprises.dndapp.ui.navigation

import android.util.Log
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.tudorEnterprises.dndapp.R
import com.tudorEnterprises.dndapp.constants.Screen
import com.tudorEnterprises.dndapp.objects.SecureStorage
import com.tudorEnterprises.dndapp.ui.theme.DndApplicationTheme

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun GetAppBarTopLoggedIn(navController: NavController) {
    val context = LocalContext.current
    var menuExpanded by remember { mutableStateOf(false) }
    val currentScreen by navController.currentBackStackEntryAsState()

    DndApplicationTheme {
        CenterAlignedTopAppBar(
            colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                containerColor = MaterialTheme.colorScheme.primary,
                titleContentColor = MaterialTheme.colorScheme.inversePrimary,
            ),
            navigationIcon = {
                if (currentScreen?.destination?.route != Screen.DmOrPlayer.route) {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            tint = MaterialTheme.colorScheme.inversePrimary,
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "back"
                        )
                    }
                }
            },
            title = {
                Text(
                    text = context.getString(R.string.app_name),
                    style = MaterialTheme.typography.headlineLarge
                )
            },
            actions = {
                IconButton(onClick = { menuExpanded = true }) {
                    Icon(imageVector = Icons.Default.Menu, contentDescription = "Menu",
                        tint = MaterialTheme.colorScheme.inversePrimary)
                }
                DropdownMenu(
                    expanded = menuExpanded,
                    onDismissRequest = { menuExpanded = false }
                ) {
                    DropdownMenuItem(
                        text = { Text("Logout") },
                        onClick = {
                            menuExpanded = false
                            Log.d("LogOut", SecureStorage.getToken(context).toString())
                            SecureStorage.clearToken(context)
                            SecureStorage.clearRefreshToken(context)
                            SecureStorage.clearUserId(context)
                            navController.navigate(Screen.Home.route)
                        }
                    )
                }
            }
        )
    }
}

@Preview
@Composable
private fun GetScreen() {
    val navController = rememberNavController()
    GetAppBarTopLoggedIn(navController)
}