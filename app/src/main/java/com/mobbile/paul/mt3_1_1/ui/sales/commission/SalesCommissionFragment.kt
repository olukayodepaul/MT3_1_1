package com.mobbile.paul.mt3_1_1.ui.sales.commission


import android.content.Context
import android.content.SharedPreferences
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
import com.mobbile.paul.mt3_1_1.models.salesCommissionList
import com.mobbile.paul.mt3_1_1.util.Utils.Companion.PREFS_FILENAME
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_sales_history.*
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject


class SalesCommissionFragment : DaggerFragment() {

    @Inject
    internal lateinit var modelFactory: ViewModelProvider.Factory

    lateinit var vmodel: SalesCommissionFragmentViewModel

    private lateinit var mAdapter: SalesCommissionAdapter

    var prefs: SharedPreferences? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_sales_history, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        vmodel = ViewModelProviders.of(this, modelFactory)[SalesCommissionFragmentViewModel::class.java]
        prefs = activity!!.getSharedPreferences(PREFS_FILENAME, Context.MODE_PRIVATE)

        vmodel.conputSalesCom(
            prefs!!.getInt("employee_id_user", 0),
            SimpleDateFormat("yyyy-MM-dd").format(Date())
        ).observe(this, observers)

        initViews()

        showProgressBar(true)
    }

    val observers = Observer<List<salesCommissionList>> {

        if (it != null) {
            showProgressBar(false)
            var list: List<salesCommissionList> = it
            mAdapter = SalesCommissionAdapter(list)
            mAdapter.notifyDataSetChanged()
            _sales_recy_view.adapter = mAdapter
        }

    }

    fun initViews() {
        _sales_recy_view.setHasFixedSize(true)
        val layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(activity)
        _sales_recy_view.layoutManager = layoutManager
    }

    fun showProgressBar(visible: Boolean) {
        base_progress_bar.visibility =
            if (visible)
                View.VISIBLE
            else
                View.INVISIBLE
    }

}



