package com.tudorEnterprises.dndapp.ui.navigation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.tudorEnterprises.dndapp.constants.Screen
import com.tudorEnterprises.dndapp.constants.UserRole
import com.tudorEnterprises.dndapp.dataStorage.tables.InventoryItemData

@Composable
fun GetInventoryButton(
    inventoryItem: InventoryItemData,
    onDelete: () -> Unit,
    navController: NavController,
    userRole: String,
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        ElevatedButton(
            onClick = {navController.navigate(Screen.InventoryItem.route + "/${inventoryItem.itemId}/$userRole" +
                    "?campaignId=${inventoryItem.campaignId}" +
                    "&characterId=${inventoryItem.characterId}") },
            modifier = Modifier.weight(4f)
        ) {
            Text(inventoryItem.itemName)
        }

        if( userRole == UserRole.DUNGEON_MASTER.role) {
            ElevatedButton(
                onClick = { onDelete() },
                modifier = Modifier.weight(1f)
            ) {
                Icon(imageVector = Icons.Default.Delete, contentDescription = "Delete")
            }
        }
    }
}

@Preview
@Composable
private fun GetInventoryButtonPreviewTrue() {
    val navController = rememberNavController()
    val itemName = "Potion of Healing"
    GetInventoryButton(InventoryItemData(0, 0, null, itemName), { }, navController, userRole = UserRole.DUNGEON_MASTER.role)
}

@Preview
@Composable
private fun GetInventoryButtonPreviewFalse() {
    val navController = rememberNavController()
    val itemName = "Potion of Healing"
    GetInventoryButton(InventoryItemData(0, 0, null, itemName), { }, navController, userRole = UserRole.PLAYER.role)
}