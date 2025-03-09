package com.tudorEnterprises.dndapp.ui.navigation

import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.tudorEnterprises.dndapp.constants.appName
import com.tudorEnterprises.dndapp.ui.theme.Purple40

@Composable
@Preview
@OptIn(ExperimentalMaterial3Api::class)
fun GetAppBarTop() {
    CenterAlignedTopAppBar(
        colors = topAppBarColors(
            containerColor = Purple40,
            titleContentColor = MaterialTheme.colorScheme.inversePrimary,
        ),
        title = {
            Text(
                appName, style = MaterialTheme.typography.headlineLarge
            )

        },
    )
}