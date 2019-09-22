package com.example.kotlin_project.providers

import com.mobbile.paul.mt3_1_1.models.*
import com.mobbile.paul.mt3_1_1.providers.MapApi
import com.mobbile.paul.mt3_1_1.viewmodel.ReloadCustomers
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okhttp3.RequestBody
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Repository @Inject
constructor(private val appDao: AppDao, private val api: Api, private var mapi: MapApi) {

    fun getUsers(username: String, password: String, imei: String): Single<Response<EmployeesApi>> =
        api.getUser(username, password, imei)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .map {it}

    fun fetchPostSales(posttoserver: postToServer): Single<Response<postRecieveFromServer>> =
        api.postSales(posttoserver)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .map {it}

    fun getbasket(urno: String, customer: String, employee_id: Int): Single<Response<GenSales>> =
        api.fetchSales(urno, customer,employee_id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .map {it}

    fun createModules(
        employee: List<ModulesRoom>,
        customers: List<Bank_n_CustomersRoom>,
        products: List<ProductsRoom>,
        producttype: List<ProductTypeRoom>
    ) =
        Observable.fromCallable { appDao.saveEmployees(employee, customers, products, producttype) }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())

    fun resaveCustomers(customers: List<Bank_n_CustomersRoom>) = Observable.fromCallable {
        appDao.resaveCustomers(customers) }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())

    fun resaveProducts(products: List<ProductsRoom>) = Observable.fromCallable {
        appDao.resaveProducts(products) }
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())

    fun fetchModules(): Single<List<ModulesRoom>> =
        Single.fromCallable {
            appDao.fetchModules()
        }.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())

    fun fetchCustomers(): Observable<List<Bank_n_CustomersRoom>> =
        Observable.fromCallable {
            appDao.fetchCustomers()
        }.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())

    fun fetchBasket(sep: Int): Observable<List<ProductsRoom>> =
        Observable.fromCallable {
            appDao.fetchBasket(sep)
        }.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())

    fun fetchGoogleMap(
        origin: String,
        destination: String,
        sensor: String,
        mode: String,
        key: String
    ): Single<GoogleGetApi> =
        mapi.getGooleMap(origin, destination, sensor, mode, key)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .map { it }

    fun fetchDailySales(): Observable<List<SalesEntriesRoom>> =
        Observable.fromCallable {
            appDao.fetchDailySales()
        } .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())

    fun createDailySales(
        salesen: List<SalesEntriesRoom>,
        salesh: List<SalesEntrieHolderRoom>
    ) =
        Observable.fromCallable { appDao.saveSales(salesen, salesh) }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())

    fun saveEntryHistory(
        entryhistory: repSalesHistoryRoom
    ) =
        Observable.fromCallable { appDao.saveEntryHistory(entryhistory) }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())

    fun deleteModulesRoom() = Observable.fromCallable {
        appDao.deleteModulesRoom()
    }.subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())

    fun salesHistoryRomm() = Observable.fromCallable {
        appDao.salesHistoryRomm()
    }.subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())



    fun deleteCustomersRoom() = Observable.fromCallable {
        appDao.deleteBank_n_CustomersRoom()
    }.subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())

    fun deleteProductsRoom() = Observable.fromCallable {
        appDao.deleteProductsRoom()
    }.subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())

    fun deleteProductTypeRoom() = Observable.fromCallable {
        appDao.deleteProductTypeRoom()
    }.subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())


    fun deleteSalesEntry() = Observable.mergeDelayError(
        Observable.fromCallable {
            appDao.deleteSalesEntriesRoom()
        },
        Observable.fromCallable {
            appDao.deleteSalesEntrieHolderRoom()
        }
    ).subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())

    fun updateDailySales(orders: Double, inventory: Double, pricing: Int, entry_time: String,  product_id: String,
                         salesprice: Double, contOrder: String, contPrincing: String, contInventory: String)  =
        Observable.fromCallable {
            appDao.updateDailySales(orders, inventory, pricing, entry_time,  product_id,
                salesprice, contOrder, contPrincing, contInventory )
    }.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())

    fun validateSalesEntry(): Observable<Int> =
        Observable.fromCallable {
            appDao.validateSalesEntry()
        }.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())

    fun fetchAllEntryPerDaily(): Observable<List<SalesEntrieHolderRoom>> =
        Observable.fromCallable {
            appDao.fetchAllEntryPerDay()
        }.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())

    fun sumAllSalesEntry(): Observable<SumSales> =
        Observable.fromCallable {
            appDao.sumAllSalesEntry()
        }.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())

    fun pullAllSalesEntry(): Single<List<SalesEntrieHolderRoom>> =
    Single.fromCallable {
        appDao.pullAllSalesEntry()
    }.subscribeOn(Schedulers.io())
    .observeOn(AndroidSchedulers.mainThread())

    fun getUploadImage(map: Map<String, RequestBody>, urno: Int): Single<Response<Attendance>> =
        api.upload(urno, map)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .map {it}

    fun getTask(userid: Int, taskid: Int, dates: String, times: String): Single<Response<EmployeesApi>> =
        api.getTask(userid, taskid, dates, times)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .map {it}

    fun updateCust(sort: Int, rostertime: String) =
        Observable.fromCallable {
            appDao.updateCust(sort, rostertime )
        }.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())

    fun updateCustTrans(urno: Int, rostertime: String) =
        Observable.fromCallable {
            appDao.updateCustTrans(urno, rostertime )
        }.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())

    fun confirmTask(user_id: Int, dates: String): Single<Response<EmployeesApi>> =
        api.confirmTask(user_id, dates)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .map {it}

    fun fetchGoogleDistance (
        units: String,
        origins: String,
        destinations: String,
        key: String
    ): Single<GoogleDistance> =
        mapi.fetchGoogleDistance(units, origins, destinations, key)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .map {it}

    fun updateProducts(totalq :  Double, totalamt : Double, totalcomm : Double, balanceamt : Double, productcode : String) =
        Observable.fromCallable {
            appDao.updateProducts(totalq, totalamt, totalcomm, balanceamt, productcode)
        }.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())

    fun fetchAllSalesEntries(): Single<List<repSalesHistoryRoom>> =
        Single.fromCallable {
            appDao.fetchAllSalesEntries()
        }.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())

    fun outletSales(token: Int): Single<Response<OutletSalesHistory>> =
        api.outletSales(token)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .map {it}

    fun fetchSoldItems(): Single<List<ProductsRoom>> =
        Single.fromCallable {
            appDao.fetchSoldItems()
        }.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())

    fun sumProductEntry(): Observable<totalSumProductEntry> =
        Observable.fromCallable {
            appDao.sumProductEntry()
        }.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())

    fun conputSalesCom(user_id: Int, dates: String): Single<Response<salesCommisssion>> =
        api.conputSalesCom(user_id, dates)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .map {it}

    fun getAllCustomers(user_id: Int): Single<Response<AllCustomers>> =
        api.getAllCustomers(user_id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .map {it}

    fun fetchSpinners(): Single<List<ProductTypeRoom>> =
        Single.fromCallable {
            appDao.fetchSpinners()
        }.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())

    fun reSetCustomerProfile(outletname: String, contactname: String, address: String, phone : String,
                             outlet_class_id: Int, outlet_language_id : Int, outlet_type_id : Int,
                             custno : Int, lat: Double, lng: Double): Single<Response<Attendance>> =
        api.reSetCustomerProfile(outletname, contactname, address, phone, outlet_class_id, outlet_language_id, outlet_type_id, custno, lat, lng)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .map {it}

    fun updareLocalCustomer(outletname: String, lat: String, lng: String, urno: String ) =
        Observable.fromCallable {
            appDao.updareLocalCustomer(outletname, lat, lng, urno)
        }.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())

    fun OtherTask(userid: Int, taskid: Int, dates: String, times: String): Single<Response<EmployeesApi>> =
        api.OtherTask(userid, taskid, dates, times)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .map {it}

    fun setOutletClose(userid: Int, urno: String, dates: String, times: String, lat: String, lng: String, distance: String, visitsequence: String): Single<Response<postRecieveClose>> =
        api.setOutletClose(userid, urno, dates, times, lat, lng, distance, visitsequence)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .map {it}

    fun fecthTodayCustomers(user_id: Int, dates: String): Single<Response<EmployeesApi>> =
        api.fecthTodayCustomers(user_id, dates)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .map {it}

    fun fecthTodayProducts(custno: String, auto: Int): Single<Response<EmployeesApi>> =
        api.fecthTodayProducts(custno, auto)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .map {it}

    fun countProducts(): Observable<Int> =
        Observable.fromCallable {
            appDao.countProducts()
        }.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .map {it}

    fun getAllSalesEntries(): Observable<List<JoinSalesEntriesAndProducts>> =
        Observable.fromCallable {
            appDao.getAllSalesEntries()
        } .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())


}


