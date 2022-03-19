package com.example.composepagingunsplash.ui.screens.search

import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.composepagingunsplash.ui.screens.common.ListContent

@Composable
fun SearchScreen(
    navController: NavController,
    viewModel: SearchViewModel = hiltViewModel()
) {
    val searchQuery by viewModel.searchQuery
    val searchedImages = viewModel.searchedImages.collectAsLazyPagingItems()

    Scaffold(
        topBar = {
            SearchWidget(
                text = searchQuery,
                onTextChange = { viewModel.updateSearchQuery(it) },
                onSearchClicked = { viewModel.search(it) },
                onCloseClicked = { navController.popBackStack() }
            )
        }
    ) {
        ListContent(items = searchedImages)
    }
}