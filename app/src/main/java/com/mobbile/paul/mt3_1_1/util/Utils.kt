package com.mobbile.paul.mt3_1_1.util

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import org.threeten.bp.LocalDateTime
import org.threeten.bp.format.DateTimeFormatter
import kotlin.math.*

class Utils {

    companion object {

        const val PREFS_FILENAME = "com.mt.v3.1.2.prefs"
        const val LATLNG = "com.mt.v3.1.2.lat_lng"
        var getTime: String = getTime()

        fun getTime(): String {
            val current = LocalDateTime.now()
            val formatter = DateTimeFormatter.ofPattern("HH:mm:ss")
            return current.format(formatter)
        }

        fun insideRadius(
            currentLat: Double, currentLng: Double,
            customerLat: Double, customerLng: Double
        ): Boolean {
            val ky = 40000 / 360
            val kx = cos(PI * customerLat / 180.0) * ky
            val dx = abs(customerLng - currentLng) * kx
            val dy = abs(customerLat - currentLat) * ky
            return sqrt(dx * dx + dy * dy) <= 5
        }

        fun distanceCovered(
            originLat: Double, originLng: Double,
            destLat: Double, destLng: Double
        ): Double {

            val moriginLng: Double = Math.toRadians(originLng)
            val mdestLng: Double = Math.toRadians(destLng)
            val moriginLat: Double = Math.toRadians(originLat)
            val mdestLat: Double = Math.toRadians(destLat)

            // Haversine formula
            val dlon: Double = mdestLng - moriginLng
            val dlat: Double = mdestLat - moriginLat

            val a: Double = sin(dlat / 2).pow(2.0) + cos(moriginLat) * cos(mdestLat) * sin(dlon / 2).pow(2)

            val c: Double = 2 * asin(sqrt(a))
            val r = 6371 //radius of the earth in km
            return (c * r)

        }

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

