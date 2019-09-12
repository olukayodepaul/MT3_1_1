package com.mobbile.paul.mt3_1_1.ui.sales.sales.sales_map_manager


import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.kotlin_project.providers.Repository
import com.mobbile.paul.mt3_1_1.models.*
import javax.inject.Inject


class SalesMapManagerViewModel @Inject constructor(val repository: Repository):   ViewModel() {

    var rs  = salesEntryResponses()

    var nResult = MutableLiveData<salesEntryResponses>()

    fun MutableProcess(): MutableLiveData<salesEntryResponses>{
        return nResult
    }

    fun GoogleMapApi(origin: String, destination: String, sensor: String, mode: String, key: String)
            : MutableLiveData<GoogleGetApi>{

        var mResult = MutableLiveData<GoogleGetApi>()

        repository.fetchGoogleMap(origin, destination, sensor, mode, key)
            .subscribe(
                {
                    mResult.postValue(it)
                },
                {
                    mResult.postValue(null)
                }).isDisposed
        return mResult
    }

    fun confirmTask(user_id: Int, dates: String, curlat: Double, curlng : Double) : MutableLiveData<salesEntryResponses> {

        var qResult = MutableLiveData<salesEntryResponses>()

        repository.confirmTask(user_id, dates)
            .subscribe({
                rs.status = it.body()!!.status
                rs.msg = it.body()!!.msg
                rs.curlat = curlat
                rs.curlng = curlng
                qResult.postValue(rs)
                Log.d(TAG, "End point confirmation api ${it.body()}")
            },{
                qResult.postValue(null)
            })
            .isDisposed

        return qResult
    }

    //Google api distance did not use this api to calculate geofencing
    private fun calculateDistance(units: String, origins: String, destinations: String, key: String, data: EmployeesApi) {

        repository.fetchGoogleDistance(units, origins, destinations, key)
            .subscribe({
                if(it!=null){
                    if(it.status == "OK") {

                        if(data!!.status==200){
                            rs.msg = data.msg
                            rs.status = data.status
                            nResult.postValue(rs)
                        }else{
                            rs.msg = data.msg
                            rs.status = data.status
                            nResult.postValue(rs)
                        }
                    }
                }
            },{
                rs.msg = it.message.toString()
                rs.status = 400
                nResult.postValue(rs)
            }).isDisposed
    }

    fun setOutletClose(userid: Int, urno: String, dates: String, times: String, lat: String, lng: String, distance: String, visitsequence: String) : LiveData<postRecieveClose> {

        var qResult = MutableLiveData<postRecieveClose>()

        repository.setOutletClose(userid, urno, dates, times, lat, lng, distance, visitsequence)
            .subscribe({
                updateCustTrans(urno.toInt(), "Close $times")
                qResult.postValue(it.body()!!)
                Log.d(TAG, it.body()!!.toString())
            },{
                qResult.postValue(null)
            })
            .isDisposed

        return qResult
    }

    fun updateCustTrans(urno: Int, rostertime: String) {
        repository.updateCustTrans(urno, rostertime)
            .subscribe({
            },{
            }).isDisposed
    }

    companion object{
        var TAG = "SalesMapManagerViewModel"
    }
}

