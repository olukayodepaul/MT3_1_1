package com.mobbile.paul.mt3_1_1.ui.auth


import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.kotlin_project.providers.Repository
import com.mobbile.paul.mt3_1_1.models.*
import javax.inject.Inject

class AuthViewModel @Inject constructor(val repository: Repository) : ViewModel() {

    lateinit var data: EmployeesApi

    lateinit var dayOfNow: String

    var mResult = MutableLiveData<String>()

    var ObResult = MutableLiveData<SaveEntries>()

    var sharedEditor = SaveEntries()


    fun authMutable(): MutableLiveData<String> {
        return mResult
    }

    fun authObservable(): MutableLiveData<SaveEntries> {
        return ObResult
    }

    fun callAuthApi(
        username: String,
        password: String,
        imei: String,
        mdate: String,
        byPassReEntry: Boolean
    ) {
        dayOfNow = mdate
        repository.getUsers(username, password, imei)
            .subscribe({
                if (it != null) {

                    data = it.body()!!

                    when (it.body()!!.status) {
                        200 -> {
                            if (!byPassReEntry) {
                                deleteModulesRoom()
                            } else {
                                sharedEditor.id = it.body()!!.id
                                sharedEditor.name = it.body()!!.name
                                sharedEditor.customerno = it.body()!!.customer_code
                                sharedEditor.dates = dayOfNow
                                sharedEditor.mode = it.body()!!.mode
                                ObResult.postValue(sharedEditor)
                            }
                        }
                        else -> {
                            mResult.postValue(it.body()!!.msg)
                        }
                    }
                }
            }, {
                mResult.postValue(it.message)
            }).isDisposed
    }

    private fun deleteModulesRoom() {
        repository.deleteModulesRoom()
            .subscribe(
                {
                    deleteCustomersRooml()
                }, {
                    mResult.postValue(it.message)
                }
            ).isDisposed
    }

    private fun deleteCustomersRooml() {
        repository.deleteCustomersRoom()
            .subscribe(
                {
                    deleteProductsRoom()
                }, {
                    mResult.postValue(it.message)
                }
            ).isDisposed
    }

    private fun deleteProductsRoom() {
        repository.deleteProductsRoom()
            .subscribe(
                {
                    deleteProductTypeRoom()
                }, {
                    mResult.postValue(it.message)
                }
            ).isDisposed
    }

    private fun deleteProductTypeRoom() {
        repository.deleteProductTypeRoom()
            .subscribe(
                {
                    insertEmployee()
                }, {
                    mResult.postValue(it.message)
                }
            ).isDisposed
    }

    private fun insertEmployee() {

        repository.createModules(
            data.modules.map { it.toModulesEntity() },
            data.customers.map { it.toCustomersEntity() },
            data.product.map { it.toProductEntity() },
            data.spinners.map { it.toProductTypeRoomEntity() }

        ).subscribe(
            {
                sharedEditor.id = data!!.id //this is the employee id
                sharedEditor.name = data!!.name
                sharedEditor.customerno = data!!.customer_code
                sharedEditor.dates = dayOfNow
                ObResult.postValue(sharedEditor)

                Log.d(TAG, data.id.toString()+" "+data.name+" "+data.customer_code+" "+dayOfNow)

            },{
                mResult.postValue(it.message)
            }
        ).isDisposed
    }

    companion object {
        var TAG = "AuthViewModel"
    }


}