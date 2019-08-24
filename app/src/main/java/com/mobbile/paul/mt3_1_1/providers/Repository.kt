package com.example.kotlin_project.providers

import com.mobbile.paul.mt3_1_1.models.*
import com.mobbile.paul.mt3_1_1.providers.MapApi
import io.reactivex.Maybe
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

    fun getUsers(urno: String, customer: String): Single<Response<GenSales>> =
        api.fetchSales(urno, customer)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .map { it }

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
        }

    fun fetchCustomers(): Observable<List<Bank_n_CustomersRoom>> =
        Observable.fromCallable {
            appDao.fetchCustomers()
        }

    fun fetchBasket(sep: Int): Observable<List<ProductsRoom>> =
        Observable.fromCallable {
            appDao.fetchBasket(sep)
        }

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
        }

    fun createDailySales(
        salesen: List<SalesEntriesRoom>,
        salesh: List<SalesEntrieHolderRoom>
    ) =
        Observable.fromCallable { appDao.saveSales(salesen, salesh) }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())

    fun deleteAll() = Observable.mergeDelayError(
        Observable.fromCallable {
            appDao.deleteModulesRoom()
        },
        Observable.fromCallable {
            appDao.deleteBank_n_CustomersRoom()
        },
        Observable.fromCallable {
            appDao.deleteProductsRoom()
        },
        Observable.fromCallable {
            appDao.deleteProductTypeRoom()
        }
    ).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())

    fun deleteSalesEntry() = Observable.mergeDelayError(
        Observable.fromCallable {
            appDao.deleteSalesEntriesRoom()
        },
        Observable.fromCallable {
            appDao.deleteSalesEntrieHolderRoom()
        }
    ).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())

    fun fetchAllEntryPerDaily(): Observable<List<SalesEntrieHolderRoom>> =
        Observable.fromCallable {
            appDao.fetchAllEntryPerDay()
        }

    /*fun updateDailySales(id:Int, product_code: String, qtyperroll: Int, qtyperpack: Int, priceperroll: Double,priceperpack: Double)  =
        Observable.fromCallable {
            appDao.updateDailySales(id, product_code, qtyperroll, qtyperpack, priceperroll ,priceperpack )
    }*/

    companion object {
        private val TAG = "Repository"
    }

}



