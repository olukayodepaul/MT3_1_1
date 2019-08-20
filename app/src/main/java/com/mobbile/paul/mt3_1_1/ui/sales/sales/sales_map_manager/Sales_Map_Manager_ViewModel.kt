package com.mobbile.paul.mt3_1_1.ui.sales.sales.sales_map_manager


import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.kotlin_project.providers.Repository
import com.mobbile.paul.mt3_1_1.models.GoogleGetApi
import javax.inject.Inject


class Sales_Map_Manager_ViewModel @Inject constructor(val repository: Repository):   ViewModel() {

    fun GoogleMapApi(origin: String, destination: String, sensor: String, mode: String, key: String)
            : MutableLiveData<GoogleGetApi>{

        var mResult = MutableLiveData<GoogleGetApi>()

        repository.fetchGoogleMap(origin, destination, sensor, mode, key)
            .subscribe(
                { data ->
                    Log.d(TAG, data!!.toString())
                    mResult.postValue(data)
                },
                { error ->
                    mResult.postValue(null)
                }).isDisposed
        return mResult
    }

    companion object{
        var TAG = "Sales_Map_Manager_ViewModel"
    }
}