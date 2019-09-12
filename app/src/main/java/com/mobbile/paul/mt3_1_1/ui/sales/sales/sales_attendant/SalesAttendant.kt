package com.mobbile.paul.mt3_1_1.ui.sales.sales.sales_attendant


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
import com.mobbile.paul.mt3_1_1.models.ProductsRoom
import com.mobbile.paul.mt3_1_1.R
import com.mobbile.paul.mt3_1_1.models.EmployeesApi
import com.mobbile.paul.mt3_1_1.ui.sales.SalesViewpager
import com.mobbile.paul.mt3_1_1.util.Utils.Companion.LATLNG
import com.mobbile.paul.mt3_1_1.util.Utils.Companion.PREFS_FILENAME
import com.mobiletraderv.paul.daggertraining.BaseActivity
import kotlinx.android.synthetic.main.activity_sales__attendant.*
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class SalesAttendant : BaseActivity() {


    @Inject
    internal lateinit var modelFactory: ViewModelProvider.Factory

    lateinit var vmodel: SalesAttendantViewModel

    private lateinit var mAdapter: Attendantadapter

    var prefs: SharedPreferences? = null

    var userid: Int = 0

    var timeStamp: String = ""

    var dateStamp: String = ""

    var mLat: String? = ""

    var mLng: String? = ""

    var sharePref: SharedPreferences? = null

    @SuppressLint("SimpleDateFormat")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sales__attendant)

        vmodel = ViewModelProviders.of(this, modelFactory)[SalesAttendantViewModel::class.java]
        vmodel.fetchBasket(1).observe(this, observers)

        prefs = getSharedPreferences(PREFS_FILENAME, Context.MODE_PRIVATE)
        sharePref  = getSharedPreferences(LATLNG, Context.MODE_PRIVATE)

        userid = prefs!!.getInt("employee_id_user", 0)

        timeStamp = SimpleDateFormat("HH:mm:ss").format(Date())
        dateStamp = SimpleDateFormat("yyyy-MM-dd").format(Date())

        initViews()

        //set starting latlng
        mLat = intent.getStringExtra("lat")
        mLng = intent.getStringExtra("lng")


        showProgressBar(true)
    }

    fun initViews() {
        view_pager.setHasFixedSize(true)
        val layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(this)
        view_pager.layoutManager = layoutManager

        backbtn.setOnClickListener {
            onBackPressed()
        }

        resumebtn.setOnClickListener {
            showProgressBar(true)
            vmodel.getTask(userid, 1, dateStamp, timeStamp).observe(this, resumeObserver)
            Log.d(TAG, "${dateStamp}")
        }

        clockoutbtn.setOnClickListener {
            showProgressBar(true)
            vmodel.getTask(userid, 2, dateStamp, timeStamp).observe(this, resumeObserver)
        }
    }

    val resumeObserver = Observer<EmployeesApi> {
        when (it.status) {
            200 -> {
                setLatLngStartingPoint()
                msgSuccess(it.msg)
            }
            400 -> {
                msgError(it.msg, 1)
            }
            else -> {
                msgError("",2)
            }
        }
        showProgressBar(false)
    }

    private fun msgSuccess(msg: String) {
        val builder = AlertDialog.Builder(this, R.style.AlertDialogDanger)
        builder.setMessage(msg)
            .setTitle("Successful")
            .setIcon(R.drawable.icons8_ok_96)
            .setCancelable(false)
            .setNegativeButton("OK") { _, _ ->
                var intent = Intent(this, SalesViewpager::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                startActivity(intent)
            }
        val dialog = builder.create()
        dialog.show()
    }

    private fun msgError(msg: String, err: Int) {

        val msgs : String = if(err==2){
            "Api Error, Please check your internet. Thanks!"
        }else{
            msg
        }

        val builder = AlertDialog.Builder(this, R.style.AlertDialogDanger)
        builder.setMessage(msgs)
            .setTitle("Notice!")
            .setIcon(R.drawable.icons8_rollback_96)
            .setCancelable(false)
            .setNegativeButton("OK") { _, _ ->

            }
        val dialog = builder.create()
        dialog.show()
    }

    val observers = Observer<List<ProductsRoom>> {
        if (it != null) {

            var list: List<ProductsRoom> = it
            mAdapter = Attendantadapter(list, this)
            mAdapter.notifyDataSetChanged()
            view_pager.adapter = mAdapter
        }
        showProgressBar(false)
    }

    fun setLatLngStartingPoint() {
        sharePref!!.edit().clear().apply()
        val editor = sharePref!!.edit()
        editor.putString("starting_lat", mLat)
        editor.putString("starting_lng", mLng)
        editor.apply()
    }

    companion object {
        var TAG = "SalesAttendant"
    }
}
