package com.mobbile.paul.mt3_1_1.ui.sales.sales.salesentries


import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mobbile.paul.mt3_1_1.models.*
import com.mobbile.paul.mt3_1_1.providers.Repository
import javax.inject.Inject

class SalesEntriesViewModel @Inject constructor(private val repository: Repository) : ViewModel() {

    var mResult = MutableLiveData<String>()

    var mData = MutableLiveData<List<JoinSalesEntriesAndProducts>>()

    lateinit var data: GenSales

    fun returnStringObservable(): MutableLiveData<String> {
        return mResult
    }

    fun returnSalesData(): LiveData<List<JoinSalesEntriesAndProducts>> {
        return mData
    }

    fun fetchSales(urno: String, customerno: String, employee_id: Int) {

        repository.getbasket(urno, customerno, employee_id)
            .subscribe(
                {
                    Log.d(TAG, it.body()!!.status.toString())
                    when (it.body()!!.status) {
                        200 -> {
                            data = it.body()!!
                            Log.d(TAG, data.toString())
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
                {
                    Log.d(TAG, it.message.toString())
                    mResult.postValue(it.message)
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

            sdata.sentry.map { it.toSalesEntriesEntity() },
            sdata.sentryh.map { it.toSalesEntrieHolderEntity() }


        ).subscribe({
            fetchDailySales()
        }, {
            Log.d(TAG, it.message.toString())
            mResult.postValue(it.message)
        }
        ).isDisposed
    }

    fun fetchDailySales() {
        repository.getAllSalesEntries()
            .subscribe({
                mData.postValue(it)
            }, {
                mResult.postValue(it.message)
            }).isDisposed
    }

    fun validateEntryStatus(): MutableLiveData<Int> {
        var mResult = MutableLiveData<Int>()
        repository.validateSalesEntry()
            .subscribe({
                mResult.postValue(it)
                Log.d(TAG, it.toString())
            }, {
                Log.d(TAG, it.message.toString())
                mResult.postValue(null)
            }).isDisposed
        return mResult
    }

    companion object {
        var TAG = "SalesEntriesViewModel"
    }
}


