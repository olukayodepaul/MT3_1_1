package com.mobbile.paul.mt3_1_1.ui.auth

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.telephony.TelephonyManager
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.app.ActivityCompat.checkSelfPermission
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.google.gson.Gson
import com.mobbile.paul.mt3_1_1.R
import com.mobbile.paul.mt3_1_1.models.SaveEntries
import com.mobbile.paul.mt3_1_1.ui.modules.ModulesActivity
import com.mobiletraderv.paul.daggertraining.BaseActivity
import kotlinx.android.synthetic.main.activity_auth.*
import javax.inject.Inject

@SuppressLint("ByteOrderMark")
class AuthActivity : BaseActivity() {

    @Inject
    internal lateinit var modelFactory: ViewModelProvider.Factory
    lateinit var vmodel: AuthViewModel


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)
        showProgressBar(false)
        vmodel = ViewModelProviders.of(this, modelFactory)[AuthViewModel::class.java]

        btn_login.setOnClickListener {
            dataProcess()
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun dataProcess() {

        val permit = checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE)

        val username: String = et_email.text.toString()
        val password: String = et_password.text.toString()
        val tel = getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
        //tel.getImei().toString()

        if (permit == PackageManager.PERMISSION_GRANTED) {
            showProgressBar(true)
            vmodel.auth("xE4EqDp", "4625", "351736103857888").observe(this, LoginObservers)
        } else {
            makeRequest()
        }
    }

    //action taken on request dialog either allow or deny
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            DEVICE_STATE_PERMISSION -> {
                if (grantResults.isEmpty() || grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(applicationContext, "imei permission deny, please allow", Toast.LENGTH_LONG).show()
                }
            }

        }
    }

    //location permission request dialog
    private fun makeRequest() {
        ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.READ_PHONE_STATE), DEVICE_STATE_PERMISSION)
    }

    val LoginObservers = Observer<SaveEntries> { data ->
        showProgressBar(false)
        when (data.id) {
            404 -> {
                Toast.makeText(applicationContext, data.name, Toast.LENGTH_SHORT).show()
            }
            else -> {
                val ds = getSharedPreferences("name", Context.MODE_PRIVATE)
                val edit = ds.edit()
                val gson = Gson()
                val json = gson.toJson(data)
                edit.putString("uniquest_id_com_mt_3", json)
                edit.apply()

                var intent = Intent(this, ModulesActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
    }

    companion object {
        private val TAG = "ModulesActivity"
        const val DEVICE_STATE_PERMISSION = 101
    }
}
