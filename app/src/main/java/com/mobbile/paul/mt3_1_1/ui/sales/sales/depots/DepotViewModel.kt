package com.mobbile.paul.mt3_1_1.ui.sales.sales.depots

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.kotlin_project.providers.Repository
import com.mobbile.paul.mt3_1_1.models.ProductsRoom
import com.mobbile.paul.mt3_1_1.models.totalSumProductEntry
import javax.inject.Inject

class DepotViewModel @Inject constructor(private val repository: Repository): ViewModel(){


    fun fetchSoldItems() : LiveData<List<ProductsRoom>> {
        var mResult = MutableLiveData<List<ProductsRoom>>()
        repository.fetchSoldItems()
            .subscribe({
                mResult.postValue(it)
            },{
                mResult.postValue(null)
            }).isDisposed
        return mResult
    }

    fun sumProductEntry() : LiveData<totalSumProductEntry> {
        var mResult = MutableLiveData<totalSumProductEntry>()
        repository.sumProductEntry()
            .subscribe({
                mResult.postValue(it)
            },{
                mResult.postValue(null)
            }).isDisposed
        return mResult
    }

    companion object {
        var TAG = "DepotViewModel"
    }
}


