package com.mobbile.paul.mt3_1_1.ui.sales.commission


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.kotlin_project.providers.Repository
import com.mobbile.paul.mt3_1_1.models.salesCommisssion
import javax.inject.Inject

class SalesCommissionFragmentViewModel @Inject constructor(val repository: Repository): ViewModel() {


    fun conputSalesCom(user_id: Int, dates: String) : LiveData<salesCommisssion> {
        var mResult = MutableLiveData<salesCommisssion>()
        repository.conputSalesCom(user_id, dates)
            .subscribe({
                mResult.postValue(it!!.body())
            },{
                mResult.postValue(null)
            }).isDisposed
        return mResult
    }

    companion object{
        var TAG = "SalesCommissionFragmentViewModel"
    }
}