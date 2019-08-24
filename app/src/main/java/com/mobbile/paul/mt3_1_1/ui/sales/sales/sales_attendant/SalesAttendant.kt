package com.mobbile.paul.mt3_1_1.ui.sales.sales.sales_attendant


import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mobbile.paul.mt3_1_1.models.ProductsRoom
import com.mobbile.paul.mt3_1_1.R
import com.mobiletraderv.paul.daggertraining.BaseActivity
import kotlinx.android.synthetic.main.activity_sales__attendant.*
import javax.inject.Inject

class SalesAttendant : BaseActivity() {


    @Inject
    internal lateinit var modelFactory: ViewModelProvider.Factory
    lateinit var vmodel: SalesAttendantViewModel
    private lateinit var mAdapter: Attendantadapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sales__attendant)
        vmodel = ViewModelProviders.of(this, modelFactory)[SalesAttendantViewModel::class.java]
        vmodel.fetchBasket(1).observe(this, observers)
        initViews()
        showProgressBar(true)
    }

    fun initViews() {
        view_pager.setHasFixedSize(true)
        val layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(this)
        view_pager.layoutManager = layoutManager
    }

    val observers = Observer<List<ProductsRoom>> {
        if (it != null) {

            var list: List<ProductsRoom> = it
            mAdapter = Attendantadapter(list, this)
            mAdapter.notifyDataSetChanged()
            view_pager.adapter = mAdapter
        }
        showProgressBar(false)
    }
}
