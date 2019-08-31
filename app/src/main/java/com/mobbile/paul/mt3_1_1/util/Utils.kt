package com.mobbile.paul.mt3_1_1.util

import org.threeten.bp.LocalDateTime
import org.threeten.bp.format.DateTimeFormatter

class Utils {

    companion object{

       const val  PREFS_FILENAME = "com.mt.v3.1.2.prefs"
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


    }



}