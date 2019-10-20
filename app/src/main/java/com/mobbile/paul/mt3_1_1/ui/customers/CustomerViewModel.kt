package com.mobbile.paul.mt3_1_1.ui.customers


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mobbile.paul.mt3_1_1.models.RepCustomers
import com.mobbile.paul.mt3_1_1.providers.Repository
import javax.inject.Inject

class CustomerViewModel @Inject constructor(private val repository: Repository) : ViewModel() {

    var mResponse = MutableLiveData<String>()

    fun mResponse(): LiveData<String> {
        return mResponse
    }

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

    fun updareLocalCustomer(outletname: String, lat: String, lng: String, urno: String) {
        repository.updareLocalCustomer(outletname, lat, lng, urno)
            .subscribe({
                mResponse.postValue("OK")
            }, {
                mResponse.postValue("FAIL")
            }).isDisposed
    }

    fun geos(urno: Int) {
        repository.getGoe(urno)
            .subscribe({
                updareLocalCustomer(it.body()!!.outletname, it.body()!!.lat,it.body()!!.lng, it.body()!!.urno.toString() )
            }, {
            }).isDisposed
    }

    companion object {
        var TAG = "CustomerViewModel"
    }



}