package com.mobbile.paul.mt3_1_1.ui.sales.sales.sales_attendant

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mobbile.paul.mt3_1_1.models.ProductsRoom
import com.example.kotlin_project.providers.Repository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject


class SalesAttendantViewModel @Inject constructor(val repository: Repository): ViewModel() {

    fun fetchBasket(sep: Int): LiveData<List<ProductsRoom>> {

        var mResult = MutableLiveData<List<ProductsRoom>>()

        repository.fetchBasket(sep)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                mResult.postValue(it)
                Log.d(TAG,"onObserve "+ it as ArrayList<ProductsRoom>)
            },{
                mResult.postValue(null)
            }).isDisposed

        return mResult
    }

    companion object {
        private val TAG = "ModulesViewMode"
    }
}