package com.mobbile.paul.mt3_1_1.ui.sales.sales.salesentries


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.kotlin_project.providers.Repository
import com.mobbile.paul.mt3_1_1.models.*
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class SalesEntriesViewModel @Inject constructor(private val repository: Repository) : ViewModel() {

    var mResult = MutableLiveData<String>()

    var mData = MutableLiveData<List<SalesEntriesRoom>>()

    lateinit var data: GenSales

    fun returnStringObservable(): MutableLiveData<String> {
        return mResult
    }

    fun returnSalesData(): LiveData<List<SalesEntriesRoom>> {
        return mData
    }

    fun fetchSales(urno: String, customerno: String) {

        repository.getUsers(urno, customerno)
            .subscribe(
                {
                    when (it.body()!!.status) {
                        200 -> {
                            data = it.body()!!
                            if (it.body() != null) {
                                deleteSalesEntry()
                            } else {
                                mResult.postValue("Api error")
                            }
                        }
                        else -> {
                            mResult.postValue("Api error")
                        }
                    }
                },
                { error ->
                    mResult.postValue(error.message)
                }).isDisposed
    }

    private fun deleteSalesEntry() {
        repository.deleteSalesEntry()
            .subscribe({
                insertSales(data)
            }, {
                mResult.postValue(it.message)
            }).isDisposed
    }

    private fun insertSales(sdata: GenSales) {
        repository.createDailySales(
            sdata.sentry.map {it.toSalesEntriesEntity()},
            sdata.sentryh.map {it.toSalesEntrieHolderEntity()}
        ).subscribe({
            fetchDailySales()
        }, {
            mResult.postValue(it.message)
        }
        ).isDisposed
    }

    fun fetchDailySales() {
        repository.fetchDailySales()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                mData.postValue(it)
            }, {
                mResult.postValue(it.message)
            }).isDisposed
    }

    companion object {
        var TAG = "SalesEntriesViewModel"
    }
}


