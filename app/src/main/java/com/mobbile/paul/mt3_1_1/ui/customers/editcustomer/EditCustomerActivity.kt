package com.mobbile.paul.mt3_1_1.ui.customers.editcustomer


import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.mobbile.paul.mt3_1_1.BuildConfig
import com.mobbile.paul.mt3_1_1.R
import com.mobbile.paul.mt3_1_1.models.Attendance
import com.mobbile.paul.mt3_1_1.models.ProductTypeRoom
import com.mobbile.paul.mt3_1_1.models.RepCustomers
import com.mobbile.paul.mt3_1_1.ui.customers.CustomerViewModel
import com.mobbile.paul.mt3_1_1.ui.customers.pictures.TakeOutletPicture
import com.mobbile.paul.mt3_1_1.ui.modules.ModulesActivity
import com.mobbile.paul.mt3_1_1.util.Utils.Companion.isInternetAvailable
import com.mobbile.paul.mt3_1_1.viewmodel.EditCustomer
import com.mobiletraderv.paul.daggertraining.BaseActivity
import kotlinx.android.synthetic.main.activity_edit_customer.*
import java.util.*
import javax.inject.Inject


class EditCustomerActivity : BaseActivity() {

    @Inject
    internal lateinit var modelFactory: ViewModelProvider.Factory

    private var mFusedLocationClient: FusedLocationProviderClient? = null

    lateinit var vmodel: CustomerViewModel

    private var hasGps = false

    lateinit var mLocationManager: LocationManager

    private lateinit var customers: RepCustomers

    lateinit var customerClassAdapter: CustomerClassSpinnerAdapter

    lateinit var preferedLangAdapter: PreferedLanguageSpinnerAdapter

    lateinit var outletTypeAdapter: OutletTypeSpinnerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_customer)
        vmodel = ViewModelProviders.of(this, modelFactory)[CustomerViewModel::class.java]
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        customers = intent.extras!!.getParcelable("extra_item")!!
        customer_name_edit.setText(customers.outletname)
        contact_name_edit.setText(customers.contactname)
        address_edit.setText(customers.outletaddress)
        phone_number_edit.setText(customers.contactphone)

        customerClassAdapter = CustomerClassSpinnerAdapter()
        preferedLangAdapter = PreferedLanguageSpinnerAdapter()
        outletTypeAdapter = OutletTypeSpinnerAdapter()

        backbtn.setOnClickListener {
            onBackPressed()
        }

        take_photo.setOnClickListener {
            val intent = Intent(this, TakeOutletPicture::class.java)
            intent.putExtra("urno", customers.urno)
            intent.putExtra("url", customers.outlet_pic)
            startActivity(intent)
        }

        registerBtn.setOnClickListener {
            if(!isInternetAvailable(this)) {
                Messages(2, "Network Error", "No Internet Connection, Thanks!")
            }else{
                showProgressBar(true)
                onClickCheckToLocationPermission()
            }
        }

        vmodel.fetchSpinners().observe(this, languageObserver)
        vmodel.mResponse().observe(this, ObserProfileEdit)
    }

    private val ObserProfileEdit = Observer<EditCustomer> {
        showProgressBar(false)
        if(it.status==200){
            Messages(1, "Success", it.msg)
        }else{
            Messages(2, "Error", it.msg)
        }
    }

    val languageObserver = Observer<List<ProductTypeRoom>> {
        if (it != null) {
            val outletClassList = ArrayList<String>()
            val preLangsList = ArrayList<String>()
            val outletTypeList = ArrayList<String>()
            if (customerClassAdapter.size() > 0) {
                customerClassAdapter.clear()
            }
            if (preferedLangAdapter.size() > 0) {
                preferedLangAdapter.clear()
            }
            if (outletTypeAdapter.size() > 0) {
                outletTypeAdapter.clear()
            }
            for (i in it.indices) {
                when (it[i].sep) {
                    1 -> {
                        outletClassList.add(it[i].name)
                        customerClassAdapter.add(it[i].id, it[i].name)
                    }
                    2 -> {
                        preLangsList.add(it[i].name)
                        preferedLangAdapter.add(it[i].id, it[i].name)
                    }
                    3 -> {
                        outletTypeList.add(it[i].name)
                        outletTypeAdapter.add(it[i].id, it[i].name)
                    }
                }
            }
            val mOutletClass =
                ArrayAdapter(this, android.R.layout.simple_spinner_item, outletClassList)
            mOutletClass.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            custClass!!.adapter = mOutletClass
            custClass!!.setSelection(customerClassAdapter.getIndexById(customers.outletclassid.toInt()))

            val mPreferedLang =
                ArrayAdapter(this, android.R.layout.simple_spinner_item, preLangsList)
            mPreferedLang.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            preflang!!.adapter = mPreferedLang
            preflang!!.setSelection(preferedLangAdapter.getIndexById(customers.outletlanguageid.toInt()))

            val mOutletType =
                ArrayAdapter(this, android.R.layout.simple_spinner_item, outletTypeList)
            mOutletType.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            outlettypeedit!!.adapter = mOutletType
            outlettypeedit!!.setSelection(outletTypeAdapter.getIndexById(customers.outlettypeid.toInt()))
            showProgressBar(false)
        }
    }

    public override fun onStart() {
        super.onStart()
    }

    private fun callGpsIntent() {
        val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
        startActivityForResult(intent, RC_ENABLE_LOCATION)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            RC_ENABLE_LOCATION -> {
                mLocationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
                hasGps = mLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
                if (!hasGps) {
                    callGpsIntent()
                }
            }
        }
    }

    private fun onClickCheckToLocationPermission() {

        mLocationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        hasGps = mLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)

        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_DENIED
            && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_DENIED){
            startLocationPermissionRequest()
        }else if(!hasGps){
            callGpsIntent()
        }else{
            mFusedLocationClient!!.lastLocation.addOnCompleteListener(this) {

                if (it.isSuccessful && it.result != null) {

                    val lastLocation: Location = it.result!!

                    val outletName = customer_name_edit.text.toString()
                    val contactName = contact_name_edit.text.toString()
                    val address = address_edit.text.toString()
                    val phones = phone_number_edit.text.toString()
                    val outletClass = customerClassAdapter.getValueId(custClass.selectedItem.toString())
                    val prefLang = preferedLangAdapter.getValueId(preflang.selectedItem.toString())
                    val outletTypeId = outletTypeAdapter.getValueId(outlettypeedit.selectedItem.toString())

                    vmodel.reSetCustomerProfile(
                        outletName,
                        contactName,
                        address,
                        phones,
                        outletClass,
                        prefLang,
                        outletTypeId,
                        customers.urno,
                        lastLocation.latitude,
                        lastLocation.longitude
                    )
                }else{
                    Messages(2, "GPS Error", "Please Change your GPS setting to Higher Accuracy. Thanks!!")
                }
            }
        }
    }

    private fun startLocationPermissionRequest() {
        ActivityCompat.requestPermissions(this@EditCustomerActivity,
            arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION), REQUEST_PERMISSIONS_REQUEST_CODE)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        when (requestCode) {
            REQUEST_PERMISSIONS_REQUEST_CODE -> {
                if (grantResults.isEmpty() || grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                    startLocationPermissionRequest()
                }
            }
        }
    }

    companion object{
        var REQUEST_PERMISSIONS_REQUEST_CODE = 1000
        var TAG = "EditCustomerActivity"
        var RC_ENABLE_LOCATION = 1000
    }

    private fun Messages(s: Int, title: String, msg: String?) {
        val builder = AlertDialog.Builder(this, R.style.AlertDialogDanger)
        builder.setMessage(msg)
            .setTitle(title)
            .setCancelable(false)
            .setIcon(R.drawable.icons8_google_alerts_100)
            .setNegativeButton(
                "OK"
            ) { _, _ ->
                if(s==1) {
                    var intent = Intent(this, ModulesActivity::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                    startActivity(intent)
                }
            }
        val dialog = builder.create()
        dialog.show()
    }


}
