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
import com.tudorEnterprises.dndapp.dataStorage.tables.CampaignNameData


@Composable
fun GetCampaignButtons(campaignName: CampaignNameData) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        ElevatedButton(
            onClick = {},
            modifier = Modifier.weight(4f)
        ) {
            Text(campaignName.campaignName)
        }

        ElevatedButton(
            onClick = {},
            modifier = Modifier.weight(1f)
        ) {
            Icon(imageVector = Icons.Default.Delete, contentDescription = "Delete")
        }
    }
}

@Preview
@Composable
private fun GetCampaignButtons() {
    val campaignName = CampaignNameData("test name value")
    GetCampaignButtons(campaignName)
}