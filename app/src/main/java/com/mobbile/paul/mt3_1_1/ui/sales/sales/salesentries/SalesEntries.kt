package com.mobbile.paul.mt3_1_1.ui.sales.sales.salesentries


import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlin_project.providers.Repository
import com.mobbile.paul.mt3_1_1.R
import com.mobbile.paul.mt3_1_1.models.SalesEntriesRoom
import com.mobbile.paul.mt3_1_1.util.Utils
import com.mobiletraderv.paul.daggertraining.BaseActivity
import kotlinx.android.synthetic.main.activity_sales_entries.*
import javax.inject.Inject


class SalesEntries : BaseActivity() {

    @Inject
    internal lateinit var modelFactory: ViewModelProvider.Factory
    lateinit var vmodel: SalesEntriesViewModel
    private lateinit var mAdapter: SalesEntriesAdapter
    var prefs: SharedPreferences? = null
    var urno: String = ""
    var customerno : String? =  ""
    var employee_id : Int =  0

    @Inject
    lateinit var repository: Repository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sales_entries)

        vmodel = ViewModelProviders.of(this, modelFactory)[SalesEntriesViewModel::class.java]

        prefs = getSharedPreferences(Utils.PREFS_FILENAME, Context.MODE_PRIVATE)

        customerno = prefs!!.getString("customerno", "")

        employee_id = prefs!!.getInt("employee_id_user", 0)

        urno = intent.getStringExtra("urno")

        vmodel.fetchSales(urno, customerno.toString(), employee_id)

        vmodel.returnStringObservable().observe(this,error)

        vmodel.returnSalesData().observe(this, observerOfSalesEntry)

        initViews()

        Log.d(TAG, employee_id.toString())
    }

    var error = Observer<String> {
        showProgressBar(false)
        reloadData()
    }

    val observerOfSalesEntry = Observer<List<SalesEntriesRoom>> {

        if (it != null) {
            var list: List<SalesEntriesRoom> = it
            mAdapter = SalesEntriesAdapter(list, this, repository)
            mAdapter.notifyDataSetChanged()
            _sales_entry_recycler.setItemViewCacheSize(list.size)
            _sales_entry_recycler.adapter = mAdapter
        }
        showProgressBar(false)
    }

    private fun initViews() {

        val layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(this)
        _sales_entry_recycler.layoutManager = layoutManager
        _sales_entry_recycler!!.setHasFixedSize(true)

    }

    private fun reloadData() {

        val builder = AlertDialog.Builder(this)
        builder.setMessage("Seems there is end point error, try _load")
            .setTitle("Cloud Error")
            .setIcon(R.drawable.icons8_cloud_refresh_256)
            .setCancelable(false)
            .setNegativeButton("Ok") { _, _ ->
                vmodel.fetchSales(urno, customerno.toString(), employee_id.toString().toInt())
            }
        val dialog  = builder.create()
        dialog.show()
    }

    companion object {
        val TAG = "SalesEntries"
    }
}

