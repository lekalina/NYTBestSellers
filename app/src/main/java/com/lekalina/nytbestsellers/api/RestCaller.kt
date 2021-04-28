package com.lekalina.nytbestsellers.api

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class RestCaller {

    val service: NYTService

    /**
     * Static network constants
     */
    companion object {
        const val BASE_URL = "https://api.nytimes.com/svc/books/v3/"
        const val API_KEY = "INSERT_NYT_API_KEY_HERE"
    }

    /**
     * Creates the network client with custom query params and sets the service object.
     * The api-key is appended to all network calls per NYT's auth process.
     */
    init {

        val httpClient = OkHttpClient.Builder()
        httpClient.apply {
            addInterceptor(
                Interceptor { chain ->
                    val authUrl = chain.request().url().newBuilder().addQueryParameter("api-key", API_KEY).build()
                    val builder = chain.request().newBuilder()
                    builder.url(authUrl)
                    return@Interceptor chain.proceed(builder.build())
                }
            )
        }

        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(httpClient.build())
            .build()

        service = retrofit.create(NYTService::class.java)
    }
}