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

@Composable
fun GetInventoryButton(
    inventoryItemName: String,
    onDelete: () -> Unit,
    navController: NavController,
    isDM: Boolean,
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        ElevatedButton(
            onClick = { }, //TODO: Implement navigation to inventory details
            modifier = Modifier.weight(4f)
        ) {
            Text(inventoryItemName)
        }

        if( isDM) {
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
    GetInventoryButton(itemName, { }, navController, isDM = true)
}

@Preview
@Composable
private fun GetInventoryButtonPreviewFalse() {
    val navController = rememberNavController()
    val itemName = "Potion of Healing"
    GetInventoryButton(itemName, { }, navController, isDM = false)
}