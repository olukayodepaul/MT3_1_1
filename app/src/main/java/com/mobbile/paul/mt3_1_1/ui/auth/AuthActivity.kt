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
import com.jakewharton.threetenabp.AndroidThreeTen
import com.mobbile.paul.mt3_1_1.R
import com.mobbile.paul.mt3_1_1.models.SaveEntries
import com.mobbile.paul.mt3_1_1.ui.modules.ModulesActivity
import com.mobbile.paul.mt3_1_1.util.Utils.Companion.PREFS_FILENAME
import com.mobbile.paul.mt3_1_1.util.Utils.Companion.getDate
import com.mobbile.paul.mt3_1_1.util.Utils.Companion.getTime
import com.mobiletraderv.paul.daggertraining.BaseActivity
import kotlinx.android.synthetic.main.activity_auth.*
import org.threeten.bp.ZonedDateTime
import javax.inject.Inject


@SuppressLint("ByteOrderMark")
class AuthActivity : BaseActivity() {

    @Inject
    internal lateinit var modelFactory: ViewModelProvider.Factory

    lateinit var vmodel: AuthViewModel

    var zdate: ZonedDateTime = ZonedDateTime.now()

    var prefs: SharedPreferences? = null

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
        vmodel.authMutable().observe(this, ErrorObserver)
        vmodel.authObservable().observe(this, intentObserver)

        Log.d(TAG, "$getDate $getTime")
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

            val name_users = prefs!!.getString("entry_date", "")

            vmodel.callAuthApi("a89L656", "5226", "351736103817460", dates, name_users.equals(dates))

        } else {
            makeRequest()
        }
    }

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

    private val ErrorObserver = Observer<String> {
        showProgressBar(false)
        Toast.makeText(applicationContext, it, Toast.LENGTH_SHORT).show()
    }

    private val intentObserver = Observer<SaveEntries> {
        val editor = prefs!!.edit()
        editor.clear()
        editor.putString("name_users", it.name)
        editor.putInt("employee_id_user", it.id)
        editor.putString("entry_date", it.dates)
        editor.putString("customerno", it.customerno)
        editor.apply()
        callIntent()
    }

    fun callIntent() {
        val intent = Intent(this, ModulesActivity::class.java)
        startActivity(intent)
        finish()
    }

    companion object {
        private val TAG = "AuthActivity"
        const val DEVICE_STATE_PERMISSION = 101

    }
}
