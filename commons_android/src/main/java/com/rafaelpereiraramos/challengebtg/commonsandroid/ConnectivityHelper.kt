package com.rafaelpereiraramos.challengebtg.commonsandroid

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.os.Build

class ConnectivityHelper private constructor(
    private val application: Application
) {

    init {
        val connMgr = application.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            connMgr?.registerDefaultNetworkCallback(AndroidQNetworkCallback)
        }
    }

    fun hasNetwork(): Boolean {
        val connMgr = application.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?

        return if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) {
            connMgr?.activeNetworkInfo?.isConnectedOrConnecting ?: false
        } else {
            AndroidQNetworkCallback.isConnectedOrConnecting
        }
    }

    object AndroidQNetworkCallback : ConnectivityManager.NetworkCallback() {
        var isConnectedOrConnecting = false

        override fun onAvailable(network: Network) {
            super.onAvailable(network)
            isConnectedOrConnecting = true
        }

        override fun onLost(network: Network) {
            super.onLost(network)
            isConnectedOrConnecting = false
        }

        override fun onUnavailable() {
            super.onUnavailable()
            isConnectedOrConnecting = false
        }
    }

    companion object {
        @Volatile private var instance: ConnectivityHelper? = null

        fun getInstance(application: Application) =
            instance ?: synchronized(this) {
                instance ?: ConnectivityHelper(application).also { instance = it }
            }
    }
}