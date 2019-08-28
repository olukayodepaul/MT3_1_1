package com.mobbile.paul.mt3_1_1.ui.modules


import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mobbile.paul.mt3_1_1.models.ModulesRoom
import com.example.kotlin_project.providers.Repository
import javax.inject.Inject

class ModulesViewModel @Inject constructor(val repository: Repository) : ViewModel() {



    fun getAllUsersModules(): LiveData<List<ModulesRoom>> {

        var mResult = MutableLiveData<List<ModulesRoom>>()

        repository.fetchModules()
            .subscribe({
                mResult.postValue(it)
                Log.d(TAG,"onObserve "+ it as ArrayList<ModulesRoom>)
            },{
                mResult.postValue(null)
            }).isDisposed

        return mResult
    }

    companion object {
        private val TAG = "ModulesViewMode"
    }

}