package com.example.intuitivecats.network

import com.example.intuitivecats.breeds.model.BreedItem
import retrofit2.http.GET
import retrofit2.http.Query

interface BreedApi {

    @GET("v1/breeds")
    suspend fun getCats(@Query("page") page: Int, @Query("limit") loadSize: Int): List<BreedItem>
}