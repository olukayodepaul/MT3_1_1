package com.mobbile.paul.mt3_1_1.ui.sales.sales.depots


import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mobbile.paul.mt3_1_1.R
import com.mobbile.paul.mt3_1_1.models.ProductsRoom
import com.mobbile.paul.mt3_1_1.models.totalSumProductEntry
import com.mobiletraderv.paul.daggertraining.BaseActivity
import kotlinx.android.synthetic.main.activity_depots.*
import javax.inject.Inject

class DepotsActivity : BaseActivity() {

    @Inject
    internal lateinit var modelFactory: ViewModelProvider.Factory

    lateinit var vmodel: DepotViewModel

    private lateinit var mAdapter: DepotAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_depots)
        vmodel = ViewModelProviders.of(this, modelFactory)[DepotViewModel::class.java]
        vmodel.fetchSoldItems().observe(this, observers)
        initWidget()
    }

    val observers = Observer<List<ProductsRoom>> {
        if (it != null) {
            showProgressBar(false)
            var list: List<ProductsRoom> = it
            mAdapter = DepotAdapter(list)
            mAdapter.notifyDataSetChanged()
            _r_view_pager.adapter = mAdapter
            vmodel.sumProductEntry().observe(this, observersTotal)
        }
    }

    val observersTotal = Observer<totalSumProductEntry> {
        showProgressBar(false)
        if (it != null) {
            order_tv_t.text = String.format("%,.1f",it.qty)
            amt_tv_t.text = String.format("%,.1f", it.tqsols)
            tv_aty_t_t.text = String.format("%,.1f", it.qty-it.tqsols)
        }
    }

    fun initWidget() {
        _r_view_pager.setHasFixedSize(true)
        val layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(this)
        _r_view_pager.layoutManager = layoutManager
    }
}
