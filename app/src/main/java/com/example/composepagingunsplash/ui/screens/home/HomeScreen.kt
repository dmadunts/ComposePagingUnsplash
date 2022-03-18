package com.example.composepagingunsplash.ui.screens.home

import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.paging.ExperimentalPagingApi
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.composepagingunsplash.navigation.Screen
import com.example.composepagingunsplash.ui.screens.common.ListContent

@ExperimentalPagingApi
@Composable
fun HomeScreen(
    navController: NavController,
    homeViewModel: HomeViewModel = hiltViewModel()
) {
    val getAllImages = homeViewModel.getAllImages.collectAsLazyPagingItems()

    Scaffold(topBar = {
        HomeTopBar(onSearchClicked = {
            navController.navigate(Screen.Search.route)
        })
    }
    ) {
        ListContent(items = getAllImages)
    }
}