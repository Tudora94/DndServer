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
fun JoinCampaignDialog(onDismiss: () -> Unit, onConfirm: (String) -> Unit) {
    var roomCode by remember { mutableStateOf("") }


    AlertDialog(
        onDismissRequest = { onDismiss() },
        confirmButton = {
            ElevatedButton(onClick = {
                onConfirm(roomCode)
                onDismiss()
            }
            ) {
                Text("Join")
            }
        },
        title = { Text("Enter Room Code") },
        text = {
            Column(verticalArrangement = Arrangement.Center) {
                OutlinedTextField(value = roomCode,
                    onValueChange = { roomCode = it},
                    label = { Text("Room Code") })
            }
        },
        dismissButton = {
            ElevatedButton(onClick = {
                onDismiss()}
            ) {
                Text("Cancel")
            }
        }

        )
}

@Preview
@Composable
private fun GetCampaignDialog() {
    JoinCampaignDialog({}, {})
}