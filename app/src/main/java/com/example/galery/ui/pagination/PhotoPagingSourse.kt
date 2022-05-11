package com.example.galery.ui.pagination

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.galery.data.entity.OnePhotoItem
import com.example.galery.data.retrofit.OnePhotoService

class PhotoPagingSource(
    private val photoApiService: OnePhotoService,
) : PagingSource<Int, OnePhotoItem>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, OnePhotoItem> {
        return try {
            val nextPage = params.key ?: 1
            val response = photoApiService.getOnePhotoAsync(nextPage)

            val listItem = response.await()
            LoadResult.Page(
                data = listItem,
                prevKey = if (nextPage == 1) null else nextPage - 1,
                nextKey = nextPage + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, OnePhotoItem>): Int? {
        return null
    }

}