package com.example.composepagingunsplash.data.apis

import com.example.composepagingunsplash.BuildConfig
import com.example.composepagingunsplash.data.models.remote.UnsplashImage
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface UnsplashApi {
    @Headers("Authorization: Client-ID ${BuildConfig.API_KEY}")
    @GET("/photos")
    suspend fun getAllImages(
        @Query("page") pageNumber: Int,
        @Query("per_page") perPage: Int
    ): List<UnsplashImage>

    @Headers("Authorization: Client-ID ${BuildConfig.API_KEY}")
    @GET("/search/photos ")
    suspend fun searchImage(
        @Query("page") pageNumber: Int,
        @Query("per_page") perPage: Int
    ): List<UnsplashImage>
}