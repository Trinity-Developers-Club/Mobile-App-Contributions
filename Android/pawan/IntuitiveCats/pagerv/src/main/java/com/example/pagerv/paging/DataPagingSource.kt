package com.example.pagerv.paging

import androidx.lifecycle.LiveData
import androidx.paging.*

import javax.inject.Inject


class DataPagingSource @Inject constructor(
    private val catRepository: ApiRepository,
    private var loadSize: Int
) : PagingSource<Int, DataItem>() {
    override fun getRefreshKey(state: PagingState<Int, DataItem>): Int? {
        return null
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, DataItem> {
        return try {
            val currentPage = params.key ?: 0
            val response = catRepository.getData(currentPage, loadSize)
            LoadResult.Page(
                data = response,
                prevKey = if (currentPage == 0) null else currentPage.minus(1),
                nextKey = if (response.size < loadSize) null else currentPage.plus(1)
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}

class PageRv(private val repository: ApiRepository, loadSize: Int = 50) :
    LiveData<PagingData<DataItem>>() {
    val data = Pager(PagingConfig(1)) {
        DataPagingSource(repository, loadSize)
    }.liveData
}