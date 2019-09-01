package com.mobbile.paul.mt3_1_1.ui.sales.sales.sales_attendant

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mobbile.paul.mt3_1_1.models.ProductsRoom
import com.example.kotlin_project.providers.Repository
import com.mobbile.paul.mt3_1_1.models.EmployeesApi
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList


class SalesAttendantViewModel @Inject constructor(val repository: Repository): ViewModel() {


    fun fetchBasket(sep: Int): LiveData<List<ProductsRoom>> {

        var mResult = MutableLiveData<List<ProductsRoom>>()

        repository.fetchBasket(sep)
            .subscribe({
                mResult.postValue(it)
                Log.d(TAG,"onObserve "+ it as ArrayList<ProductsRoom>)
            },{
                mResult.postValue(null)
            }).isDisposed

        return mResult
    }

    fun getTask(userid: Int, taskid: Int, dates: String, times: String): MutableLiveData<EmployeesApi> {

        var nResult = MutableLiveData<EmployeesApi>()

        repository.getTask(userid, taskid, dates, times)
            .subscribe({
                if (it != null) {
                    if(taskid==2){
                        var timeStamp = SimpleDateFormat("HH:mm:ss").format(Date())
                        updateCust(1, timeStamp)
                    }
                    nResult.postValue(it.body())
                    Log.d(TAG, "${taskid.toString()} this is to verify the taskid")
                }
            },{
                nResult.postValue(null)
            }).isDisposed

        return nResult
    }



    fun updateCust(sort: Int, rostertime: String){
        repository.updateCust(sort, rostertime)
            .subscribe().isDisposed
    }

    companion object {
        private val TAG = "ModulesViewMode"
    }
}