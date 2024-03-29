package com.mobbile.paul.mt3_1_1.util

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.NetworkInfo
import android.os.Build
import org.threeten.bp.LocalDateTime
import org.threeten.bp.format.DateTimeFormatter
import kotlin.math.abs
import kotlin.math.cos
import kotlin.math.sqrt

class Utils {

    companion object {

        const val PREFS_FILENAME = "com.mt.v3.1.2.prefs"
        const val LATLNG = "com.mt.v3.1.2.lat_lng"
        var getDate: String = getDate()
        var getTime: String = getTime()

        fun getTime(): String {
            val current = LocalDateTime.now()
            val formatter = DateTimeFormatter.ofPattern("HH:mm:ss")
            //val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS")
            return current.format(formatter)
        }

        fun getDate(): String {
            val current = LocalDateTime.now()
            val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
            return current.format(formatter)
        }

        fun insideRadius(currentLat: Double, currentLng: Double,
                         customerLat: Double, customerLng: Double
                         ): Boolean {
            val ky = 400000 / 360
            val kx = cos(Math.PI * customerLat / 180.0) * ky
            val dx = abs(customerLng - currentLng) * kx
            val dy = abs(customerLat - currentLat) * ky
            return sqrt(dx * dx + dy * dy) <= 5
        }

        //check network checker
        @Suppress("DEPRECATION")
        fun isInternetAvailable(context: Context): Boolean {
            var result = false
            val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                cm?.run {
                    cm.getNetworkCapabilities(cm.activeNetwork)?.run {
                        result = when {
                            hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                            hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                            hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                            else -> false
                        }
                    }
                }
            } else {
                cm?.run {
                    cm.activeNetworkInfo?.run {
                        if (type == ConnectivityManager.TYPE_WIFI) {
                            result = true
                        } else if (type == ConnectivityManager.TYPE_MOBILE) {
                            result = true
                        }
                    }
                }
            }
            return result
        }
    }
}

