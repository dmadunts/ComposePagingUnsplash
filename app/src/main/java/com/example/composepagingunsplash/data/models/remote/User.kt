package com.example.composepagingunsplash.data.models.remote

import androidx.room.Embedded

data class User(
    @Embedded
    val links: Links,
    val username: String,
)
