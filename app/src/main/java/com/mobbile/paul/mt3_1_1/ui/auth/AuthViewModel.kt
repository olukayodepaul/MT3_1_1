package com.mobbile.paul.mt3_1_1.ui.auth

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.kotlin_project.providers.Repository
import com.mobbile.paul.mt3_1_1.models.*
import javax.inject.Inject

class AuthViewModel @Inject constructor(val repository: Repository) : ViewModel() {

    fun auth(
        username: String,
        password: String,
        imei: String,
        mdate: String,
        byPassReEntry: Boolean
    ): MutableLiveData<SaveEntries> {

        var mResult = MutableLiveData<SaveEntries>()
        var sharedEditor = SaveEntries()

        repository.getUsers(username, password, imei)
            .subscribe(
                { data ->
                    Log.d(TAG, data.body().toString())
                    when (data.body()!!.status) {
                        200 -> {
                            if (!byPassReEntry) {
                                insertEmployee(
                                    data.body()!!.modules,
                                    data.body()!!.customers,
                                    data.body()!!.product,
                                    data.body()!!.spinners
                                )
                            }
                            Log.d(TAG, "get information " + byPassReEntry.toString())
                            sharedEditor.id = data.body()!!.id
                            sharedEditor.name = data.body()!!.name
                            sharedEditor.dates = mdate
                        }
                        404 -> {
                            sharedEditor.id = data.body()!!.status
                            sharedEditor.name = data.body()!!.msg
                            sharedEditor.dates = ""
                        }
                    }
                    mResult.postValue(sharedEditor)
                },
                { error ->
                    sharedEditor.id = 404
                    sharedEditor.name = error.message.toString()
                    sharedEditor.dates = ""
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
        ).subscribe(
            {
                deleteEmployee( ModulesRoom(),
                    Bank_n_CustomersRoom(),
                    ProductsRoom(),
                    ProductTypeRoom()
                )
            },{}
        ).isDisposed
    }

    private fun deleteEmployee(
        employee: ModulesRoom,
        customers: Bank_n_CustomersRoom,
        products: ProductsRoom,
        producttype: ProductTypeRoom
    ) {
        repository.deleteEmployee(
            employee,
            customers,
            products,
            producttype
        ).subscribe()
    }

    companion object {
        var TAG = "AuthViewModel"
    }
}