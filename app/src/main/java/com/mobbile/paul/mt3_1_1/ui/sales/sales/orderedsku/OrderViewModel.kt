package com.mobbile.paul.mt3_1_1.ui.sales.sales.orderedsku


import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.kotlin_project.providers.Repository
import com.mobbile.paul.mt3_1_1.models.*
import javax.inject.Inject

class OrderViewModel @Inject constructor(private val repository: Repository): ViewModel() {

    lateinit var allData: postRecieveFromServer

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
                if(it != null) {
                    allData = it.body()!!
                    saveEntryHistory()
                }
            },{

            }).isDisposed
    }

    fun saveEntryHistory() {

        var mappers =  repSalesHistoryApi()
        mappers.outletname = allData.outletname
        mappers.times = allData.times
        mappers.urno = allData.urno
        mappers.outletstatus = allData.outletstatus

        //insert sales history with mappers
        repository.saveEntryHistory(mappers.toCustomersEntity())
            .subscribe({

                var upDate: List<UpdateProdcts> = allData.updateproductlist

                for (i in 0..(upDate.size)) {

                    UpdateDailySales(
                        upDate.get(i).totalqtysold,
                        upDate.get(i).balanceamount,
                        upDate.get(i).totalcommission,
                        upDate.get(i).balanceamount,
                        upDate.get(i).product_code
                    )

                    Log.d(TAG, upDate.size.toString()+" "+i.toString())

                    if(i==upDate.size-1) {

                    }
                }
            },{

            }).isDisposed
    }


    fun UpdateDailySales(totalq :  Double, totalamt : Double, totalcomm : Double, balanceamt : Double, productcode : String){
        repository.updateProducts(totalq,totalamt,totalcomm,balanceamt,productcode )
            .subscribe().isDisposed
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