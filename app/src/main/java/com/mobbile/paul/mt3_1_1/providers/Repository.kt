package com.example.kotlin_project.providers

import com.mobbile.paul.mt3_1_1.models.*
import com.mobbile.paul.mt3_1_1.providers.MapApi
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

    fun updateDailySales(orders: Double, inventory: Double, pricing: Int, entry_time: String, id: Int, product_id: String,
                         salesprice: Double, contOrder: String, contPrincing: String, contInventory: String)  =
        Observable.fromCallable {
            appDao.updateDailySales(orders, inventory, pricing, entry_time, id, product_id,
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

    fun getUploadImage(map: Map<String, RequestBody>): Single<Response<EmployeesApi>> =
        api.upload(map)
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

}


