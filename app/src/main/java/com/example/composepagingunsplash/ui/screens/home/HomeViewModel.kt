package com.example.composepagingunsplash.ui.screens.home

import androidx.lifecycle.ViewModel
import androidx.paging.ExperimentalPagingApi
import com.example.composepagingunsplash.data.repositories.impl.UnsplashRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@ExperimentalPagingApi
@HiltViewModel
class HomeViewModel @Inject constructor(repository: UnsplashRepository) : ViewModel() {
    val getAllImages = repository.getAllImages()
}
