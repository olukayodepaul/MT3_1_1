package com.mobbile.paul.mt3_1_1.ui.customers


import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mobbile.paul.mt3_1_1.BaseActivity
import com.mobbile.paul.mt3_1_1.R
import com.mobbile.paul.mt3_1_1.models.RepCustomers
import com.mobbile.paul.mt3_1_1.ui.customers.newcustomers.MapOutlets
import com.mobbile.paul.mt3_1_1.ui.modules.ModulesActivity
import com.mobbile.paul.mt3_1_1.util.Util.showMsgDialog
import com.mobbile.paul.mt3_1_1.util.Util.showSomeDialog
import com.mobbile.paul.mt3_1_1.util.Utils.Companion.PREFS_FILENAME
import kotlinx.android.synthetic.main.activity_customer_page_viwer.*
import javax.inject.Inject

class CustomerPageViwer : BaseActivity() {

    @Inject
    internal lateinit var modelFactory: ViewModelProvider.Factory

    lateinit var vmodel: CustomerViewModel


    private lateinit var mAdapter: CustomersAdapter

    var pref: SharedPreferences? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_customer_page_viwer)
        vmodel = ViewModelProviders.of(this, modelFactory)[CustomerViewModel::class.java]
        pref = getSharedPreferences(PREFS_FILENAME, Context.MODE_PRIVATE)
        vmodel.getAllCustomers(pref!!.getInt("employee_id_user", 0)).observe(this, observers)
        initWidget()

        backbtn.setOnClickListener {
            onBackPressed()
        }

        floatingActionButton.setOnClickListener {
            val intent = Intent(this, MapOutlets::class.java)
            startActivity(intent)
        }
        vmodel.mResponse().observe(this, ObserveCust)
    }

    var ObserveCust = Observer<String> {
        if(it=="OK") {
            showMsgDialog(ModulesActivity(), this, "Completed","Asy data process completed!")
        }else{
            showSomeDialog( this,"Asy data process completed!","Completed")
        }
    }

    override fun onStart() {
        super.onStart()
            vmodel.getAllCustomers(pref!!.getInt("employee_id_user", 0)).observe(this, observers)
    }

    val observers = Observer<List<RepCustomers>> {
        if (it != null) {
            showProgressBar(false)
            var list: List<RepCustomers> = it
            mAdapter = CustomersAdapter(list, this, vmodel)
            mAdapter.notifyDataSetChanged()
            _r_view_pager.setItemViewCacheSize(list.size)
           _r_view_pager.adapter = mAdapter
        }
    }

    fun initWidget() {
        _r_view_pager.setHasFixedSize(true)
        val layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(this)
        _r_view_pager.layoutManager = layoutManager
    }


}
