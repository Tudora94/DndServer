package com.tudorEnterprises.dndapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.tudorEnterprises.dndapp.navigation.NavigationController
import com.tudorEnterprises.dndapp.ui.theme.DndApplicationTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            DndApplicationTheme {
                NavigationController()
            }
        }
    }
}
