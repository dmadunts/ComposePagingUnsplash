package com.example.composepagingunsplash.data.models.remote

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.composepagingunsplash.utils.Constants.UNSPLASH_IMAGE_TABLE

@Entity(tableName = UNSPLASH_IMAGE_TABLE)
data class UnsplashImage(
    @PrimaryKey(autoGenerate = false )
    val id: String,
    @Embedded val urls: Urls,
    val likes: Int,
    @Embedded val user: User
)