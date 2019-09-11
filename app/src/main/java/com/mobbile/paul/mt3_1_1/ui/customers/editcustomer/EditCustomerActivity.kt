package com.mobbile.paul.mt3_1_1.ui.customers.editcustomer



import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.widget.ArrayAdapter
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.mobbile.paul.mt3_1_1.R
import com.mobbile.paul.mt3_1_1.models.Attendance
import com.mobbile.paul.mt3_1_1.models.ProductTypeRoom
import com.mobbile.paul.mt3_1_1.models.RepCustomers
import com.mobbile.paul.mt3_1_1.ui.customers.CustomerViewModel
import com.mobbile.paul.mt3_1_1.ui.customers.pictures.TakeOutletPicture
import com.mobbile.paul.mt3_1_1.ui.modules.ModulesActivity
import com.mobiletraderv.paul.daggertraining.BaseActivity
import kotlinx.android.synthetic.main.activity_edit_customer.*
import java.util.*
import javax.inject.Inject



class EditCustomerActivity : BaseActivity() {

    @Inject
    internal lateinit var modelFactory: ViewModelProvider.Factory

    lateinit var vmodel: CustomerViewModel

    private lateinit var customers: RepCustomers

    lateinit var customerClassAdapter: CustomerClassSpinnerAdapter

    lateinit var preferedLangAdapter : PreferedLanguageSpinnerAdapter

    lateinit var outletTypeAdapter: OutletTypeSpinnerAdapter

    private var hasGps = false

    private lateinit var fusedLocationClient: FusedLocationProviderClient

    lateinit var mLocationManager: LocationManager

    private var mLastLocation: Location? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_customer)
        vmodel = ViewModelProviders.of(this, modelFactory)[CustomerViewModel::class.java]
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        customers = intent.extras!!.getParcelable("extra_item")!!

        customer_name_edit.setText(customers.outletname)
        contact_name_edit.setText(customers.contactname)
        address_edit.setText(customers.outletaddress)
        phone_number_edit.setText(customers.contactphone)

        customerClassAdapter = CustomerClassSpinnerAdapter()
        preferedLangAdapter = PreferedLanguageSpinnerAdapter()
        outletTypeAdapter = OutletTypeSpinnerAdapter()

        checkLocationPermission()

        registerBtn.setOnClickListener {
            showProgressBar(true)
            PickLocation()
        }

        take_photo.setOnClickListener {
            var intent = Intent(this, TakeOutletPicture::class.java)
            intent.putExtra("urno", customers.urno)
            intent.putExtra("url", customers.outlet_pic)
            startActivity(intent)
        }

        vmodel.MutableOfLiveData().observe(this, ObserProfileEdit)
    }

    val languageObserver = Observer<List<ProductTypeRoom>> {
        if(it!=null) {
            val outletClassList = ArrayList<String>()
            val preLangsList = ArrayList<String>()
            val outletTypeList = ArrayList<String>()
            if (customerClassAdapter.size() > 0) { customerClassAdapter.clear()}
            if (preferedLangAdapter.size() > 0) { preferedLangAdapter.clear()}
            if (outletTypeAdapter.size() > 0) { outletTypeAdapter.clear()}
            for(i in it.indices) {
                when(it[i].sep) {
                    1->{
                        outletClassList.add(it[i].name)
                        customerClassAdapter.add(it[i].id, it[i].name)
                    }
                    2->{
                        preLangsList.add(it[i].name)
                        preferedLangAdapter.add(it[i].id, it[i].name)
                    }
                    3->{
                        outletTypeList.add(it[i].name)
                        outletTypeAdapter.add(it[i].id, it[i].name)
                    }
                }
            }
            val mOutletClass = ArrayAdapter(this, android.R.layout.simple_spinner_item, outletClassList)
            mOutletClass.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            custClass!!.adapter = mOutletClass
            custClass!!.setSelection(customerClassAdapter.getIndexById(customers.outletclassid.toInt()))

            val mPreferedLang = ArrayAdapter(this, android.R.layout.simple_spinner_item, preLangsList)
            mPreferedLang.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            preflang!!.adapter = mPreferedLang
            preflang!!.setSelection(preferedLangAdapter.getIndexById(customers.outletlanguageid.toInt()))

            val mOutletType = ArrayAdapter(this, android.R.layout.simple_spinner_item, outletTypeList)
            mOutletType.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            outlettypeedit!!.adapter = mOutletType
            outlettypeedit!!.setSelection(outletTypeAdapter.getIndexById(customers.outlettypeid.toInt()))
            showProgressBar(false)
        }
    }

    companion object {
        const val  PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1000
        val TAG = "EditCustomerActivity"
        const val RC_ENABLE_LOCATION = 1
    }

    private fun RationalForResponceMsg(s:Int, title: String, msg: String) {
        val builder = AlertDialog.Builder(this, R.style.AlertDialogDanger)
        builder.setMessage(msg)
            .setTitle(title)
            .setCancelable(false)
            .setIcon(R.drawable.icons8_google_alerts_100)
            .setNegativeButton("OK"
            ) { _, _ ->
                when(s){
                    1->{
                        var intent = Intent(this, ModulesActivity::class.java)
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                        startActivity(intent)
                    }
                }
            }
        val dialog  = builder.create()
        dialog.show()
    }

    fun checkLocationPermission() {
        val accessPermissionStatus = ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
        val coarsePermissionStatus = ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)

        if (accessPermissionStatus == PackageManager.PERMISSION_GRANTED
            && coarsePermissionStatus == PackageManager.PERMISSION_GRANTED) {
            GPSRationaleEnable()
        } else {
            requestLocationPermission()
        }
    }

    private fun GPSRationaleEnable() {
        mLocationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        hasGps = mLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
        if(!hasGps) {
            val builder = AlertDialog.Builder(this, R.style.AlertDialogDanger)
            builder.setMessage("Your location need to be put On")
                .setTitle("GPS Enable")
                .setIcon(R.drawable.icons8_google_alerts_100)
                .setCancelable(false)
                .setNegativeButton("OK") { _, _ ->
                    callGpsIntent()
                }
            val dialog  = builder.create()
            dialog.show()
        }else {
            vmodel.fetchSpinners().observe(this, languageObserver)
            Log.d(TAG,"LOCATION ENABLE 11")
        }
    }

    private fun requestLocationPermission() {
        ActivityCompat.requestPermissions(this,
            arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
            PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION
        )
    }

    private fun callGpsIntent() {
        val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
        startActivityForResult(intent, RC_ENABLE_LOCATION)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            RC_ENABLE_LOCATION -> {
                checkGpsEnabledAndPrompt()
            }
        }
    }

    private fun checkGpsEnabledAndPrompt() {
        mLocationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        hasGps = mLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
        if (hasGps) {
            vmodel.fetchSpinners().observe(this, languageObserver)
        }else {
            GPSRationaleEnable()
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        when (requestCode) {
            ModulesActivity.PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION -> {
                if (grantResults.isEmpty() || grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                    GPSPermissionRationaleAlert()
                    Log.d(ModulesActivity.TAG, "not allow permission granted")

                }else{
                    mLocationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
                    hasGps = mLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
                    if(hasGps){
                        vmodel.fetchSpinners().observe(this, languageObserver)
                    }else{
                        callGpsIntent()
                    }
                }
            }
        }
    }

    private fun GPSPermissionRationaleAlert() {
        val builder = AlertDialog.Builder(this, R.style.AlertDialogDanger)
        builder.setMessage("Without allowing GPS permission, this application will not work for you")
            .setTitle("GPS Permission")
            .setCancelable(false)
            .setIcon(R.drawable.icons8_google_alerts_100)
            .setNegativeButton("OK") { _, _ ->
                requestLocationPermission()
            }
        val dialog  = builder.create()
        dialog.show()
    }

    fun PickLocation() {

        mLocationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        hasGps = mLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)

        val accessPermissionStatus = ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
        val coarsePermissionStatus = ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)

        if (accessPermissionStatus != PackageManager.PERMISSION_GRANTED
            && coarsePermissionStatus != PackageManager.PERMISSION_GRANTED) {
            requestLocationPermission()
        } else if(!hasGps) {
            GPSRationaleEnable()
        }else{
            fusedLocationClient.lastLocation.addOnCompleteListener {
                if (it.isSuccessful) {
                    mLastLocation = it.result
                    val outletName = customer_name_edit.text.toString()
                    val contactName = contact_name_edit.text.toString()
                    val address = address_edit.text.toString()
                    val phones = phone_number_edit.text.toString()
                    val outletClass = customerClassAdapter.getValueId(custClass.selectedItem.toString())
                    val prefLang = preferedLangAdapter.getValueId(preflang.selectedItem.toString())
                    val outletTypeId = outletTypeAdapter.getValueId(outlettypeedit.selectedItem.toString())
                    vmodel.reSetCustomerProfile(outletName, contactName, address, phones, outletClass, prefLang, outletTypeId, customers.urno, mLastLocation!!.latitude,mLastLocation!!.longitude)
                }
            }
        }
    }

    val ObserProfileEdit = Observer<Attendance> {
        showProgressBar(false)
        if(it!=null){
            if(it.status==200) {
                RationalForResponceMsg(1, "Success", it.msg)
            }else {
                RationalForResponceMsg(2, "Error", it.msg)
            }
        }else{
            RationalForResponceMsg(2, "Error", "Network Error, Please check your Internet. Thanks!")
        }
    }

}
