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
import com.tudorEnterprises.dndapp.dataStorage.tables.CampaignNameData


@Composable
fun GetCampaignButtons(campaignName: CampaignNameData, onDelete: () -> Unit, navController: NavController) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        ElevatedButton(
            onClick = {navController.navigate(Screen.Campaign.route + "/${campaignName.syncCampaignId}")},
            modifier = Modifier.weight(4f)
        ) {
            Text(campaignName.campaignName)
        }

        ElevatedButton(
            onClick = { onDelete() },
            modifier = Modifier.weight(1f)
        ) {
            Icon(imageVector = Icons.Default.Delete, contentDescription = "Delete")
        }
    }
}

@Preview
@Composable
private fun GetCampaignButtonsPreview() {
    val navController = rememberNavController()
    val campaignName = CampaignNameData("test name value")
    GetCampaignButtons(campaignName, { }, navController)
}