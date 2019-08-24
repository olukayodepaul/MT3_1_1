package com.mobbile.paul.mt3_1_1.ui.sales.sales.salesentries


import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mobbile.paul.mt3_1_1.R
import com.mobbile.paul.mt3_1_1.models.SalesEntriesRoom
import com.mobiletraderv.paul.daggertraining.BaseActivity
import kotlinx.android.synthetic.main.activity_sales_entries.*
import javax.inject.Inject


class SalesEntries : BaseActivity() {

    @Inject
    internal lateinit var modelFactory: ViewModelProvider.Factory
    lateinit var vmodel: SalesEntriesViewModel
    private lateinit var mAdapter: SalesEntriesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sales_entries)
        vmodel = ViewModelProviders.of(this, modelFactory)[SalesEntriesViewModel::class.java]
        vmodel.fetchSales("215370", "SWC2780")
        vmodel.returnStringObservable().observe(this,error)
        vmodel.returnSalesData().observe(this, observerOfSalesEntry )
        initViews()
    }

    var error = Observer<String> {
        showProgressBar(false)
        reloadData()
    }

    val observerOfSalesEntry = Observer<List<SalesEntriesRoom>> {
        if (it != null) {
            var list: List<SalesEntriesRoom> = it
            mAdapter = SalesEntriesAdapter(list, this)
            mAdapter.notifyDataSetChanged()
            _sales_entry_recycler.adapter = mAdapter
        }
        showProgressBar(false)
    }

    fun initViews() {
        _sales_entry_recycler.setHasFixedSize(true)
        val layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(this)
        _sales_entry_recycler.layoutManager = layoutManager
    }

    fun reloadData() {
        val builder = AlertDialog.Builder(this)
        builder.setMessage("Seems there is end point error, try _load")
            .setTitle("Cloud Error")
            .setIcon(R.drawable.icons8_cloud_refresh_256)
            .setCancelable(false)
            .setNegativeButton("Ok") { _, _ ->
                vmodel.fetchSales("215370", "SWC2780")
            }
        val dialog  = builder.create()
        dialog.show()
    }

    companion object {
        val TAG = "SalesEntries"
    }
}

