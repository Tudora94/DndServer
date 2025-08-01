package com.tudorEnterprises.dndapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.tudorEnterprises.dndapp.constants.Screen
import com.tudorEnterprises.dndapp.ui.screens.CreateUserScreen
import com.tudorEnterprises.dndapp.ui.screens.DMLandingScreen
import com.tudorEnterprises.dndapp.ui.screens.DmOrPlayerScreen
import com.tudorEnterprises.dndapp.ui.screens.GetCampaignBaseScreen
import com.tudorEnterprises.dndapp.ui.screens.GetCharacterBaseScreen
import com.tudorEnterprises.dndapp.ui.screens.LoginScreen
import com.tudorEnterprises.dndapp.ui.screens.PlayerLandingScreen
import com.tudorEnterprises.dndapp.ui.screens.GetCampaignInventoryScreen


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
        composable(route = Screen.Campaign.route + "/{campaignId}",
            arguments = listOf(navArgument("campaignId") { type = NavType.IntType })) {
            backStackEntry ->
            val campaignId = backStackEntry.arguments?.getInt("campaignId") ?: 0
            GetCampaignBaseScreen(navController = navController, campaignId = campaignId)
        }
        composable(route = Screen.Character.route + "/{characterId}",
            arguments = listOf(navArgument("characterId") {type = NavType.IntType })) {
            backStackEntry ->
            val characterId = backStackEntry.arguments?.getInt("characterId") ?: 0
            GetCharacterBaseScreen(navController = navController, characterId = characterId)
        }
        composable(
            route = Screen.CampaignInventory.route + "/{campaignId}/{campaignName}",
            arguments = listOf(
                navArgument("campaignId") { type = NavType.IntType },
                navArgument("campaignName") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val campaignId = backStackEntry.arguments?.getInt("campaignId") ?: 0
            val campaignName = backStackEntry.arguments?.getString("campaignName") ?: ""

            GetCampaignInventoryScreen(
                navController = navController,
                campaignId = campaignId,
                campaignName = campaignName
            )
        }

    }
}