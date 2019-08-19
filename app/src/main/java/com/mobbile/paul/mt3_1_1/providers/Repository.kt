package com.example.kotlin_project.providers

import com.mobbile.paul.mt3_1_1.models.Bank_n_CustomersRoom
import com.mobbile.paul.mt3_1_1.models.ModulesRoom
import com.mobbile.paul.mt3_1_1.models.ProductTypeRoom
import com.mobbile.paul.mt3_1_1.models.ProductsRoom
import com.mobbile.paul.mt3_1_1.models.EmployeesApi
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Repository @Inject
constructor(private val appDao: AppDao, private val api: Api) {

    fun getUsers(username: String, password: String, imei: String): Single<Response<EmployeesApi>> =
           api.getUser(username, password, imei)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .map {it}

    fun createModules(employee: List<ModulesRoom>,
                      customers: List<Bank_n_CustomersRoom>,
                      products: List<ProductsRoom>,
                      producttype: List<ProductTypeRoom>) =
        Observable.fromCallable { appDao.saveEmployees(employee,customers,products,producttype)}
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())

    fun fetchModules(): Single<List<ModulesRoom>> =
        Single.fromCallable {appDao.fetchModules()}

    fun fetchCustomers(): Observable<List<Bank_n_CustomersRoom>> =
        Observable.fromCallable {appDao.fetchCustomers()}

    fun fetchBasket(sep:Int): Observable<List<ProductsRoom>> =
        Observable.fromCallable {appDao.fetchBasket(sep)}

    companion object {
        private val TAG = "Repository"
    }

}

