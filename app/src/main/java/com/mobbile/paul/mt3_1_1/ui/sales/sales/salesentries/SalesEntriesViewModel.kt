package com.mobbile.paul.mt3_1_1.ui.sales.sales.salesentries

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.kotlin_project.providers.Repository
import com.mobbile.paul.mt3_1_1.models.SalesEntriesRoom
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class SalesEntriesViewModel @Inject constructor(val repository: Repository) : ViewModel() {

      fun fetchDailySales(): LiveData<List<SalesEntriesRoom>> {

          var mResult = MutableLiveData<List<SalesEntriesRoom>>()
          repository.fetchDailySales()
              .subscribeOn(Schedulers.io())
              .observeOn(AndroidSchedulers.mainThread())
              .subscribe({
                  mResult.postValue(it)
                  Log.d(TAG,"onObserve "+ it as ArrayList<SalesEntriesRoom>)
              },{
                  mResult.postValue(null)
              }).isDisposed
          return mResult
      }

    companion object {
        var TAG = "SalesEntriesViewModel"
    }

}


