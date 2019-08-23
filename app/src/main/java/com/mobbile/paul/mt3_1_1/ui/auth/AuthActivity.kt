package com.mobbile.paul.mt3_1_1.ui.auth

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.telephony.TelephonyManager
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.app.ActivityCompat.checkSelfPermission
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.google.gson.Gson
import com.jakewharton.threetenabp.AndroidThreeTen
import com.mobbile.paul.mt3_1_1.R
import com.mobbile.paul.mt3_1_1.models.SaveEntries
import com.mobbile.paul.mt3_1_1.ui.modules.ModulesActivity
import com.mobiletraderv.paul.daggertraining.BaseActivity
import kotlinx.android.synthetic.main.activity_auth.*
import org.threeten.bp.Instant
import org.threeten.bp.ZoneId
import org.threeten.bp.ZonedDateTime
import org.threeten.bp.temporal.ChronoField
import java.lang.reflect.Type
import javax.inject.Inject
import com.google.gson.reflect.TypeToken as TypeToken1

@SuppressLint("ByteOrderMark")
class AuthActivity : BaseActivity() {

    @Inject
    internal lateinit var modelFactory: ViewModelProvider.Factory
    lateinit var vmodel: AuthViewModel
    var zdate: ZonedDateTime = ZonedDateTime.now()
    var prefs: SharedPreferences? = null
    val PREFS_FILENAME = "com.mt.v3.1.2.prefs"
    lateinit var dates: String


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)
        showProgressBar(false)
        AndroidThreeTen.init(this)
        vmodel = ViewModelProviders.of(this, modelFactory)[AuthViewModel::class.java]
        prefs = getSharedPreferences(PREFS_FILENAME, Context.MODE_PRIVATE)
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
        dates = zdate.toLocalDate().toString()

        if (permit == PackageManager.PERMISSION_GRANTED) {
            showProgressBar(true)
            vmodel.auth("t0kTdEw", "3613", "356973100833183", dates, validateDateEntries())
                .observe(this, loginObservers)
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

    private fun makeRequest() {
        ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.READ_PHONE_STATE), DEVICE_STATE_PERMISSION)
    }

    private val loginObservers = Observer<SaveEntries> {
        showProgressBar(false)
        when (it.id) {
            404 -> {
                Toast.makeText(applicationContext, it.name, Toast.LENGTH_SHORT).show()
            }
            else -> {
                saveDataInSharePreference(it)
            }
        }
    }

    fun saveDataInSharePreference(data: SaveEntries) {
        val editor = prefs!!.edit()
        editor.clear()
        editor.putString("name_users", data.name)
        editor.putInt("employee_id_user", data.id)
        editor.putString("entry_date", data.dates)
        editor.apply()
        callIntent()
    }

    fun validateDateEntries(): Boolean {
        val name_users = prefs!!.getString("entry_date", "")
        return name_users.equals(dates)
    }

    fun callIntent() {
        var intent = Intent(this, ModulesActivity::class.java)
        startActivity(intent)
        finish()
    }

    companion object {
        private val TAG = "ModulesActivity"
        const val DEVICE_STATE_PERMISSION = 101

    }
}
