package com.mobbile.paul.mt3_1_1.ui.sales.commission


import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mobbile.paul.mt3_1_1.models.salesCommissionList
import com.mobbile.paul.mt3_1_1.providers.Repository
import javax.inject.Inject

class SalesCommissionFragmentViewModel @Inject constructor(val repository: Repository): ViewModel() {


    fun conputSalesCom(user_id: Int, dates: String) : LiveData<List<salesCommissionList>> {
        var mResult = MutableLiveData<List<salesCommissionList>>()
        repository.conputSalesCom(user_id, dates)
            .subscribe({
                if(it.body()!!.status==200){
                    Log.d(TAG, it!!.body()!!.comlist.toString())
                    mResult.postValue(it.body()!!.comlist)
                }else{
                    mResult.postValue(null)
                }
            },{
                Log.d(TAG, it.message.toString())
                mResult.postValue(null)
            }).isDisposed
        return mResult
    }

    companion object{
        var TAG = "SalesCommissionFragmentViewModel"
    }
}