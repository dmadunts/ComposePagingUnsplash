package com.example.composepagingunsplash.data.models.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Query
import com.example.composepagingunsplash.data.models.remote.UnsplashImage

@Dao
interface UnsplashImageDao {

    @Query("SELECT * FROM unsplash_image_table")
    fun getAllImages(): PagingSource<Int, UnsplashImage>


}