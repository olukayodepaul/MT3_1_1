package com.mobbile.paul.mt3_1_1.ui.sales.commission


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders

import com.mobbile.paul.mt3_1_1.R
import com.mobbile.paul.mt3_1_1.models.salesCommisssion
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_for_sales.*
import kotlinx.android.synthetic.main.fragment_sales_history.*
import kotlinx.android.synthetic.main.fragment_sales_history.base_progress_bar
import javax.inject.Inject


class SalesCommissionFragment : DaggerFragment() {

    @Inject
    internal lateinit var modelFactory: ViewModelProvider.Factory

    lateinit var vmodel: SalesCommissionFragmentViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_sales_history, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        vmodel = ViewModelProviders.of(this, modelFactory)[SalesCommissionFragmentViewModel::class.java]
        vmodel.conputSalesCom(0,"").observe(this, observers)
        showProgressBar(true)
    }

    val observers = Observer<salesCommisssion> {
        if (it != null) {
            showProgressBar(false)
            flue_float_amount.text = it.flueamount
            flue_float_date.text = it.fluedate
            ovm_amount.text = it.ovmamount
            ovm_date.text = it.ovmdate
            sales_com_amount.text = it.salescomamount
            sales_com_date.text = it.salescomdate
            mt_com_amount.text = it.mtcomamount
            mt_com_date.text = it.mtcomdate
        }
    }


    fun showProgressBar(visible: Boolean) {
        base_progress_bar.visibility =
            if (visible)
                View.VISIBLE
            else
                View.INVISIBLE
    }

}



