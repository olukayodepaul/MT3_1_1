package com.mobbile.paul.mt3_1_1.providers

import com.mobbile.paul.mt3_1_1.models.*
import io.reactivex.Single
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.Response
import retrofit2.http.Query

interface NodejsApi {

    @Headers("Connection:close")
    @POST("/api/opensales")
    fun postSales(
        @Body saleposttoserver: postToServer
    ): Single<Response<postRecieveFromServer>>

    @Headers("Connection:close")
    @POST("/api/closesales")
    fun setOutletClose(
        @Query("userid") userid: Int,
        @Query("urno") urno: String,
        @Query("dates") dates: String,
        @Query("times") times: String,
        @Query("lat") lat: String,
        @Query("lng") lng: String,
        @Query("distance") distance: String,
        @Query("visitsequence") visitsequence: String,
        @Query("outletname") outletname: String,
        @Query("tripduration") tripduration: String
    ): Single<Response<postRecieveClose>>

    @Headers("Connection:close")
    @POST("/api/salesentries")
    fun fetchSales(
        @Query("urno") urno: String,
        @Query("customerno") customerno: String,
        @Query("employee_id") employee_id: Int
    ): Single<Response<GenSales>>

    @Headers("Connection:close")
    @POST("/api/each_customer_sales")
    fun outletSales(
        @Query("token") token: Int
    ): Single<Response<OutletSalesHistory>>

}