package com.example.composepagingunsplash.data.paging

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.example.composepagingunsplash.data.apis.UnsplashApi
import com.example.composepagingunsplash.data.database.UnsplashDatabase
import com.example.composepagingunsplash.data.models.remote.UnsplashImage
import com.example.composepagingunsplash.data.models.remote.UnsplashRemoteKeys
import com.example.composepagingunsplash.utils.Constants.ITEMS_PER_PAGE

@ExperimentalPagingApi
class UnsplashRemoteMediator(
    private val api: UnsplashApi,
    private val database: UnsplashDatabase
) : RemoteMediator<Int, UnsplashImage>() {
    private val unsplashImageDao = database.unsplashImageDao()
    private val unsplashRemoteKeysDao = database.unsplashRemoteKeysDao()

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, UnsplashImage>
    ): MediatorResult {
        return try {
            val currentPage = when (loadType) {
                LoadType.REFRESH -> {
                    val remoteKeys = getRemoteKeyToClosestPosition(state)
                    remoteKeys?.nextPage?.minus(1) ?: 1
                }
                LoadType.PREPEND -> {
                    val remoteKeys = getRemoteKeysForFirstItem(state)
                    val prevPage = remoteKeys?.prevPage
                        ?: return MediatorResult.Success(
                            endOfPaginationReached = remoteKeys != null
                        )
                    prevPage
                }
                LoadType.APPEND -> {
                    val remoteKeys = getRemoteKeysForLastItem(state)
                    val nextPage = remoteKeys?.nextPage
                        ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
                    nextPage
                }
            }

            val response = api.getAllImages(pageNumber = currentPage, perPage = ITEMS_PER_PAGE)
            val endOfPaginationReached = response.isEmpty()

            val prevPage = if (currentPage == 1) null else currentPage - 1
            val nextPage = if (endOfPaginationReached) null else currentPage + 1

            database.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    unsplashImageDao.deleteAllImages()
                }
                val keys = response.map { unsplashImage ->
                    UnsplashRemoteKeys(
                        id = unsplashImage.id,
                        prevPage = prevPage,
                        nextPage = nextPage
                    )
                }
                unsplashRemoteKeysDao.addAllRemoteKeys(keys)
                unsplashImageDao.addImages(images = response)
            }
            MediatorResult.Success(endOfPaginationReached)
        } catch (e: Exception) {
            return MediatorResult.Error(e)
        }
    }

    private suspend fun getRemoteKeyToClosestPosition(state: PagingState<Int, UnsplashImage>): UnsplashRemoteKeys? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { id ->
                unsplashRemoteKeysDao.getRemoteKeys(id)
            }
        }
    }

    private suspend fun getRemoteKeysForFirstItem(state: PagingState<Int, UnsplashImage>): UnsplashRemoteKeys? {
        return state.pages.firstOrNull { it.data.isEmpty() }?.data?.firstOrNull()
            ?.let { unsplashImage -> unsplashRemoteKeysDao.getRemoteKeys(unsplashImage.id) }
    }

    private suspend fun getRemoteKeysForLastItem(state: PagingState<Int, UnsplashImage>): UnsplashRemoteKeys? {
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()
            ?.let { unsplashImage ->
                unsplashRemoteKeysDao.getRemoteKeys(
                    unsplashImage.id
                )
            }
    }

}