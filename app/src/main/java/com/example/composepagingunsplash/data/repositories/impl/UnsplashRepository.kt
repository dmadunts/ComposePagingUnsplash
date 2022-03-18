package com.example.composepagingunsplash.data.repositories.impl

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.composepagingunsplash.data.apis.UnsplashApi
import com.example.composepagingunsplash.data.database.UnsplashDatabase
import com.example.composepagingunsplash.data.models.remote.UnsplashImage
import com.example.composepagingunsplash.data.paging.UnsplashRemoteMediator
import com.example.composepagingunsplash.utils.Constants.ITEMS_PER_PAGE
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UnsplashRepository @Inject constructor(
    private val api: UnsplashApi,
    private val database: UnsplashDatabase
) {
    @ExperimentalPagingApi
    fun getAllImages(): Flow<PagingData<UnsplashImage>> {
        val pagingSourceFactory = { database.unsplashImageDao().getAllImages() }
        return Pager(
            config = PagingConfig(pageSize = ITEMS_PER_PAGE),
            remoteMediator = UnsplashRemoteMediator(
                api = api, database = database
            ),
            pagingSourceFactory = pagingSourceFactory
        ).flow
    }
}