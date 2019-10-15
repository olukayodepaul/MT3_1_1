package com.mobbile.paul.mt3_1_1.ui.settings

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mobbile.paul.mt3_1_1.models.*
import com.mobbile.paul.mt3_1_1.providers.Repository
import com.mobbile.paul.mt3_1_1.viewmodel.ReloadCustomers
import javax.inject.Inject

class SettingViewModel @Inject constructor(private val repository: Repository): ViewModel() {

    var mResponse = MutableLiveData<ReloadCustomers>()

    var response = ReloadCustomers()

    fun mResponse(): LiveData<ReloadCustomers>{
        return mResponse
    }

    fun reloadCustomers(user_id: Int, dates: String) {

        repository.fecthTodayCustomers(user_id, dates)
            .subscribe({
                if (it.isSuccessful && it.body()!=null && it.code()==200 && it.body()!!.status==200) {
                    Log.d(TAG, it.body()!!.toString() )
                    reInsertCustomersIntoLocalDb(it.body()!!.customers)
                }else{
                    response.status = it.code()
                    response.msg = "Unknown Customer Error, Please screen shot and send to mobile trader monitor. Thanks!"
                    mResponse.postValue(response)
                }
            }, {
                response.status = 500
                response.msg = it.message
                mResponse.postValue(response)
            }).isDisposed
    }

    fun reInsertCustomersIntoLocalDb(customers: List<Bank_n_CustomersApi>) {
        repository.resaveCustomers(
            customers.map {it.toCustomersEntity() }
        ).subscribe({
            response.status = 200
            response.msg = "Customers Data Successfully Synchronised. Thanks!"
            mResponse.postValue(response)
        },{
            response.status = 500
            response.msg = it.message
            mResponse.postValue(response)
        }).isDisposed
    }

    fun reloadProduct(custno: String?) {
        repository.fecthTodayProducts(custno!!, firstCount())
            .subscribe({
                if (it.isSuccessful && it.body()!=null && it.code()==200 && it.body()!!.status==200) {
                    Log.d(TAG, it.body()!!.product.toString())
                    reInsertProductsIntoLocalDb(it.body()!!.product)
                }else{
                    response.status = it.code()
                    response.msg = "Unknown Basket Error, Please screen shot and send to mobile trader monitor. Thanks!"
                    mResponse.postValue(response)
                }
            }, {
                response.status = 500
                response.msg = it.message
                mResponse.postValue(response)
            }).isDisposed
    }

    fun reInsertProductsIntoLocalDb(products: List<ProductsApi>) {
        repository.resaveProducts(
            products.map {it.toProductEntity()}
        ).subscribe({
            response.status = 200
            response.msg = "Basket Data Successfully Synchronise. Thanks!"
            mResponse.postValue(response)
        },{
            response.status = 500
            response.msg = it.message
            mResponse.postValue(response)
        }).isDisposed
    }

    fun firstCount(): Int {
        var mNumber = 0
        repository.countProducts()
            .subscribe({
                mNumber = it
            },{
            }).isDisposed
        return mNumber
    }

    companion object{
        var TAG = "SettingViewModel"
    }

}