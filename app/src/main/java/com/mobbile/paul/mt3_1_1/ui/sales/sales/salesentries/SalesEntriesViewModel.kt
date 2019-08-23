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

    fun returnStringObservable(): MutableLiveData<String> {
        return mResult
    }

    fun fetchSales(urno: String, customerno: String) {
        repository.getUsers(urno, customerno)
            .subscribe(
                {
                    when (it.body()!!.status) {
                        200 -> {
                            if (it.body() != null) {
                                insertSales(it.body()!!.sentry, it.body()!!.sentryh)
                                //Log.d(TAG, it.body().toString())
                            } else {
                                mResult.postValue("400~api error")
                            }
                        }
                        else -> {
                            mResult.postValue("400~api error")
                        }
                    }
                },
                { error ->
                    mResult.postValue("400~" + error.message)
                }).isDisposed
    }

    private fun insertSales(
        salesen: List<SalesEntriesApi>,
        salesh: List<SalesEntrieHolderApi>
    ) {
        repository.createDailySales(
            salesen.map { it.toSalesEntriesEntity() },
            salesh.map { it.toSalesEntrieHolderEntity() }
        ).subscribe({
            mResult.postValue("200~")
        }, { error ->
            mResult.postValue("400~" + error.message)
        }
        ).isDisposed
    }

    fun fetchDailySales(): LiveData<List<SalesEntriesRoom>> {

        var nResult = MutableLiveData<List<SalesEntriesRoom>>()

        repository.fetchDailySales()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                nResult.postValue(it)
            }, {
                nResult.postValue(null)
            }).isDisposed

        return nResult
    }

    companion object {
        var TAG = "SalesEntriesViewModel"
    }
}


