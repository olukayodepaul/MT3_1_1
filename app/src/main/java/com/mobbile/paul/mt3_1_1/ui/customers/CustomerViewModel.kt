package com.mobbile.paul.mt3_1_1.ui.customers


import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.kotlin_project.providers.Repository
import com.mobbile.paul.mt3_1_1.models.ProductTypeRoom
import com.mobbile.paul.mt3_1_1.models.RepCustomers
import com.mobbile.paul.mt3_1_1.viewmodel.EditCustomer
import javax.inject.Inject

class CustomerViewModel @Inject constructor(private val repository: Repository) : ViewModel() {

    var mResponse = MutableLiveData<EditCustomer>()

    var response = EditCustomer()

    fun mResponse(): LiveData<EditCustomer> {
        return mResponse
    }

    //fetch all rep customers
    fun getAllCustomers(user_id: Int): LiveData<List<RepCustomers>> {
        var mResult = MutableLiveData<List<RepCustomers>>()
        repository.getAllCustomers(user_id)
            .subscribe({
                if (it.body()!!.status == 200) {
                    mResult.postValue(it.body()!!.repcustomers)
                } else {
                    mResult.postValue(null)
                }
            }, {
                mResult.postValue(null)
            }).isDisposed
        return mResult
    }

    //fetch all spinner information
    fun fetchSpinners(): LiveData<List<ProductTypeRoom>> {
        var mResult = MutableLiveData<List<ProductTypeRoom>>()
        repository.fetchSpinners()
            .subscribe({
                mResult.postValue(it)
            }, {
                mResult.postValue(null)
            }).isDisposed
        return mResult
    }

    fun reSetCustomerProfile(
        outletname: String, contactname: String, address: String, phone: String,
        outlet_class_id: Int, outlet_language_id: Int, outlet_type_id: Int,
        custno: Int, lat: Double, lng: Double
    ) {
        repository.reSetCustomerProfile(
            outletname,
            contactname,
            address,
            phone,
            outlet_class_id,
            outlet_language_id,
            outlet_type_id,
            custno,
            lat,
            lng
        )
            .subscribe({
                if (it.isSuccessful && it.body() != null && it.code() == 200 && it.body()!!.status == 200) {

                    Log.d(TAG, it.body().toString())
                    updareLocalCustomer(
                        outletname,
                        lat.toString(),
                        lng.toString(),
                        custno.toString(),
                        it.body()!!.status,
                        it.body()!!.msg
                    )
                } else {
                    response.msg = it.body()!!.msg
                    response.status = it.body()!!.status
                    mResponse.postValue(response)
                }
            }, {
                response.msg = it.message
                response.status = 500
                mResponse.postValue(response)
            }).isDisposed
    }

    fun updareLocalCustomer(outletname: String, lat: String, lng: String, urno: String, status: Int, msg: String) {
        repository.updareLocalCustomer(outletname, lat, lng, urno)
            .subscribe({
                response.msg = msg
                response.status = status
                mResponse.postValue(response)
            }, {
                response.msg = it.message
                response.status = 500
                mResponse.postValue(response)
            }).isDisposed
    }

    companion object {
        var TAG = "CustomerViewModel"
    }

}