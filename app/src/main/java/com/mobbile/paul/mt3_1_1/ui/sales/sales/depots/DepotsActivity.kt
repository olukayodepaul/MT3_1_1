package com.mobbile.paul.mt3_1_1.ui.sales.sales.depots


import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mobbile.paul.mt3_1_1.R
import com.mobbile.paul.mt3_1_1.models.EmployeesApi
import com.mobbile.paul.mt3_1_1.models.ProductsRoom
import com.mobbile.paul.mt3_1_1.models.totalSumProductEntry
import com.mobbile.paul.mt3_1_1.ui.sales.SalesViewpager
import com.mobbile.paul.mt3_1_1.util.Utils
import com.mobbile.paul.mt3_1_1.util.Utils.Companion.PREFS_FILENAME
import com.mobiletraderv.paul.daggertraining.BaseActivity
import kotlinx.android.synthetic.main.activity_bank.*
import kotlinx.android.synthetic.main.activity_depots.*
import kotlinx.android.synthetic.main.activity_depots._r_view_pager
import kotlinx.android.synthetic.main.activity_depots.amt_tv_t
import kotlinx.android.synthetic.main.activity_depots.backbtn
import kotlinx.android.synthetic.main.activity_depots.order_tv_t
import kotlinx.android.synthetic.main.activity_depots.resumebtn
import kotlinx.android.synthetic.main.activity_depots.tv_aty_t_t
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class DepotsActivity : BaseActivity() {

    @Inject
    internal lateinit var modelFactory: ViewModelProvider.Factory

    lateinit var vmodel: DepotViewModel

    private lateinit var mAdapter: DepotAdapter


    var prefs: SharedPreferences? = null

    var userid: Int = 0

    var timeStamp: String = ""

    var dateStamp: String = ""

    @SuppressLint("SimpleDateFormat")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_depots)
        vmodel = ViewModelProviders.of(this, modelFactory)[DepotViewModel::class.java]
        vmodel.fetchSoldItems().observe(this, observers)
        initWidget()

        prefs = getSharedPreferences(PREFS_FILENAME, Context.MODE_PRIVATE)

        userid = prefs!!.getInt("employee_id_user", 0)

        timeStamp = SimpleDateFormat("HH:mm:ss").format(Date())

        dateStamp = SimpleDateFormat("yyyy-MM-dd").format(Date())

        //clockin
        resumebtn.setOnClickListener {
            takeTodayClockInAttendant()
        }

        clockoutbtn.setOnClickListener {
            takeTodayCloseAttendant()
        }

        backbtn.setOnClickListener {
            onBackPressed()
        }
    }

    val observers = Observer<List<ProductsRoom>> {
        if (it != null) {
            showProgressBar(false)
            var list: List<ProductsRoom> = it
            mAdapter = DepotAdapter(list)
            mAdapter.notifyDataSetChanged()
            _r_view_pager.adapter = mAdapter
            vmodel.sumProductEntry().observe(this, observersTotal)
        }
    }

    val observersTotal = Observer<totalSumProductEntry> {
        showProgressBar(false)
        if (it != null) {
            order_tv_t.text = String.format("%,.1f",it.qty)
            amt_tv_t.text = String.format("%,.1f", it.tqsols)
            tv_aty_t_t.text = String.format("%,.1f", it.qty-it.tqsols)
        }
    }

    fun initWidget() {
        _r_view_pager.setHasFixedSize(true)
        val layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(this)
        _r_view_pager.layoutManager = layoutManager
    }

    fun takeTodayClockInAttendant() {
        showProgressBar(true)
        vmodel.getTask(userid,3, dateStamp, timeStamp).observe(this, clockinObserver)
    }

    val clockinObserver = Observer<EmployeesApi> {
        if (it != null) {
            Log.d(TAG, it.toString())
            when (it.status) {
                200 -> {
                    msgSuccess(it.msg, "Success", 2)
                }
                400 -> {
                    msgSuccess(it.msg, "Error", 2)
                }
            }
        } else {
            msgSuccess("Network problem, Please check your network. Thanks!", "Error", 2)
        }
        showProgressBar(false)
    }

    fun takeTodayCloseAttendant() {
        showProgressBar(true)
        vmodel.getTask(userid, 6, dateStamp, timeStamp).observe(this, closeObserver)
    }

    val closeObserver = Observer<EmployeesApi> {
        if (it != null) {
            when (it.status) {
                200 -> {
                    msgSuccess(it.msg, "Success", 1)
                }
                400 -> {
                    msgSuccess(it.msg, "Error", 2)
                }
            }
        } else {
            msgSuccess("Network problem, Please check your network. Thanks!", "Error", 2)
        }
        showProgressBar(false)
    }



    private fun msgSuccess(msg: String, title: String, status: Int) {
        val builder = AlertDialog.Builder(this, R.style.AlertDialogDanger)
        builder.setMessage(msg)
            .setTitle(title)
            .setIcon(R.drawable.icons8_google_alerts_100)
            .setCancelable(false)
            .setNegativeButton("OK") { _, _ ->
                if (status == 1) {
                    var intent = Intent(this, SalesViewpager::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                    startActivity(intent)
                }
            }
        val dialog = builder.create()
        dialog.show()
    }

    companion object{
        val TAG = "DepotsActivity"
    }
}
