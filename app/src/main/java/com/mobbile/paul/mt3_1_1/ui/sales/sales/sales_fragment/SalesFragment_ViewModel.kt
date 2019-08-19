package com.mobbile.paul.mt3_1_1.ui.sales.sales.sales_fragment


import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.kotlin_project.Models.RoomModel.Bank_n_CustomersRoom
import com.example.kotlin_project.providers.Repository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class SalesFragment_ViewModel @Inject constructor(val repository: Repository): ViewModel()  {


    fun fetch() : LiveData<List<Bank_n_CustomersRoom>> {

        var mResult = MutableLiveData<List<Bank_n_CustomersRoom>>()

        repository.fetchCustomers()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                mResult.postValue(it)
                Log.d(TAG,"onObserve "+ it as ArrayList<Bank_n_CustomersRoom>)
            },{
                mResult.postValue(null)
            }).isDisposed
        Log.d(TAG, "log this infornation")
        return mResult
    }

    companion object{

        var TAG = "SalesFragment_ViewModel"
    }
}


