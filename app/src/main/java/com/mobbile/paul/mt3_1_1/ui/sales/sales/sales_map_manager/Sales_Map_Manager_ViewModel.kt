package com.mobbile.paul.mt3_1_1.ui.sales.sales.sales_map_manager

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.kotlin_project.providers.Repository
import javax.inject.Inject
import com.google.gson.Gson



class Sales_Map_Manager_ViewModel @Inject constructor(val repository: Repository):   ViewModel() {

    fun GoogleMapApi(origin: String, destination: String, sensor: String, mode: String, key: String){

        repository.fetchGoogleMap(origin, destination, sensor, mode, key)
            .subscribe(
                { data ->
                    val gson = Gson()
                    Log.d(TAG, gson.toJson(data))
                },
                { error ->

                }).isDisposed
    }

    companion object{
        var TAG = "Sales_Map_Manager_ViewModel"
    }

}