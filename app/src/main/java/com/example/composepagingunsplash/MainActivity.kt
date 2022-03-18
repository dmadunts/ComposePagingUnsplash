package com.example.composepagingunsplash

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.rememberNavController
import com.example.composepagingunsplash.navigation.SetupGraph
import com.example.composepagingunsplash.ui.theme.ComposePagingTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposePagingTheme {
                val navController = rememberNavController()
                SetupGraph(navController = navController)
            }
        }
    }
}
