package com.mobbile.paul.mt3_1_1.ui.settings

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.SwitchCompat
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.mobbile.paul.mt3_1_1.R
import com.mobbile.paul.mt3_1_1.util.Utils.Companion.PREFS_FILENAME
import com.mobbile.paul.mt3_1_1.viewmodel.ReloadCustomers
import com.mobiletraderv.paul.daggertraining.BaseActivity
import kotlinx.android.synthetic.main.settings_activity.*
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject
import androidx.lifecycle.Observer

class SettingsActivity : BaseActivity() {

    @Inject
    internal lateinit var modelFactory: ViewModelProvider.Factory

    lateinit var vmodel: SettingViewModel

    lateinit var  sw_customers: SwitchCompat

    lateinit var  sw_basket: SwitchCompat

    lateinit var prefs: SharedPreferences


    @SuppressLint("SimpleDateFormat")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.settings_activity)
        vmodel = ViewModelProviders.of(this, modelFactory)[SettingViewModel::class.java]
        prefs = getSharedPreferences(PREFS_FILENAME, Context.MODE_PRIVATE)
        showProgressBar(false)
        backbtn.setOnClickListener {
            onBackPressed()
        }
        initCustomesrData()
        initProductsData()
        vmodel.mResponse().observe(this, SingleObserver)
    }

    @SuppressLint("SetTextI18n")
    fun initCustomesrData(){
        sw_customers = findViewById<View>(R.id.customers_switch) as SwitchCompat
        sw_customers.text = "OFF"

        sw_customers.setOnClickListener {
            showProgressBar(true)
            if(sw_customers.isChecked){
                sw_customers.text = "ON"
                vmodel.reloadCustomers(prefs.getInt("employee_id_user", 0), SimpleDateFormat("yyyy-MM-dd").format(Date()))
            }else{
                showProgressBar(false)
                sw_customers.text = "OFF"
            }
        }
    }

    @SuppressLint("SetTextI18n")
    fun initProductsData(){
        sw_basket = findViewById<View>(R.id.basket_switch) as SwitchCompat
        sw_basket.text = "OFF"

        sw_basket.setOnClickListener {
            showProgressBar(true)
            if(sw_basket.isChecked){
                sw_basket.text = "ON"
                vmodel.reloadProduct(prefs.getString("customerno",""))
            }else{
                showProgressBar(false)
                sw_basket.text = "OFF"
            }
        }
    }

    private val SingleObserver = Observer<ReloadCustomers> {
        showProgressBar(false)
        userInteraction(it.status, it.msg)
    }

    private fun userInteraction(status: Int, msg: String?) {
        val builder = AlertDialog.Builder(this, R.style.AlertDialogDanger)
        builder.setMessage(msg)
            .setTitle("Notification")
            .setIcon(R.drawable.icons8_google_alerts_100)
            .setCancelable(false)
            .setPositiveButton("Ok") { _, _ ->
                sw_customers.isChecked = false
                sw_basket.isChecked = false
            }
        val dialog = builder.create()
        dialog.show()
    }





}