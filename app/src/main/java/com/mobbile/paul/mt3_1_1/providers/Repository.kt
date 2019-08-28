package com.example.kotlin_project.providers

import com.mobbile.paul.mt3_1_1.models.*
import com.mobbile.paul.mt3_1_1.providers.MapApi
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
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
            .map { it }

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

    fun updateDailySales(orders: Double, inventory: Double, pricing: Int, entry_time: String, id: Int, product_id: Int,
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

    //fetch the result
    /*fun fetchAllEntryPerDaily(): Observable<List<SalesEntrieHolderRoom>> =
        Observable.fromCallable {
            appDao.fetchAllEntryPerDay()
        }*/



    companion object {
        private val TAG = "Repository"
    }

}



