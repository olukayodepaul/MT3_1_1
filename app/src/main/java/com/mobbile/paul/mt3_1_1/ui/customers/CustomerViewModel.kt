package com.mobbile.paul.mt3_1_1.ui.customers

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.kotlin_project.providers.Repository
import com.mobbile.paul.mt3_1_1.models.ProductTypeRoom
import com.mobbile.paul.mt3_1_1.models.RepCustomers
import javax.inject.Inject

class CustomerViewModel  @Inject constructor(private val repository: Repository) : ViewModel(){

    fun getAllCustomers(user_id: Int) : LiveData<List<RepCustomers>> {

        var mResult = MutableLiveData<List<RepCustomers>>()

        repository.getAllCustomers(user_id)
            .subscribe({
                if(it.body()!!.status==200){
                    mResult.postValue(it.body()!!.repcustomers)
                }else{
                    mResult.postValue(null)
                }
            },{
                mResult.postValue(null)
            }).isDisposed
        return mResult
    }

    fun fatchSpinners(types: Int) : LiveData<List<ProductTypeRoom>> {

        var mResult = MutableLiveData<List<ProductTypeRoom>>()

        repository.fatchSpinners(types)
            .subscribe({
                mResult.postValue(it)
            },{
                mResult.postValue(null)
            }).isDisposed
        return mResult
    }
}