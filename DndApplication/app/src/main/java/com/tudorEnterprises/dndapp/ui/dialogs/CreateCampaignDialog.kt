package com.tudorEnterprises.dndapp.ui.dialogs

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun CreateCampaignDialog(onDismiss: () -> Unit, onConfirm: (String) -> Unit) {
    var campaignName by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = { },
        confirmButton = {
            ElevatedButton(onClick = { onConfirm(campaignName)
                onDismiss()}
            ) {
                Text("Create")
            }
        },
        title = {Text("Login")},
        text = {
            Column(verticalArrangement = Arrangement.Center) {
                OutlinedTextField(value = campaignName,
                    onValueChange = { campaignName = it},
                    label = { Text("campaign Name")})
            }
        }
    )
}

@Preview
@Composable
private fun GetCampaignDialog() {
    CreateCampaignDialog({}, {})
}