package com.duolingo.data.di.providers

import android.annotation.SuppressLint
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import javax.inject.Singleton

/**
 * A network checker which checks if the device is connected to the internet.
 */
@Singleton
class NetworkChecker(private val context: Context) {

    @Suppress("DEPRECATION")
    val isConnected: Boolean
        @SuppressLint("MissingPermission")
        get() = (context.getSystemService(Context.CONNECTIVITY_SERVICE) as? ConnectivityManager)?.run {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                getNetworkCapabilities(activeNetwork)?.run {
                    when {
                        hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                        hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                        hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                        else -> false
                    }
                } ?: false
            } else {
                activeNetworkInfo?.run {
                    when (type) {
                        ConnectivityManager.TYPE_WIFI -> true
                        ConnectivityManager.TYPE_MOBILE -> true
                        else -> false
                    }
                } ?: false
            }
        } ?: false

}
