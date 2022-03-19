package com.example.composepagingunsplash.data.models.remote

import com.google.gson.annotations.SerializedName

data class SearchResult(
    @SerializedName("results")
    val images: List<UnsplashImage>
)
