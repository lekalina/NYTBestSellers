package com.lekalina.nytbestsellers

import android.app.Application
import android.content.Context
import com.lekalina.nytbestsellers.api.NetworkState

class NYT: Application() {

    companion object {
        // set accessible context reference to avoid passing context
        // around in viewmodels and repos simply to initialize the db
        // instance and network state listener
        lateinit var context: Context
    }

    override fun onCreate() {
        super.onCreate()
        context = this
        NetworkState.getInstance() // start listening to network state
    }
}