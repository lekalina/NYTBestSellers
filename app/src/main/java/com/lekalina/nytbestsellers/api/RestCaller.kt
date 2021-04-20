package com.lekalina.nytbestsellers.api

import com.lekalina.nytbestsellers.NYT
import okhttp3.Cache
import okhttp3.CacheControl
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


class RestCaller(private val enableCache: Boolean = true) {

    private val service: NYTService

    /**
     * Static network constants
     */
    companion object {
        const val BASE_URL = "https://api.nytimes.com/svc/books/v3/"
        const val API_KEY = "INSERT_NYT_API_KEY_HERE"
    }

    /**
     * Creates the network client with custom cache headers/query params and sets the service object.
     * Default Cache maxAge is set to 24 hours since the NYT response data is only updated weekly.
     * Offline maxAge is set to 7 days and pulls from Cache only. No network calls are made offline.
     * The api-key is appended to all network calls per NYT's auth process.
     */
    init {

        val httpClient = OkHttpClient.Builder()
        if(enableCache) {
            httpClient.cache(Cache(NYT.appContext.cacheDir, 10 * 1024 * 1024L))
        }
        httpClient.apply {
            addInterceptor(
                Interceptor { chain ->
                    val authUrl = chain.request().url().newBuilder().addQueryParameter("api-key", API_KEY).build()
                    val builder = chain.request().newBuilder()
                    builder.url(authUrl)
                    if(enableCache) {
                        if(NYT.networkState.isOnline) {
                            builder.cacheControl(CacheControl.Builder()
                                    .maxAge(24, TimeUnit.HOURS)
                                    .build())
                        }
                        else {
                            builder.cacheControl(CacheControl.Builder()
                                    .onlyIfCached()
                                    .maxAge(7, TimeUnit.DAYS)
                                    .build())
                        }
                    }
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

    /**
     *  @return the created service
     */
    fun getService(): NYTService {
        return service
    }
}