package com.mobbile.paul.mt3_1_1.ui.sales.sales.bank


import android.annotation.SuppressLint
import android.app.AlertDialog
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
import com.mobbile.paul.mt3_1_1.models.EmployeesApi
import com.mobbile.paul.mt3_1_1.models.ProductsRoom
import com.mobbile.paul.mt3_1_1.models.totalSumProductEntry
import com.mobbile.paul.mt3_1_1.ui.sales.SalesViewpager
import com.mobbile.paul.mt3_1_1.util.Utils.Companion.PREFS_FILENAME
import kotlinx.android.synthetic.main.activity_bank.*
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class BankActivity : BaseActivity() {

    @Inject
    internal lateinit var modelFactory: ViewModelProvider.Factory

    lateinit var vmodel: BankViewModel

    private lateinit var mAdapter: BankAdapter

    var prefs: SharedPreferences? = null

    var userid: Int = 0

    var timeStamp: String = ""

    var dateStamp: String = ""


    @SuppressLint("SimpleDateFormat")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bank)
        vmodel = ViewModelProviders.of(this, modelFactory)[BankViewModel::class.java]
        vmodel.fetchSoldItems().observe(this, observers)

        prefs = getSharedPreferences(PREFS_FILENAME, Context.MODE_PRIVATE)

        userid = prefs!!.getInt("employee_id_user", 0)

        timeStamp = SimpleDateFormat("HH:mm:ss").format(Date())

        dateStamp = SimpleDateFormat("yyyy-MM-dd").format(Date())

        initWidget()

        resumebtn.setOnClickListener {
            showProgressBar(true)
            takeTodayBankAttendant()
        }

        backbtn.setOnClickListener {
            onBackPressed()
        }
    }

    val observers = Observer<List<ProductsRoom>> {
        if (it != null) {
            showProgressBar(false)
            var list: List<ProductsRoom> = it
            mAdapter = BankAdapter(list)
            mAdapter.notifyDataSetChanged()
            _r_view_pager.adapter = mAdapter
            vmodel.sumProductEntry().observe(this, observersTotal)
        }
    }

    val observersTotal = Observer<totalSumProductEntry> {
        showProgressBar(false)
        if (it != null) {
            order_tv_t.text = String.format("%,.1f", it.tqsols)
            amt_tv_t.text = String.format("%,.1f", it.tasole)
            tv_aty_t_t.text = String.format("%,.1f", it.tcco)
        }
    }

    fun initWidget() {
        _r_view_pager.setHasFixedSize(true)
        val layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(this)
        _r_view_pager.layoutManager = layoutManager
    }

    fun takeTodayBankAttendant() {
        vmodel.getTask(userid, 5, dateStamp, timeStamp).observe(this, resumeObserver)
    }

    val resumeObserver = Observer<EmployeesApi> {
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

}
