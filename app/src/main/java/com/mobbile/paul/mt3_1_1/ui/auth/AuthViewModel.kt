package com.mobbile.paul.mt3_1_1.ui.auth

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.kotlin_project.providers.Repository
import com.mobbile.paul.mt3_1_1.models.ApiModel.*
import javax.inject.Inject

class AuthViewModel @Inject constructor(val repository: Repository) : ViewModel() {


    fun auth(username: String, password: String, imei: String): MutableLiveData<SaveEntries> {

        var mResult = MutableLiveData<SaveEntries>()
        var sharedEditor = SaveEntries()

        repository.getUsers(username, password, imei)
            .subscribe(
                { data ->
                    Log.d(TAG, data.body().toString())
                    when (data.body()!!.status) {
                        200 -> {
                            var mModules: List<ModulesApi> = data.body()!!.modules
                            var mCustomers: List<Bank_n_CustomersApi> = data.body()!!.customers
                            var mProduct: List<ProductsApi> = data.body()!!.product
                            var mProducttype: List<ProductTypeApi> = data.body()!!.spinners
                            insertEmployee(mModules, mCustomers, mProduct, mProducttype)
                            sharedEditor.id = data.body()!!.id
                            sharedEditor.name = data.body()!!.name
                        }
                        404 -> {
                            sharedEditor.id = data.body()!!.status
                            sharedEditor.name = data.body()!!.msg
                        }
                    }
                    mResult.postValue(sharedEditor)
                },
                { error ->
                    sharedEditor.id = 404
                    sharedEditor.name = error.message.toString()
                    mResult.postValue(sharedEditor)
                }).isDisposed

        return mResult
    }

    private fun insertEmployee(
        employee: List<ModulesApi>,
        customers: List<Bank_n_CustomersApi>,
        products: List<ProductsApi>,
        producttype: List<ProductTypeApi>
    ) {
        repository.createModules(
            employee.map { it.toModulesEntity() },
            customers.map { it.toCustomersEntity() },
            products.map { it.toProductEntity() },
            producttype.map { it.toProductTypeRoomEntity() }
        ).subscribe().isDisposed
    }

    companion object {
        var TAG = "AuthViewModel"
    }


}