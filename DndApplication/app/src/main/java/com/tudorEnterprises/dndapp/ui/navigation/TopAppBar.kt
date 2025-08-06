package com.tudorEnterprises.dndapp.ui.navigation

import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.tudorEnterprises.dndapp.R
import com.tudorEnterprises.dndapp.ui.theme.DndApplicationTheme

@Composable
@Preview
@OptIn(ExperimentalMaterial3Api::class)
fun GetAppBarTop() {
    val context = LocalContext.current
    val appName = context.getString(R.string.app_name)
    DndApplicationTheme {
        CenterAlignedTopAppBar(
            colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                containerColor = MaterialTheme.colorScheme.primary,
                titleContentColor = MaterialTheme.colorScheme.onPrimary
            ),
            title = {
                Text(
                    appName, style = MaterialTheme.typography.headlineLarge
                )

            },
        )
    }
}