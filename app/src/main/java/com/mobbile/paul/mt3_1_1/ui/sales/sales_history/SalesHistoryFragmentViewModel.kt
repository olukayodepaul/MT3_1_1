package com.mobbile.paul.mt3_1_1.ui.sales.sales_history

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.kotlin_project.providers.Repository
import com.mobbile.paul.mt3_1_1.models.repSalesHistoryRoom
import javax.inject.Inject

class SalesHistoryFragmentViewModel @Inject constructor(val repository: Repository): ViewModel(){



    fun pullAllSalesEntry() : LiveData<List<repSalesHistoryRoom>> {

        var mResult = MutableLiveData<List<repSalesHistoryRoom>>()

        repository.fetchAllSalesEntries()
            .subscribe({
                mResult.postValue(it)
            },{
                mResult.postValue(null)
            }).isDisposed

        return mResult
    }

    companion object {
        var TAG = "SalesFragmentViewModel"
    }

}