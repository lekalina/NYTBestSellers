package com.lekalina.nytbestsellers

import android.app.Application
import android.content.Context
import com.lekalina.nytbestsellers.api.NetworkState

class NYT: Application() {

    override fun onCreate() {
        super.onCreate()
        appContext = applicationContext
        networkState = NetworkState()
    }

    companion object {
        lateinit  var appContext: Context
        lateinit var networkState: NetworkState
    }
}