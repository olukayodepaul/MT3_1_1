package com.mobbile.paul.mt3_1_1.ui.sales.sales.orderedsku


import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mobbile.paul.mt3_1_1.R
import com.mobbile.paul.mt3_1_1.models.SalesEntrieHolderApi
import com.mobbile.paul.mt3_1_1.models.SalesEntrieHolderRoom
import com.mobbile.paul.mt3_1_1.models.SumSales
import com.mobbile.paul.mt3_1_1.util.Utils
import com.mobiletraderv.paul.daggertraining.BaseActivity
import kotlinx.android.synthetic.main.activity_ordered_sku.*
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class OrderedSku : BaseActivity() {

    @Inject
    internal lateinit var modelFactory: ViewModelProvider.Factory

    lateinit var vmodel: OrderViewModel

    private lateinit var mAdapter: OrderAdapter

    var urno: String = ""

    var token: String? = ""

    var outletname: String? = ""

    var defaulttoken: String? = ""

    var prefs: SharedPreferences? = null

    var employee_id: Int = 0

    var visit_sequence: String? = ""

    var clat: Double = 0.0

    var clng: Double = 0.0

    var distance: String? = ""

    var arivaltime: String? = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ordered_sku)

        vmodel = ViewModelProviders.of(this, modelFactory)[OrderViewModel::class.java]

        prefs = getSharedPreferences(Utils.PREFS_FILENAME, Context.MODE_PRIVATE)

        visit_sequence = intent.getStringExtra("visit_sequence")

        urno = intent.getStringExtra("urno")

        token = intent.getStringExtra("token")

        outletname = intent.getStringExtra("outletname")

        defaulttoken = intent.getStringExtra("defaulttoken")

        employee_id = prefs!!.getInt("employee_id_user", 0)

        clat = intent.getDoubleExtra("clat", 0.0)

        clng = intent.getDoubleExtra("clng", 0.0)

        distance = intent.getStringExtra("distance")

        arivaltime = intent.getStringExtra("arivaltime")

        vmodel.fetch().observe(this, observerOfSalesEntry)

        tv_outlet_name.text = outletname

        IntAdapter()

    }

    fun IntAdapter() {
        val layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(this)
        recycler_view_complete.layoutManager = layoutManager
        recycler_view_complete!!.setHasFixedSize(true)

        btn_complete.setOnClickListener {
            if (!token.equals(token_form.text.toString())) {
                tokenVerify()
            } else {
                vmodel.pullAllSalesEntry().observe(this, obervePullinSalesData)
            }
        }
    }

    @SuppressLint("SimpleDateFormat")
    private val obervePullinSalesData = Observer<List<SalesEntrieHolderApi>> {
        if (it != null) {
            vmodel.postSalesToServer(
                employee_id, urno, token!!, distance!!, arivaltime!!,
                SimpleDateFormat("HH:mm:ss").format(Date()),
                SimpleDateFormat("yyyy-MM-dd").format(Date()),
                clat.toString(), clng.toString(), it, visit_sequence!!
            )
        }
    }

    private val observerOfSalesEntry = Observer<List<SalesEntrieHolderRoom>> {
        if (it != null) {
            var list: List<SalesEntrieHolderRoom> = it
            mAdapter = OrderAdapter(list)
            mAdapter.notifyDataSetChanged()
            recycler_view_complete.adapter = mAdapter
            loadTotalSales()
        }
        showProgressBar(false)
    }

    private fun loadTotalSales() {
        vmodel.sumAllSalesEntry().observe(this, obserTotal)
    }

    private val obserTotal = Observer<SumSales> {
        if (it != null) {
            s_s_amount.text = it!!.stotalsum.toString()
            s_s_order.text = it!!.sorder.toString()
            s_s_pricing.text = it!!.spricing.toString()
            s_s_invetory.text = it!!.sinventory.toString()
        }
    }

    private fun tokenVerify() {
        val builder = AlertDialog.Builder(this)
        builder.setMessage("Please enter valid customer code! Or do you want to use default token")
            .setTitle("Incorrect Token")
            .setIcon(R.drawable.icons8_validation_100)
            .setCancelable(false)
            .setNegativeButton("Cancel") { _, _ ->
            }.setPositiveButton("Ok") { _, _ ->
                //Call submit end point
                //miss commission of Mobile trader commission
            }
        val dialog = builder.create()
        dialog.show()
    }

    companion object {
        var TAG = "OrderedSku"
    }
}
