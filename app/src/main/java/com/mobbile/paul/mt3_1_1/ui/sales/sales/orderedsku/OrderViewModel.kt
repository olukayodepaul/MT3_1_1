package com.mobbile.paul.mt3_1_1.ui.sales.sales.orderedsku


import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mobbile.paul.mt3_1_1.models.*
import com.mobbile.paul.mt3_1_1.providers.Repository
import javax.inject.Inject

class OrderViewModel @Inject constructor(private val repository: Repository): ViewModel() {

    lateinit var allData: postRecieveFromServer

    var mutableRespose = MutableLiveData<String>()

    fun setMutableResponse(): MutableLiveData<String> {
        return mutableRespose
    }

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
                          departuretime: String, dates: String, arrivallat: Double, arrivallng: Double,
                          saleslist: List<SalesEntrieHolderApi>, visit_sequence : String,
                          outletname:String, durations:String) {

        val list = postToServer()
        list.employee_id = employee_id
        list.urno = urno
        list.token = token
        list.distance = distance
        list.arrivaltime = arrivaltime
        list.departuretime = departuretime
        list.dates = dates
        list.arrivallat = arrivallat.toString()
        list.arrivallng = arrivallng.toString()
        list.visitsequence = visit_sequence
        list.saleslist = saleslist
        list.outletname = outletname
        list.tripduration = durations

        repository.fetchPostSales(list)
            .subscribe({
                if(it != null) {
                    allData = it.body()!!
                    updateCustTrans(urno.toInt(), "open $arrivaltime")
                }
            },{
                mutableRespose.postValue("400~$it.message")
            }).isDisposed
    }


    fun saveEntryHistory() {
        var mappers =  repSalesHistoryApi()
        mappers.outletname = allData.outletname
        mappers.times = allData.times
        mappers.urno = allData.urno
        mappers.outletstatus = allData.outletstatus
        repository.saveEntryHistory(mappers.toCustomersEntity()) //save sales sales history then update the product table
            .subscribe({

               var upDate: List<UpdateProdcts> = allData.updateproductlist

                for (i in 0 until upDate.count()) {
                    UpdateDailySales(
                        upDate[i].totalqtysold,
                        upDate[i].balanceamount,
                        upDate[i].totalcommission,
                        upDate[i].balanceamount,
                        upDate[i].product_code
                    )
                }
                mutableRespose.postValue("200~Sales successfully entered. Thanks!")
            },{
                mutableRespose.postValue("400~$it.message")
            }).isDisposed
    }

    fun UpdateDailySales(totalq :  Double, totalamt : Double, totalcomm : Double, balanceamt : Double, productcode : String) {
        repository.updateProducts(totalq,totalamt,totalcomm,balanceamt,productcode )
            .subscribe({
            },{
                mutableRespose.postValue("400~$it.message")
            }).isDisposed
    }

    fun updateCustTrans(urno: Int, rostertime: String) {
        repository.updateCustTrans(urno, rostertime)
            .subscribe({
                saveEntryHistory()
            },{
                mutableRespose.postValue("400~$it.message")
            }).isDisposed
    }

    fun pullAllSalesEntry() : LiveData<List<SalesEntrieHolderApi>> {

        var mResult = MutableLiveData<List<SalesEntrieHolderApi>>()

        repository.pullAllSalesEntry()
            .subscribe({data->
                mResult.postValue(data.map{it.toSalesHolderEntity()})
            },{
                mutableRespose.postValue("400~$it.message")
            }).isDisposed

        return mResult
    }

    companion object{
        var TAG = "OrderViewModels"
    }

}