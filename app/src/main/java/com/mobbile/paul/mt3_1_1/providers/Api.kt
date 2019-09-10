package com.example.kotlin_project.providers


import com.mobbile.paul.mt3_1_1.models.*
import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Query
import okhttp3.RequestBody
import retrofit2.http.PartMap
import retrofit2.http.Multipart


interface Api {

    @Headers("Connection:close")
    @POST("/mobiletrader/login")
    fun getUser(
        @Query("username") username: String,
        @Query("password") password: String,
        @Query("imei") imei: String
    ): Single<Response<EmployeesApi>>

    @Headers("Connection:close")
    @POST("/mobiletrader/salesentry")
    fun fetchSales(
        @Query("urno") urno: String,
        @Query("customerno") customerno: String,
        @Query("employee_id") employee_id: Int
    ): Single<Response<GenSales>>

    @Headers("Connection:close")
    @POST("/mobiletrader/postsales")
    fun postSales(
        @Body saleposttoserver: postToServer
    ): Single<Response<postRecieveFromServer>>

    @Headers("Connection:close")
    @Multipart
    @POST("/mobiletrader/mapcustomers")
    fun upload(
        @PartMap map: Map<String, @JvmSuppressWildcards RequestBody>
    ): Single<Response<EmployeesApi>>

    @Headers("Connection:close")
    @POST("/mobiletrader/employeetask")
    fun getTask(
        @Query("userid") userid: Int,
        @Query("taskid") taskid: Int,
        @Query("dates") dates: String,
        @Query("times") times: String
    ): Single<Response<EmployeesApi>>

    @Headers("Connection:close")
    @POST("/mobiletrader/confirmtask")
    fun confirmTask(
        @Query("userid") userid: Int,
        @Query("dates") dates: String
    ): Single<Response<EmployeesApi>>

    @Headers("Connection:close")
    @POST("/mobiletrader/outletsales")
    fun outletSales(
        @Query("token") token: Int
    ): Single<Response<OutletSalesHistory>>

    //service not yet implemented
    @Headers("Connection:close")
    @POST("/mobiletrader/salescom")
    fun conputSalesCom(
        @Query("user_id") user_id: Int,
        @Query("dates") dates: String
    ): Single<Response<salesCommisssion>>


    @Headers("Connection:close")
    @POST("/mobiletrader/allmycustomer")
    fun getAllCustomers(
        @Query("user_id") user_id: Int
    ): Single<Response<AllCustomers>>

}
