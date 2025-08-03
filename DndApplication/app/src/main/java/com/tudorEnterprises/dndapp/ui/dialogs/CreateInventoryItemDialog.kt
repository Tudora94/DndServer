package com.tudorEnterprises.dndapp.ui.dialogs

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.tudorEnterprises.dndapp.objects.InventoryItem

@Composable
fun CreateInventoryItemDialog(
    onDismiss: () -> Unit,
    onConfirm: (InventoryItem) -> Unit
) {
    var inventoryItem by remember { mutableStateOf(InventoryItem()) }
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current

    AlertDialog(
        onDismissRequest = { onDismiss() },
        confirmButton = {
            ElevatedButton(onClick = {
                onConfirm(inventoryItem)
                keyboardController?.hide()
                focusManager.clearFocus()
                onDismiss()
            }
            ) {
                Text("Create")
            }
        },
        title = { Text("New inventory item") },
        text = {
            Box(modifier = Modifier.heightIn(max = 300.dp)) { // Limit height to enable scrolling
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    item {
                        OutlinedTextField(
                            value = inventoryItem.name,
                            onValueChange = {
                                if (it.length <= 255) {
                                    inventoryItem = inventoryItem.copy(name = it)
                                }
                            },
                            label = { Text("Item Name") },
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                    item {
                        OutlinedTextField(
                            value = inventoryItem.description,
                            onValueChange = {
                                if (it.length <= 255) {
                                    inventoryItem = inventoryItem.copy(description = it)
                                }
                            },
                            label = { Text("Item Description") },
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                    item {
                        OutlinedTextField(
                            value = inventoryItem.detail,
                            onValueChange = { inventoryItem = inventoryItem.copy(detail = it) },
                            label = { Text("Item Details") },
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }
            }

        },
        dismissButton = {
            ElevatedButton(onClick = {
                keyboardController?.hide()
                focusManager.clearFocus()
                onDismiss()
            }
            ) {
                Text("Cancel")
            }
        }
    )
}

@Preview
@Composable
private fun GetCampaignDialog() {
    CreateInventoryItemDialog({}, {})
}