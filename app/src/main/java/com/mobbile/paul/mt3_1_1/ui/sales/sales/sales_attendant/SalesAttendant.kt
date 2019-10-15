package com.mobbile.paul.mt3_1_1.ui.sales.sales.sales_attendant


import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mobbile.paul.mt3_1_1.BaseActivity
import com.mobbile.paul.mt3_1_1.models.ProductsRoom
import com.mobbile.paul.mt3_1_1.R
import com.mobbile.paul.mt3_1_1.ui.sales.SalesViewpager
import com.mobbile.paul.mt3_1_1.util.Utils.Companion.LATLNG
import com.mobbile.paul.mt3_1_1.util.Utils.Companion.PREFS_FILENAME
import com.mobbile.paul.mt3_1_1.util.Utils.Companion.isInternetAvailable
import com.mobbile.paul.mt3_1_1.viewmodel.Attendant
import kotlinx.android.synthetic.main.activity_sales__attendant.*
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class SalesAttendant : BaseActivity(), View.OnClickListener {

    @Inject
    internal lateinit var modelFactory: ViewModelProvider.Factory

    lateinit var vmodel: SalesAttendantViewModel

    private lateinit var mAdapter: Attendantadapter

    lateinit var prefs: SharedPreferences

    lateinit var sharePref: SharedPreferences

    var mLat: String? = ""

    var mLng: String? = ""


    @SuppressLint("SimpleDateFormat")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sales__attendant)

        vmodel = ViewModelProviders.of(this, modelFactory)[SalesAttendantViewModel::class.java]
        vmodel.fetchBasket(1).observe(this, observers)

        prefs = getSharedPreferences(PREFS_FILENAME, Context.MODE_PRIVATE)
        sharePref = getSharedPreferences(LATLNG, Context.MODE_PRIVATE)

        mLat = intent.getStringExtra("lat")
        mLng = intent.getStringExtra("lng")

        backbtn.setOnClickListener(this)
        resumebtn.setOnClickListener(this)
        clockoutbtn.setOnClickListener(this)

        showProgressBar(true)

        initViews()

        vmodel.mResponse().observe(this, attendantObserver)
    }

    fun initViews() {
        view_pager.setHasFixedSize(true)
        val layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(this)
        view_pager.layoutManager = layoutManager
    }

    private val observers = Observer<List<ProductsRoom>> {
        if (it != null) {
            var list: List<ProductsRoom> = it
            mAdapter = Attendantadapter(list, this)
            mAdapter.notifyDataSetChanged()
            view_pager.adapter = mAdapter
        }
        showProgressBar(false)
    }

    @SuppressLint("SimpleDateFormat")
    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.backbtn -> {
                onBackPressed()
            }
            R.id.resumebtn -> {
                if(!isInternetAvailable(this)){
                    message("No Internet Connection, Thanks!", "Internet Error", 1,400)
                }else{
                    showProgressBar(true)
                    vmodel.getTask(
                        prefs!!.getInt("employee_id_user", 0),
                        1,
                        SimpleDateFormat("yyyy-MM-dd").format(Date()),
                        SimpleDateFormat("HH:mm:ss").format(Date())
                    )
                }
            }
            R.id.clockoutbtn -> {
                if(!isInternetAvailable(this)){
                    message("No Internet Connection, Thanks!", "Internet Error", 1,400)
                }else {
                    showProgressBar(true)
                    vmodel.getTask(
                        prefs!!.getInt("employee_id_user", 0),
                        2,
                        SimpleDateFormat("yyyy-MM-dd").format(Date()),
                        SimpleDateFormat("HH:mm:ss").format(Date())
                    )
                }
            }
        }
    }

    private var attendantObserver = Observer<Attendant> {
        if(it.status==200 && it.taskid==2) {
            setLatLngStartingPoint()
            message(it.msg, "Notice!",it.taskid, it.status)
        }else if(it.status==200 && it.taskid==1) {
            message(it.msg, "Notice!",it.taskid, it.status)
        }else{
            message(it.msg, "Notice!",it.taskid, it.status)
        }
    }

    fun setLatLngStartingPoint() {
        sharePref.edit().clear().apply()
        val editor = sharePref.edit()
        editor.putString("assertiondate", SimpleDateFormat("yyyy-MM-dd").format(Date()))
        editor.putString("starting_lat", mLat)
        editor.putString("starting_lng", mLng)
        editor.apply()
    }

    private fun message(msg: String?, title: String, taskid: Int, status: Int) {
        val builder = AlertDialog.Builder(this, R.style.AlertDialogDanger)
        builder.setMessage(msg)
            .setTitle(title)
            .setIcon(R.drawable.icons8_google_alerts_100)
            .setCancelable(false)
            .setNegativeButton("OK") { _, _ ->
                showProgressBar(false)
                if(status==200 && taskid==2) {
                    var intent = Intent(this, SalesViewpager::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                    startActivity(intent)
                }
            }
        val dialog = builder.create()
        dialog.show()
    }

    companion object {
        var TAG = "SalesAttendant"
    }
}
