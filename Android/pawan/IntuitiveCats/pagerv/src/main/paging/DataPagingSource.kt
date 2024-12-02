package com.example.intuitivecats.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.intuitivecats.CatRepository
import com.example.intuitivecats.model.BreedItem
import retrofit2.HttpException
import javax.inject.Inject


internal class DataPagingSource @Inject constructor(
    private val catRepository: CatRepository
) : PagingSource<Int, BreedItem>() {
    override fun getRefreshKey(state: PagingState<Int, BreedItem>): Int? {
        return null
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, BreedItem> {
        return try {
            val currentPage = params.key ?: 0
            val loadSize = 25
            val response = catRepository.getBreeds(currentPage, loadSize)
            LoadResult.Page(
                data = response,
                prevKey = if (currentPage == 0) null else -1,
                nextKey = if (response.size < loadSize) null else currentPage.plus(1)
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        } catch (exception: HttpException) {
            LoadResult.Error(exception)
        }
    }
}

class PagedRecyclerView(
    val repo: ApiRepository,
    val loadSize: Int,

) {

}