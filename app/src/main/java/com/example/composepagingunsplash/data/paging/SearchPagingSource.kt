package com.example.composepagingunsplash.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.composepagingunsplash.data.apis.UnsplashApi
import com.example.composepagingunsplash.data.models.remote.UnsplashImage
import com.example.composepagingunsplash.utils.Constants.ITEMS_PER_PAGE

class SearchPagingSource(
    private val api: UnsplashApi,
    private val query: String
) : PagingSource<Int, UnsplashImage>() {
    override fun getRefreshKey(state: PagingState<Int, UnsplashImage>): Int? {
        return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, UnsplashImage> {
        val currentPage = params.key ?: 1
        return try {
            val response = api.searchImage(query, ITEMS_PER_PAGE)
            val endOfPaginationReached = response.images.isEmpty()

            if (response.images.isNotEmpty()) {
                LoadResult.Page(
                    data = response.images,
                    prevKey = if (currentPage == 1) null else currentPage + 1,
                    nextKey = if (endOfPaginationReached) null else currentPage - 1
                )
            } else {
                LoadResult.Page(
                    data = emptyList(),
                    prevKey = null,
                    nextKey = null
                )
            }
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}