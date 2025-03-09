package com.tudorEnterprises.dndapp.ui.navigation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun GetCreateUserButton(
    onCreateUserClick: () -> Unit
) {
    ElevatedButton(
        onClick = {
            onCreateUserClick()
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 4.dp, bottom = 4.dp, start = 30.dp, end = 30.dp)
            .height(60.dp),
        shape = RoundedCornerShape(25),
        elevation = ButtonDefaults.elevatedButtonElevation(3.dp),
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(4.dp),
        ) {
            Row {
                Text(
                    text = "Create New User",
                    style = MaterialTheme.typography.headlineMedium,
                )
            }
        }
    }
}

@Preview
@Composable
private fun ViewCreateButton() {
    GetCreateUserButton {  }
}