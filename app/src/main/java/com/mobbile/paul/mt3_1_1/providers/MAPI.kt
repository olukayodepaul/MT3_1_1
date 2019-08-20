package com.mobbile.paul.mt3_1_1.providers


import com.mobbile.paul.mt3_1_1.models.GoogleApi
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface MAPI {

    @Headers("Connection:close")
    @GET("/maps/api/directions/json?")
    fun getGooleMap(
        @Query("origin") origin: String,
        @Query("destination") destination: String,
        @Query("sensor") sensor: String,
        @Query("mode") mode: String,
        @Query("key") key: String
    ): Single<GoogleApi>
}


