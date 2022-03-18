package com.example.composepagingunsplash.navigation

sealed class Screen(
    val route: String
) {
    object Home : Screen("home_screen")
    object Search : Screen("search_screen")
}