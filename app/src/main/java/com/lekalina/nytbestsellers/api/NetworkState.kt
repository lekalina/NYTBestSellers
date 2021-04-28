package com.lekalina.nytbestsellers.api

import android.content.Context
import android.net.*
import com.lekalina.nytbestsellers.NYT

class NetworkState: ConnectivityManager.NetworkCallback() {

    companion object {
        @Volatile private var networkState: NetworkState? = null

        fun getInstance(): NetworkState = networkState ?: synchronized(this) {
            networkState ?: NetworkState().also { networkState = it }
        }
    }

    var isOnline = false

    init {
        val cm: ConnectivityManager = NYT.context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val builder: NetworkRequest.Builder = NetworkRequest.Builder()
        cm.registerNetworkCallback(builder.build(), this)
    }

    override fun onAvailable(network: Network) {
        super.onAvailable(network)
        isOnline = true
    }

    override fun onLost(network: Network) {
        super.onLost(network)
        isOnline = false
    }


}