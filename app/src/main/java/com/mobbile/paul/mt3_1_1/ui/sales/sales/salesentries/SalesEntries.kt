package com.mobbile.paul.mt3_1_1.ui.sales.sales.salesentries


import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.mobbile.paul.mt3_1_1.R
import com.mobbile.paul.mt3_1_1.models.SalesEntriesRoom
import com.mobiletraderv.paul.daggertraining.BaseActivity
import javax.inject.Inject


class SalesEntries : BaseActivity() {

    @Inject
    internal lateinit var modelFactory: ViewModelProvider.Factory
    lateinit var vmodel: SalesEntriesViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sales_entries)
        vmodel = ViewModelProviders.of(this, modelFactory)[SalesEntriesViewModel::class.java]
        vmodel.fetchSales("215370", "SWC2780")
        vmodel.returnStringObservable().observe(this, observerOfApi)
    }

    val observerOfApi = Observer<String> {

        var rdata = it.split("~")
        when (rdata[0].toInt()) {
            200 -> {
                vmodel.fetchDailySales().observe(this, observerOfEntries)
            }
            400 -> {
                showProgressBar(false)
            }
        }
    }

    var observerOfEntries = Observer<List<SalesEntriesRoom>> {
        showProgressBar(false)
    }

    companion object {
        val TAG = "SalesEntries"
    }
}

