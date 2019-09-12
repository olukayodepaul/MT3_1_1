package com.mobbile.paul.mt3_1_1.ui.customers

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.kotlin_project.providers.Repository
import com.mobbile.paul.mt3_1_1.models.Attendance
import com.mobbile.paul.mt3_1_1.models.ProductTypeRoom
import com.mobbile.paul.mt3_1_1.models.RepCustomers
import javax.inject.Inject

class CustomerViewModel  @Inject constructor(private val repository: Repository) : ViewModel(){

    var vals: Attendance? = null

    var mutaleResult = MutableLiveData<Attendance>()

    fun MutableOfLiveData(): LiveData<Attendance> {
        return mutaleResult
    }

    fun getAllCustomers(user_id: Int) : LiveData<List<RepCustomers>> {
        var mResult = MutableLiveData<List<RepCustomers>>()
        repository.getAllCustomers(user_id)
            .subscribe({
                if(it.body()!!.status==200) {
                    mResult.postValue(it.body()!!.repcustomers)
                }else {
                    mResult.postValue(null)
                }
            },{
                mResult.postValue(null)
            }).isDisposed
        return mResult
    }

    fun fetchSpinners() : LiveData<List<ProductTypeRoom>> {
        var mResult = MutableLiveData<List<ProductTypeRoom>>()

        repository.fetchSpinners()
            .subscribe({
                mResult.postValue(it)
            },{
                mResult.postValue(null)
            }).isDisposed
        return mResult
    }

    fun reSetCustomerProfile(outletname: String, contactname: String, address: String, phone : String,
                             outlet_class_id: Int, outlet_language_id : Int, outlet_type_id : Int,
                             custno : Int, lat: Double, lng: Double) {
        repository.reSetCustomerProfile(outletname, contactname, address, phone, outlet_class_id, outlet_language_id, outlet_type_id, custno, lat, lng)
            .subscribe({
                vals = it!!.body()
                updareLocalCustomer(outletname,lat.toString(), lng.toString(), custno.toString())
            },{
                Log.d(TAG, it.message)
                mutaleResult.postValue(null)
            }).isDisposed
    }

    fun updareLocalCustomer(outletname: String, lat: String, lng: String, urno: String) {
        repository.updareLocalCustomer(outletname, lat, lng, urno)
            .subscribe({
                mutaleResult.postValue(vals)
            },{
                mutaleResult.postValue(null)
            }).isDisposed
    }

    companion object{
        var TAG = "CustomerViewModel"
    }

}