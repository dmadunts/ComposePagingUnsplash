package com.example.composepagingunsplash.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.composepagingunsplash.data.models.dao.UnsplashImageDao
import com.example.composepagingunsplash.data.models.remote.UnsplashImage
import com.example.composepagingunsplash.data.models.remote.UnsplashRemoteKeys

@Database(entities = [UnsplashImage::class, UnsplashRemoteKeys::class], version = 1)
abstract class UnsplashDatabase : RoomDatabase() {
    abstract fun unsplashImageDao(): UnsplashImageDao
    abstract fun unsplashRemoteKeysDao(): UnsplashRemoteKeys
}