package com.example.composepagingunsplash.model

import androidx.room.Embedded

data class User(
    @Embedded
    val links: Links,
    val username: String,
)
