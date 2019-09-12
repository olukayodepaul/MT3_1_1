package com.mobbile.paul.mt3_1_1.ui.sales.commission


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.kotlin_project.providers.Repository
import com.mobbile.paul.mt3_1_1.models.salesCommissionList
import javax.inject.Inject

class SalesCommissionFragmentViewModel @Inject constructor(val repository: Repository): ViewModel() {


    fun conputSalesCom(user_id: Int, dates: String) : LiveData<List<salesCommissionList>> {
        var mResult = MutableLiveData<List<salesCommissionList>>()
        repository.conputSalesCom(user_id, dates)
            .subscribe({
                if(it.body()!!.status==200){
                    mResult.postValue(it!!.body()!!.comlist)
                }else{
                    mResult.postValue(null)
                }
            },{
                mResult.postValue(null)
            }).isDisposed
        return mResult
    }

    companion object{
        var TAG = "SalesCommissionFragmentViewModel"
    }
}