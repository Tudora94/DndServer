package com.tudorEnterprises.dndapp.ui.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.tudorEnterprises.dndapp.AppMetadata
import com.tudorEnterprises.dndapp.ui.theme.DndApplicationTheme

@Composable
fun GetBottomAppBar() {
    DndApplicationTheme {
        val versionName = AppMetadata.versionName

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = MaterialTheme.colorScheme.primary)
                .padding(top = 4.dp, bottom = 4.dp),
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = "v$versionName",
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = MaterialTheme.colorScheme.inversePrimary.copy(
                        alpha = 0.3F
                    )
                ),
                textAlign = TextAlign.Right,
                modifier = Modifier.padding(end = 20.dp),
            )
        }
    }
}

@Composable
@Preview
private fun BottomAppPreview(){
    GetBottomAppBar()
}