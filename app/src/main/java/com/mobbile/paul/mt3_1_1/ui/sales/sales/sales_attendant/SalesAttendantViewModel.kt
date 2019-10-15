package com.mobbile.paul.mt3_1_1.ui.sales.sales.sales_attendant

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mobbile.paul.mt3_1_1.models.ProductsRoom
import com.mobbile.paul.mt3_1_1.providers.Repository
import com.mobbile.paul.mt3_1_1.viewmodel.Attendant
import javax.inject.Inject
import kotlin.collections.ArrayList


class SalesAttendantViewModel @Inject constructor(private val repository: Repository) : ViewModel() {

    var mResponse = MutableLiveData<Attendant>()

    var response = Attendant()

    fun mResponse(): LiveData<Attendant> {
        return mResponse
    }

    fun fetchBasket(sep: Int): LiveData<List<ProductsRoom>> {

        var mResult = MutableLiveData<List<ProductsRoom>>()

        repository.fetchBasket(sep)
            .subscribe({
                mResult.postValue(it)
                Log.d(TAG, "onObserve " + it as ArrayList<ProductsRoom>)
            }, {
                mResult.postValue(null)
            }).isDisposed
        return mResult
    }

    fun getTask(userid: Int, taskid: Int, dates: String, times: String) {

        repository.getTask(userid, taskid, dates, times)
            .subscribe({
                if (it.isSuccessful && it.body() != null && it.code() == 200 && it.body()!!.status == 200) {
                    when (taskid) {
                        1 -> {
                            response.msg = it.body()!!.msg
                            response.status = it.body()!!.status
                            response.taskid = taskid
                            mResponse.postValue(response)
                        }
                        2 -> {
                            updateCust(1, times, taskid, it.body()!!.msg)
                        }
                    }
                } else {
                    response.msg = it.body()!!.msg
                    response.status = it.body()!!.status
                    response.taskid = taskid
                    mResponse.postValue(response)
                }
            }, {
                response.msg = it.message
                response.status = 100000
                response.taskid = taskid
                mResponse.postValue(response)
            }).isDisposed
    }

    fun updateCust(sort: Int, rosterTime: String, taskid: Int, msg: String) {
        repository.updateCust(sort, rosterTime)
            .subscribe({
                response.msg = msg
                response.status = 200
                response.taskid = taskid
                mResponse.postValue(response)
            },{
                response.msg = it.message
                response.status = 100000
                response.taskid = taskid
                mResponse.postValue(response)

            }).isDisposed
    }

    companion object {
        private val TAG = "ModulesViewMode"
    }
}