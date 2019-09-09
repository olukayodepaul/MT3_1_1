package com.mobbile.paul.mt3_1_1.ui.sales.sales_history.salesdetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.kotlin_project.providers.Repository
import com.mobbile.paul.mt3_1_1.models.OutletSalesHistoryDetails
import com.mobbile.paul.mt3_1_1.models.totalSumProductEntry
import javax.inject.Inject

class SalesDetailsViewModel @Inject constructor(val repository: Repository): ViewModel(){



    fun outletSales(token: Int) : LiveData<List<OutletSalesHistoryDetails>> {
        var mResult = MutableLiveData<List<OutletSalesHistoryDetails>>()
        repository.outletSales(token)
            .subscribe({
                mResult.postValue(it.body()!!.shisto)
            },{
                mResult.postValue(null)
            }).isDisposed
        return mResult
    }



    companion object {
        var TAG = "SalesDetailsViewModel"
    }

}