package com.example.intuitivecats.network

import com.example.intuitivecats.breeds.model.BreedItem
import com.example.pagerv.paging.DataItem
import com.example.pagerv.paging.ApiRepository
import javax.inject.Inject

class CatRepository @Inject constructor(private val breedApi: BreedApi) : ApiRepository {

    private var breeds = mutableListOf<BreedItem>()

    override suspend fun getData(currentPage: Int, loadSize: Int): List<DataItem> {
        return breedApi.getCats(currentPage, loadSize).also {
            breeds.addAll(it)
        }.map {
            DataItem(
                id = it.id,
                title = it.name,
                subTitle = it.origin,
                imageUrl = it.image?.url
            )
        }
    }

    fun getBreedById(id: String?): BreedItem {
        return breeds.filter {
            it.id == id
        }[0]
    }

}
