package com.mobbile.paul.mt3_1_1.providers


import com.mobbile.paul.mt3_1_1.models.*
import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Query
import okhttp3.RequestBody
import retrofit2.http.PartMap
import retrofit2.http.Multipart


interface Api {

    @Headers("Connection:close")
    @POST("/aut")
    fun getUser(
        @Query("username") username: String,
        @Query("password") password: String,
        @Query("imei") imei: String
    ): Single<Response<EmployeesApi>>

    @Headers("Connection:close")
    @Multipart
    @POST("/mapcustomers")
    fun upload(
        @Query("urno") urno: Int,
        @PartMap map: Map<String, @JvmSuppressWildcards RequestBody>
    ): Single<Response<Attendance>>

    @Headers("Connection:close")
    @POST("/employeetask")
    fun getTask(
        @Query("userid") userid: Int,
        @Query("taskid") taskid: Int,
        @Query("dates") dates: String,
        @Query("times") times: String
    ): Single<Response<EmployeesApi>>

    @Headers("Connection:close")
    @POST("/confirmtask")
    fun confirmTask(
        @Query("userid") userid: Int,
        @Query("dates") dates: String
    ): Single<Response<EmployeesApi>>

    @Headers("Connection:close")
    @POST("/commissions")
    fun conputSalesCom(
        @Query("user_id") user_id: Int,
        @Query("dates") dates: String
    ): Single<Response<salesCommisssion>>

    @Headers("Connection:close")
    @POST("/allmycustomer")
    fun getAllCustomers(
        @Query("user_id") user_id: Int
    ): Single<Response<AllCustomers>>

    @POST("/customerprofile")
    fun reSetCustomerProfile (
        @Query("outletname") outletname: String,
        @Query("contactname") contactname: String,
        @Query("address") address: String,
        @Query("phone") phone: String,
        @Query("outlet_class_id") outlet_class_id: Int,
        @Query("outlet_language_id")  outlet_language_id: Int,
        @Query("outlet_type_id") outlet_type_id : Int,
        @Query("custno") custno : Int,
        @Query("lat") lat: Double,
        @Query("lng") lng: Double
    ): Single<Response<Attendance>>

    @Headers("Connection:close")
    @POST("/othertask")
    fun OtherTask(
        @Query("userid") userid: Int,
        @Query("taskid") taskid: Int,
        @Query("dates") dates: String,
        @Query("times") times: String
    ): Single<Response<EmployeesApi>>

    @Headers("Connection:close")
    @POST("/reselectcustomers")
    fun fecthTodayCustomers(
        @Query("user_id") user_id: Int,
        @Query("dates") dates: String
    ): Single<Response<EmployeesApi>>

    @Headers("Connection:close")
    @POST("/reselectproduct")
    fun fecthTodayProducts(
        @Query("custno") custno: String,
        @Query("autoid") autoid: Int
    ): Single<Response<EmployeesApi>>

    //update customers
    @Headers("Connection:close")
    //@Multipart
    @POST("/all_rep_card")
    fun updateCards(
        @Query("employee_id") employee_id: Int,
        @Query("urno") urno: String,
        @Query("outletclassid") outletclassid: Int,
        @Query("outletlanguageid") outletlanguageid: Int,
        @Query("outlettypeid") outlettypeid: Int,
        @Query("outletname") outletname: String,
        @Query("outletaddress") outletaddress: String,
        @Query("contactname") contactname: String,
        @Query("contactphone") contactphone: String,
        @Query("latitude") latitude: String,
        @Query("longitude") longitude: String,
        // @PartMap map: Map<String, @JvmSuppressWildcards RequestBody>,
        @Query("entry_date_time") entry_date_time: String,
        @Query("entry_date") entry_date: String
    ): Single<Response<getCards>>

    //registration of the new customers
    @Headers("Connection:close")
    @Multipart
    @POST("/mapoutlets")
    fun createNewCards(
        @Query("employee_id") employee_id: Int,
        @Query("outletclassid") outletclassid: Int,
        @Query("outletlanguageid") outletlanguageid: Int,
        @Query("outlettypeid") outlettypeid: Int,
        @Query("outletname") outletname: String,
        @Query("outletaddress") outletaddress: String,
        @Query("contactname") contactname: String,
        @Query("contactphone") contactphone: String,
        @Query("latitude") latitude: String,
        @Query("longitude") longitude: String,
        @PartMap map: Map<String, @JvmSuppressWildcards RequestBody>,
        @Query("entry_date_time") entry_date_time: String,
        @Query("entry_date") entry_date: String
    ): Single<Response<getCards>>

    @Headers("Connection:close")
    @POST("/geos")
    fun getGoe(
        @Query("urno") urno: Int
    ): Single<Response<getGeoData>>
}
