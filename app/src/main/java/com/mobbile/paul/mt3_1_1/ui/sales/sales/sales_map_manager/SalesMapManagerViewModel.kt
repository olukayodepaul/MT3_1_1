package com.mobbile.paul.mt3_1_1.ui.sales.sales.sales_map_manager


import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mobbile.paul.mt3_1_1.models.*
import com.mobbile.paul.mt3_1_1.providers.Repository
import com.mobbile.paul.mt3_1_1.viewmodel.CloseOutlets
import javax.inject.Inject


class SalesMapManagerViewModel @Inject constructor(val repository: Repository):   ViewModel() {

    var rs  = salesEntryResponses()

    var response = CloseOutlets()

    var nResult = MutableLiveData<CloseOutlets>()

    fun MutableProcess(): MutableLiveData<CloseOutlets>{
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
            },{
                qResult.postValue(null)
            })
            .isDisposed

        return qResult
    }

    fun setOutletClose(userid: Int, urno: String, dates: String, times: String, lat: String, lng: String, distance: String, visitsequence: String, outletname:String, durations:String) {

        repository.setOutletClose(userid, urno, dates, times, lat, lng, distance, visitsequence, outletname, durations)
            .subscribe({
                if(it.isSuccessful && it.body() != null && it.code() == 200 && it.body()!!.status == 200) {
                    Log.d(TAG, it.body().toString())
                    if(it.body()!!.avail==1){
                        updateCustTrans(urno.toInt(), "Close $times", it.body()!!.status)
                    }else{
                        response.status = 400
                        response.msg= "Outlet Close Successful. Thanks!"
                        nResult.postValue(response)
                    }
                }else{
                    response.status = 1000
                    response.msg = "Api Error. Thanks!"
                    nResult.postValue(response)
                }
            },{
                response.status = 1000
                response.msg= it.message!!
                nResult.postValue(response)
            })
            .isDisposed
    }

    fun updateCustTrans(urno: Int, rostertime: String, status: Int) {
        repository.updateCustTrans(urno, rostertime)
            .subscribe({
                response.status = status
                response.msg= "Outlet Close Successful. Thanks!"
                nResult.postValue(response)
            },{
                response.status = 1000
                response.msg= it.message!!
                nResult.postValue(response)
            }).isDisposed
    }

    //Google api distance. I  did not use this api to calculate geofencing
    /*private fun calculateDistance(units: String, origins: String, destinations: String, key: String, data: EmployeesApi) {

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
    }*/

    companion object{
        var TAG = "SalesMapManagerViewM"
    }
}

