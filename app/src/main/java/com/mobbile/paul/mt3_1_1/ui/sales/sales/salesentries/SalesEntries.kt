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
        vmodel.fetchDailySales().observe(this, observers)
    }

    val observers = Observer<List<SalesEntriesRoom>> { data ->
        if (data != null) {

        }
    }



}
