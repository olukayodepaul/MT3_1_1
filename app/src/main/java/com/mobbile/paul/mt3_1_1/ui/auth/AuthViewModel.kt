package com.mobbile.paul.mt3_1_1.ui.auth


import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.kotlin_project.providers.Repository
import com.mobbile.paul.mt3_1_1.models.*
import retrofit2.Response
import javax.inject.Inject

class AuthViewModel @Inject constructor(val repository: Repository) : ViewModel() {

    var mResult = MutableLiveData<SaveEntries>()

    var sharedEditor = SaveEntries()

    lateinit var timeOfNow: String

    fun authMutable(): MutableLiveData<SaveEntries> {
        return mResult
    }

    fun auth(
        username: String,
        password: String,
        imei: String,
        mdate: String,
        byPassReEntry: Boolean
    ) {
        timeOfNow = mdate

        repository.getUsers(username, password, imei)
            .subscribe(
                {
                    when (it.body()!!.status) {
                        200 -> {

                            if (!byPassReEntry) {
                                deleteAll(it)
                                Log.d(TAG, "CALL 1")
                            }else{
                                sharedEditor.id = it.body()!!.id
                                sharedEditor.name = it.body()!!.name
                                sharedEditor.dates = timeOfNow
                                mResult.postValue(sharedEditor)
                                Log.d(TAG, "CALL 2")
                            }
                        }
                        404 -> {
                            sharedEditor.id = it.body()!!.status
                            sharedEditor.name = it.body()!!.msg
                            sharedEditor.dates = ""
                            mResult.postValue(sharedEditor)
                        }
                    }

                },
                {
                    sharedEditor.id = 404
                    sharedEditor.name = it.message.toString()
                    sharedEditor.dates = ""
                    mResult.postValue(sharedEditor)
                }).isDisposed
    }

    private fun insertEmployee(data: Response<EmployeesApi>) {

        var employee: List<ModulesApi> = data.body()!!.modules
        var customers: List<Bank_n_CustomersApi> = data.body()!!.customers
        var products: List<ProductsApi> = data.body()!!.product
        var producttype: List<ProductTypeApi> = data.body()!!.spinners

        repository.createModules(
            employee.map { it.toModulesEntity() },
            customers.map { it.toCustomersEntity() },
            products.map { it.toProductEntity() },
            producttype.map { it.toProductTypeRoomEntity()}
        ).subscribe(
            {
                sharedEditor.id = data.body()!!.id
                sharedEditor.name = data.body()!!.name
                sharedEditor.dates = timeOfNow
                mResult.postValue(sharedEditor)

            }, {
                sharedEditor.id = 404
                sharedEditor.name = it.message.toString()
                sharedEditor.dates = ""
                mResult.postValue(sharedEditor)
            }

        ).isDisposed
    }

    private fun deleteAll(data: Response<EmployeesApi>){
        repository.deleteAll()
            .subscribe(
                {
                    insertEmployee(data)
                },{
                    Log.d(TAG, "CALL 4")
                    sharedEditor.id = 404
                    sharedEditor.name = it.message.toString()
                    sharedEditor.dates = ""
                    mResult.postValue(sharedEditor)
                }
            ).isDisposed
    }

    companion object {
        var TAG = "AuthViewModel"
    }
}