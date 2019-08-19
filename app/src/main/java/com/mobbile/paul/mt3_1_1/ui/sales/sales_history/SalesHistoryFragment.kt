package com.mobbile.paul.mt3_1_1.ui.sales.sales_history


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders

import com.mobbile.paul.mt3_1_1.R
import dagger.android.support.DaggerFragment
import javax.inject.Inject


class SalesHistoryFragment : DaggerFragment() {

    @Inject
    internal lateinit var modelFactory: ViewModelProvider.Factory

    lateinit var vmodel: SalesHistoryFragment_ViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_sales_history, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        vmodel = ViewModelProviders.of(this, modelFactory)[SalesHistoryFragment_ViewModel::class.java]
        // TODO: Use the ViewModel
    }


}



