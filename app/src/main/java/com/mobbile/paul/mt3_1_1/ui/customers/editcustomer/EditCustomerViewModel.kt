package com.mobbile.paul.mt3_1_1.ui.customers.editcustomer

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mobbile.paul.mt3_1_1.models.ProductTypeRoom
import com.mobbile.paul.mt3_1_1.providers.Repository
import javax.inject.Inject

class EditCustomerViewModel @Inject constructor(private val repository: Repository): ViewModel(){

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

    fun updateCards(employee_id: Int, urno: String, outletclassid: Int, outletlanguageid: Int, outlettypeid: Int,
                    outletname: String, outletaddress: String, contactname: String, contactphone: String, latitude: String,
                    longitude: String,  entry_date_time: String, entry_date: String
    ) : LiveData<String> {

        var mResult = MutableLiveData<String>()

        repository.updateCards(employee_id, urno, outletclassid, outletlanguageid, outlettypeid, outletname, outletaddress, contactname,
            contactphone, latitude, longitude, entry_date_time, entry_date)
            .subscribe({
                mResult.postValue(it.body()!!.status)
            },{
                mResult.postValue(it.message)
            }).isDisposed

        return mResult
    }

    companion object{
        val TAG = "EditCustomerViewModel"
    }


}