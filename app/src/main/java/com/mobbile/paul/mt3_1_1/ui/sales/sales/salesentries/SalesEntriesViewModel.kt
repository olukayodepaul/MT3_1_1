package com.mobbile.paul.mt3_1_1.ui.sales.sales.salesentries


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.kotlin_project.providers.Repository
import com.mobbile.paul.mt3_1_1.models.*
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class SalesEntriesViewModel @Inject constructor(val repository: Repository) : ViewModel() {

    fun fetchSales(userid: String): MutableLiveData<SalesEntriesRoom> {
        var mResult = MutableLiveData<SalesEntriesRoom>()
        repository.getUsers(userid)
            .subscribe(
                { data ->
                    when (data.body()!!.status) {
                        200->{
                            insertSales(data.body()!!.sentry, data.body()!!.sentryh)
                        }
                    }
                },
                { error ->
                }).isDisposed

        return mResult
    }

    private fun insertSales(
        salesen: List<SalesEntriesApi>,
        salesh: List<SalesEntrieHolderApi>
    ) {
        repository.createDailySales(
            salesen.map{it.toSalesEntriesEntity()},
            salesh.map{it.toSalesEntrieHolderEntity()}
        ).subscribe(
        ).isDisposed
    }

    fun fetchDailySales(): LiveData<List<SalesEntriesRoom>> {
        var mResult = MutableLiveData<List<SalesEntriesRoom>>()
        repository.fetchDailySales()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                mResult.postValue(it)
            },{
                mResult.postValue(null)
            }).isDisposed
        return mResult
    }

    companion object {
        var TAG = "SalesEntriesViewModel"
    }
}


