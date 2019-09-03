package com.mobbile.paul.mt3_1_1.ui.sales.sales.orderedsku


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.kotlin_project.providers.Repository
import com.mobbile.paul.mt3_1_1.models.*
import javax.inject.Inject

class OrderViewModel @Inject constructor(private val repository: Repository): ViewModel() {


    fun fetch() : LiveData<List<SalesEntrieHolderRoom>> {

        var mResult = MutableLiveData<List<SalesEntrieHolderRoom>>()

        repository.fetchAllEntryPerDaily()
            .subscribe({
                mResult.postValue(it)
            },{
                mResult.postValue(null)
            }).isDisposed

        return mResult
    }


    fun sumAllSalesEntry() : LiveData<SumSales> {

        var mResult = MutableLiveData<SumSales>()

        repository.sumAllSalesEntry()
            .subscribe({
                mResult.postValue(it)
            },{
                mResult.postValue(null)
            }).isDisposed

        return mResult
    }

    fun postSalesToServer(employee_id: Int, urno: String,token: String,
                          distance: String, arrivaltime: String,
                          departuretime: String, dates: String, arrivallat: String, arrivallng: String,
                          saleslist: List<SalesEntrieHolderApi>, visit_sequence : String) {

        var list = postToServer()
        list.employee_id = employee_id
        list.urno = urno
        list.token = token
        list.distance = distance
        list.arrivaltime = arrivaltime
        list.departuretime = departuretime
        list.dates = dates
        list.arrivallat = arrivallat
        list.arrivallng = arrivallng
        list.visitsequence = visit_sequence
        list.saleslist = saleslist

        repository.fetchPostSales(list)
            .subscribe({

            },{

            }).isDisposed
    }

    fun pullAllSalesEntry() : LiveData<List<SalesEntrieHolderApi>> {

        var mResult = MutableLiveData<List<SalesEntrieHolderApi>>()

        repository.pullAllSalesEntry()
            .subscribe({data->
                mResult.postValue(data.map{it.toSalesHolderEntity()})
            },{
                mResult.postValue(null)
            }).isDisposed

        return mResult
    }

    companion object{
        var TAG = "OrderViewModel"
    }

}