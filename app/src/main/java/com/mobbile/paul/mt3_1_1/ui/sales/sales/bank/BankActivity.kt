package com.mobbile.paul.mt3_1_1.ui.sales.sales.bank


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
import kotlinx.android.synthetic.main.activity_bank.*
import javax.inject.Inject

class BankActivity : BaseActivity() {

    @Inject
    internal lateinit var modelFactory: ViewModelProvider.Factory

    lateinit var vmodel: BankViewModel

    private lateinit var mAdapter: BankAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bank)
        vmodel = ViewModelProviders.of(this, modelFactory)[BankViewModel::class.java]
        vmodel.fetchSoldItems().observe(this, observers)
        initWidget()
    }

    val observers = Observer<List<ProductsRoom>> {
        if (it != null) {
            showProgressBar(false)
            var list: List<ProductsRoom> = it
            mAdapter = BankAdapter(list)
            mAdapter.notifyDataSetChanged()
            _r_view_pager.adapter = mAdapter
            vmodel.sumProductEntry().observe(this, observersTotal)
        }
    }

    val observersTotal = Observer<totalSumProductEntry> {
        showProgressBar(false)
        if (it != null) {
            order_tv_t.text = String.format("%,.1f",it.tqsols)
            amt_tv_t.text = String.format("%,.1f", it.tasole)
            tv_aty_t_t.text = String.format("%,.1f", it.tcco)
        }
    }

    fun initWidget() {
        _r_view_pager.setHasFixedSize(true)
        val layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(this)
        _r_view_pager.layoutManager = layoutManager
    }
}
