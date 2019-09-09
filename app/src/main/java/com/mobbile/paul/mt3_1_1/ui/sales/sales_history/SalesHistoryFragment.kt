package com.mobbile.paul.mt3_1_1.ui.sales.sales_history


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.mobbile.paul.mt3_1_1.R
import com.mobbile.paul.mt3_1_1.models.repSalesHistoryRoom
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_for_sales.*
import kotlinx.android.synthetic.main.fragment_sales_h.*
import javax.inject.Inject


class SalesHistoryFragment : DaggerFragment() {

    @Inject
    internal lateinit var modelFactory: ViewModelProvider.Factory

    private lateinit var mAdapter: SalesHistoryAdapter


    lateinit var vmodel: SalesHistoryFragmentViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_sales_h, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        vmodel = ViewModelProviders.of(this, modelFactory)[SalesHistoryFragmentViewModel::class.java]
        vmodel.pullAllSalesEntry().observe(this, observers)
        initWidget()
    }

    fun initWidget(){
        h_widget.setHasFixedSize(true)
        val layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(this.requireContext())
        h_widget.layoutManager = layoutManager
    }

    val observers = Observer<List<repSalesHistoryRoom>> {
        showProgressBar(false)
        if (it != null) {
            var list: List<repSalesHistoryRoom> = it
            mAdapter = SalesHistoryAdapter(list, this.requireContext())
            mAdapter.notifyDataSetChanged()
            h_widget.adapter = mAdapter
        }
    }

    fun showProgressBar(visible: Boolean) {
        base_progress_bars.visibility =
            if (visible)
                View.VISIBLE
            else
                View.INVISIBLE
    }
}



