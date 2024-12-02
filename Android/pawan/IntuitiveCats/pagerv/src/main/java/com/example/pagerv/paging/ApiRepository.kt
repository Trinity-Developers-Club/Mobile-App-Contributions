package com.example.pagerv.paging

interface ApiRepository {
    suspend fun getData(currentPage: Int, loadSize: Int): List<DataItem>
}