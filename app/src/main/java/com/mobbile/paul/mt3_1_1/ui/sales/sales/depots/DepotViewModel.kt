package com.mobbile.paul.mt3_1_1.ui.sales.sales.depots

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mobbile.paul.mt3_1_1.models.EmployeesApi
import com.mobbile.paul.mt3_1_1.models.ProductsRoom
import com.mobbile.paul.mt3_1_1.models.totalSumProductEntry
import com.mobbile.paul.mt3_1_1.providers.Repository
import javax.inject.Inject

class DepotViewModel @Inject constructor(private val repository: Repository) : ViewModel() {


    fun fetchSoldItems(): LiveData<List<ProductsRoom>> {
        var mResult = MutableLiveData<List<ProductsRoom>>()
        repository.fetchSoldItems()
            .subscribe({
                mResult.postValue(it)
            }, {
                mResult.postValue(null)
            }).isDisposed
        return mResult
    }

    fun sumProductEntry(): LiveData<totalSumProductEntry> {
        var mResult = MutableLiveData<totalSumProductEntry>()
        repository.sumProductEntry()
            .subscribe({
                mResult.postValue(it)
            }, {
                mResult.postValue(null)
            }).isDisposed
        return mResult
    }

    fun getTask(
        userid: Int,
        taskid: Int,
        dates: String,
        times: String
    ): MutableLiveData<EmployeesApi> {

        var nResult = MutableLiveData<EmployeesApi>()

        repository.OtherTask(userid, taskid, dates, times)
            .subscribe({
                if (it != null) {
                    Log.d(TAG, it.body().toString())
                    updateCust(4, times)
                    nResult.postValue(it.body())
                }
            }, {
                nResult.postValue(null)
            }).isDisposed

        return nResult
    }

    fun updateCust(sort: Int, rostertime: String) {
        repository.updateCust(sort, rostertime)
            .subscribe().isDisposed
    }

    companion object {
        var TAG = "DepotViewModel"
    }


}


