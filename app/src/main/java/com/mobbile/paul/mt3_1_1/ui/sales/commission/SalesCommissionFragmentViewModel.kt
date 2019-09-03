package com.mobbile.paul.mt3_1_1.ui.sales.commission

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.kotlin_project.providers.Repository
import javax.inject.Inject

class SalesCommissionFragmentViewModel @Inject constructor(val repository: Repository): ViewModel(){

    fun tect(){
        Log.d(TAG,"Onsamples")
    }

    companion object{
        var TAG = "SalesCommissionFragmentViewModel"
    }
}