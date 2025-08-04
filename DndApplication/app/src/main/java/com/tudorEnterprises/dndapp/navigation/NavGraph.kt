package com.tudorEnterprises.dndapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.tudorEnterprises.dndapp.constants.Screen
import com.tudorEnterprises.dndapp.constants.UserRole
import com.tudorEnterprises.dndapp.ui.screens.CreateUserScreen
import com.tudorEnterprises.dndapp.ui.screens.DMLandingScreen
import com.tudorEnterprises.dndapp.ui.screens.DmOrPlayerScreen
import com.tudorEnterprises.dndapp.ui.screens.GetCampaignBaseScreen
import com.tudorEnterprises.dndapp.ui.screens.GetCharacterBaseScreen
import com.tudorEnterprises.dndapp.ui.screens.LoginScreen
import com.tudorEnterprises.dndapp.ui.screens.PlayerLandingScreen
import com.tudorEnterprises.dndapp.ui.screens.GetCampaignInventoryScreen
import com.tudorEnterprises.dndapp.ui.screens.GetInventoryItemScreen


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
            route = Screen.CampaignInventory.route + "/{id}/{name}/{userRole}?playerCampaignId={playerCampaignId}",
            arguments = listOf(
                navArgument("id") { type = NavType.IntType },
                navArgument("name") { type = NavType.StringType },
                navArgument("userRole") { type = NavType.StringType },
                navArgument("playerCampaignId") { type = NavType.StringType; nullable = true; defaultValue = null } // Optional argument for player campaigns
            )
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getInt("id") ?: 0
            val name = backStackEntry.arguments?.getString("name") ?: ""
            val userRole = backStackEntry.arguments?.getString("userRole") ?: UserRole.PLAYER.role

            val playerCampaignIdString = backStackEntry.arguments?.getString("playerCampaignId")
            val playerCampaignId = playerCampaignIdString?.toIntOrNull()

            GetCampaignInventoryScreen(
                navController = navController,
                id = id,
                name = name,
                userRole = userRole,
                playerCampaignId = playerCampaignId
            )
        }
        composable(route = Screen.InventoryItem.route + "/{itemId}/{userRole}?campaignId={campaignId}&characterId={characterId}",
            arguments = listOf(
                navArgument("itemId") { type = NavType.IntType },
                navArgument("userRole") { type = NavType.StringType },
                navArgument("campaignId") { type = NavType.StringType; nullable = true; defaultValue = null }, // Optional argument for campaign ID
                navArgument("characterId") { type = NavType.StringType; nullable = true; defaultValue = null } // Optional argument for character ID
            )
        ) { backStackEntry ->
            val itemId = backStackEntry.arguments?.getInt("itemId") ?: 0
            val userRole = backStackEntry.arguments?.getString("userRole") ?: UserRole.PLAYER.role

            val campaignIdString = backStackEntry.arguments?.getString("campaignId")
            val campaignId = campaignIdString?.toIntOrNull()
            val characterIdString = backStackEntry.arguments?.getString("characterId")
            val characterId = characterIdString?.toIntOrNull()

            GetInventoryItemScreen(
                navController = navController,
                inventoryItemId = itemId,
                userRole = userRole,
                campaignId = campaignId,
                characterId = characterId
            )
        }

    }
}