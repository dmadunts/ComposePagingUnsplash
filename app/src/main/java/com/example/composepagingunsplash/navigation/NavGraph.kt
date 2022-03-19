package com.example.composepagingunsplash.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.paging.ExperimentalPagingApi
import com.example.composepagingunsplash.ui.screens.home.HomeScreen
import com.example.composepagingunsplash.ui.screens.search.SearchScreen

@ExperimentalPagingApi
@Composable
fun SetupGraph(navController: NavHostController) {
    NavHost(navController = navController, startDestination = Screen.Home.route) {
        composable(route = Screen.Home.route) {
            HomeScreen(navController)
        }
        composable(Screen.Search.route) {
            SearchScreen(navController)
        }
    }
}