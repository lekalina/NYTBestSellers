package com.lekalina.nytbestsellers.api

import com.lekalina.nytbestsellers.data.BooksResponse
import com.lekalina.nytbestsellers.data.Categories
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface NYTService {

    @GET("lists/names.json")
    suspend fun getBookCategories(): Response<Categories>

    @GET("lists/current/{category}.json")
    suspend fun getBooksByCategory(@Path("category") category: String): Response<BooksResponse>
}