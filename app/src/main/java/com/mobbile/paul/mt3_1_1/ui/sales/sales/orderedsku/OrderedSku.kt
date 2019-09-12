package com.mobbile.paul.mt3_1_1.ui.sales.sales.orderedsku


import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
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
import com.mobbile.paul.mt3_1_1.ui.sales.SalesViewpager
import com.mobbile.paul.mt3_1_1.util.Utils.Companion.LATLNG
import com.mobbile.paul.mt3_1_1.util.Utils.Companion.PREFS_FILENAME
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

    var latlng: SharedPreferences? = null

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

        prefs = getSharedPreferences(PREFS_FILENAME, Context.MODE_PRIVATE)

        latlng = getSharedPreferences(LATLNG, Context.MODE_PRIVATE)

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

        vmodel.setMutableResponse().observe(this, obServeOfPost)

    }



    fun IntAdapter() {
        val layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(this)
        recycler_view_complete.layoutManager = layoutManager
        recycler_view_complete!!.setHasFixedSize(true)

        btn_complete.setOnClickListener {
            showProgressBar(true)
            if (token.equals(token_form.text.toString())) {
                vmodel.pullAllSalesEntry().observe(this, obervePullinSalesData)
            } else {
                tokenVerify(2, "Error",  "Invalid Customer Verification code ")
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

    val obServeOfPost = Observer<String>{
        val rsp  = it.split("~")
        when(rsp[0]){
            "200"->{
                tokenVerify(1, "Success",  rsp[1])
            }
            else->{
                tokenVerify(2, "Error",  rsp[1])
            }
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
            s_s_amount.text = it.stotalsum.toString()
            s_s_order.text = it.sorder.toString()
            s_s_pricing.text = it.spricing.toString()
            s_s_invetory.text = it.sinventory.toString()
        }
    }


    private fun tokenVerify(status: Int, title: String, msg: String) {
        val builder = AlertDialog.Builder(this, R.style.AlertDialogDanger)
        builder.setMessage(msg)
            .setTitle(title)
            .setIcon(R.drawable.icons8_google_alerts_100)
            .setCancelable(false)
            .setPositiveButton("Ok") { _, _ ->
                if(status==1) {
                    latlng!!.edit().clear().apply()
                    val editor = latlng!!.edit()
                    editor.putString("starting_lat", clat.toString())
                    editor.putString("starting_lng", clng.toString())
                    editor.apply()

                    val intent = Intent(this, SalesViewpager::class.java )
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                    startActivity(intent)
                }
            }
        val dialog = builder.create()
        dialog.show()
    }

    companion object {
        var TAG = "OrderedSku"
    }
}
