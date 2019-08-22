package com.example.kotlin_project.providers


import com.mobbile.paul.mt3_1_1.models.EmployeesApi
import com.mobbile.paul.mt3_1_1.models.GenSales
import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Query


interface Api {

    @Headers("Connection:close")
    @POST("/mobiletrader/login")
    fun getUser(
        @Query("username") username: String,
        @Query("password") password: String,
        @Query("imei") imei: String
    ): Single<Response<EmployeesApi>>

    @Headers("Connection:close")
    @GET("/mobiletrader/json?")
    fun fetchSales(
        @Query("usersid") usersid: String
    ): Single<Response<GenSales>>

}