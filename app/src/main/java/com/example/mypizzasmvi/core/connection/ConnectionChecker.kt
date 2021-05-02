package com.example.mypizzasmvi.core.connection

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkRequest
import android.os.Build

interface ConnectionChecker {
    fun isConnected(): Boolean
}

class ConnectionCheckerImpl(private val context: Context): ConnectionChecker {
    @Synchronized override fun isConnected(): Boolean {
        val manager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val request = NetworkRequest.Builder().build()
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val callbackHandler = CallbackHandler()
            manager.requestNetwork(request, callbackHandler, 1000)
            callbackHandler.await().also { manager.unregisterNetworkCallback(callbackHandler) }
        } else {
            manager.activeNetworkInfo!!.isAvailable
        }
    }
}

class CallbackHandler : ConnectivityManager.NetworkCallback() {
    var hasConnection: Boolean? = null
    override fun onAvailable(network: Network) { hasConnection = true }
    override fun onUnavailable() { hasConnection = false }

    fun await(): Boolean {
        while (hasConnection == null) { Thread.sleep(10) }
        return hasConnection!!
    }
}
