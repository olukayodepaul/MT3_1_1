package com.example.kotlin_project.providers


import com.mobbile.paul.mt3_1_1.models.EmployeesApi
import com.mobbile.paul.mt3_1_1.models.GenSales
import com.mobbile.paul.mt3_1_1.models.postRecieveFromServer
import com.mobbile.paul.mt3_1_1.models.postToServer
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

    @Multipart
    @POST("/mobiletrader/mapcustomers")
    fun upload(
        @PartMap map: Map<String, @JvmSuppressWildcards RequestBody>
    ): Single<Response<EmployeesApi>>

}