package com.tudorEnterprises.dndapp.ui.Dialogs

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun LoadingDialog(isLoading: Boolean, message: String?, onDismiss: () -> Unit) {
    AlertDialog(
        onDismissRequest = { },
        confirmButton = {
            if (!isLoading) { // Show the dismiss button only when loading is done
                Button(onClick = onDismiss) {
                    Text("Dismiss")
                }
            }
        },
        title = { Text("Login") },
        text = {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                if (isLoading) {
                    CircularProgressIndicator()
                }
                Spacer(modifier = Modifier.height(16.dp))
                Text(message ?: "Processing...")
            }
        }
    )
}

@Preview
@Composable
private fun GetDialog() {
    LoadingDialog(true,"TestString") { }
}
