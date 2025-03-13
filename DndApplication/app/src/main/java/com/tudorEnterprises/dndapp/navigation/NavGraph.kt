package com.tudorEnterprises.dndapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.tudorEnterprises.dndapp.constants.Screen
import com.tudorEnterprises.dndapp.ui.screens.CreateUserScreen
import com.tudorEnterprises.dndapp.ui.screens.DMLandingScreen
import com.tudorEnterprises.dndapp.ui.screens.DmOrPlayerScreen
import com.tudorEnterprises.dndapp.ui.screens.LoginScreen
import com.tudorEnterprises.dndapp.ui.screens.PlayerLandingScreen


@Composable
fun NavigationController() {
    val navController = rememberNavController()

    //TODO add token check to confirm if access token exists and

    NavHost(
        navController = navController,
        startDestination = Screen.Home.route, // Starts at Login
    ) {
        composable(route = Screen.Home.route) {
            LoginScreen(navController = navController)
        }
        composable(route = Screen.CreateUser.route) {
            CreateUserScreen(navController = navController)
        }
        composable(route = Screen.DmOrPlayer.route) {
            DmOrPlayerScreen(navController = navController)
        }
        composable(route = Screen.DMLanding.route) {
            DMLandingScreen(navController = navController)
        }
        composable(route = Screen.PlayerLanding.route) {
            PlayerLandingScreen(navController = navController)
        }
    }
}