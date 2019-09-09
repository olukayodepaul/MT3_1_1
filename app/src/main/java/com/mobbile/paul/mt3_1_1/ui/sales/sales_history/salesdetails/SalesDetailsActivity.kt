package com.mobbile.paul.mt3_1_1.ui.sales.sales_history.salesdetails


import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mobbile.paul.mt3_1_1.R
import com.mobbile.paul.mt3_1_1.models.OutletSalesHistoryDetails
import com.mobiletraderv.paul.daggertraining.BaseActivity
import kotlinx.android.synthetic.main.activity_sales_details.*
import javax.inject.Inject

class SalesDetailsActivity : BaseActivity() {

    @Inject
    internal lateinit var modelFactory: ViewModelProvider.Factory

    private lateinit var mAdapter: SalesDetailsAdapter

    var token : Int? = 0

    lateinit var vmodel: SalesDetailsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sales_details)
        vmodel = ViewModelProviders.of(this, modelFactory)[SalesDetailsViewModel::class.java]
        token = intent.getIntExtra("token",0)
        vmodel.outletSales(token!!).observe(this, observers)
        initWidget()
    }

    val observers = Observer<List<OutletSalesHistoryDetails>> {
        showProgressBar(false)
        if (it != null) {
            showProgressBar(false)
            var list: List<OutletSalesHistoryDetails> = it
            mAdapter = SalesDetailsAdapter(list)
            mAdapter.notifyDataSetChanged()
            h_widget_s.adapter = mAdapter
        }
    }

    fun initWidget(){
        h_widget_s.setHasFixedSize(true)
        val layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(this)
        h_widget_s.layoutManager = layoutManager
    }
}
