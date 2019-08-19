package com.mobbile.paul.mt3_1_1.ui.sales.sales.sales_fragment


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mobbile.paul.mt3_1_1.models.Bank_n_CustomersRoom
import com.mobbile.paul.mt3_1_1.R
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_for_sales.*
import javax.inject.Inject


class SalesFragment : DaggerFragment() {

    @Inject
    internal lateinit var modelFactory: ViewModelProvider.Factory

    lateinit var vmodel: SalesFragment_ViewModel

    private lateinit var mAdapter: SalesAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_for_sales, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        vmodel = ViewModelProviders.of(this, modelFactory)[SalesFragment_ViewModel::class.java]
        vmodel.fetch().observe(this, observers)
        initViews()
    }

    val observers = Observer<List<Bank_n_CustomersRoom>> { data ->

        if (data != null) {
            var list: List<Bank_n_CustomersRoom> = data
            mAdapter = SalesAdapter(list, this.requireContext())
            mAdapter.notifyDataSetChanged()
            _recycler.adapter = mAdapter
        }
    }

    fun initViews() {
        _recycler.setHasFixedSize(true)
        val layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(this.requireContext())
        _recycler.layoutManager = layoutManager
    }

    companion object{
        var TAG = "onCheck"
    }

}







