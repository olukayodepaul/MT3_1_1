package com.mobbile.paul.mt3_1_1.ui.modules



import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.LocationManager
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlin_project.Models.RoomModel.ModulesRoom
import com.mobbile.paul.mt3_1_1.R
import com.mobiletraderv.paul.daggertraining.BaseActivity
import kotlinx.android.synthetic.main.activity_modules.*
import javax.inject.Inject

class ModulesActivity : BaseActivity() {

    @Inject
    internal lateinit var modelFactory: ViewModelProvider.Factory
    lateinit var vmodel: ModulesViewModel
    private lateinit var mAdapter: ModulesAdapter
    lateinit var mLocationManager: LocationManager
    private var hasGps = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_modules)
        vmodel = ViewModelProviders.of(this, modelFactory)[ModulesViewModel::class.java]
        vmodel.getAllUsersModules().observe(this, ModulesObservers)
        showProgressBar(true)
        checkLocationPermission()
    }

    val ModulesObservers = Observer<List<ModulesRoom>> { data ->
        if (data != null) {
            showProgressBar(false)
            var list: List<ModulesRoom> = data
            mAdapter = ModulesAdapter(list, this)
            mAdapter.notifyDataSetChanged()
            module_recycler.adapter = mAdapter
        }
    }

    fun initViews() {
        module_recycler.setHasFixedSize(true)
        val layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(this)
        module_recycler.layoutManager = layoutManager
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

    private fun requestLocationPermission() {
        ActivityCompat.requestPermissions(this,
            arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
            PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        when (requestCode) {
            PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION -> {
                if (grantResults.isEmpty() || grantResults[0] != PackageManager.PERMISSION_GRANTED) {

                    GPSPermissionRationaleAlert()
                    Log.d(TAG, "not allow permission granted")

                }else{
                    mLocationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
                    hasGps = mLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
                    if(hasGps){
                        initViews()
                    }else{
                        callGpsIntent()
                    }
                }
            }
        }
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
            initViews()
            Log.d(TAG,"LOCATION ENABLE 1")
        }else{
            GPSRationaleEnable()
        }
    }

    private fun GPSPermissionRationaleAlert() {
        val builder = AlertDialog.Builder(this)
        builder.setMessage("Without allowing GPS permission, this application will not work for you")
            .setTitle("GPS Permission")
            .setCancelable(false)
            .setNegativeButton("OK") { _, _ ->
                requestLocationPermission()
            }
        val dialog  = builder.create()
        dialog.show()
    }

    private fun GPSRationaleEnable() {
        mLocationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        hasGps = mLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
        if(!hasGps) {
            val builder = AlertDialog.Builder(this)
            builder.setMessage("Your location need to be put On")
                .setTitle("GPS Enable")
                .setCancelable(false)
                .setNegativeButton("OK") { _, _ ->
                    callGpsIntent()
                }
            val dialog  = builder.create()
            dialog.show()
        }else {
            initViews()
            Log.d(TAG,"LOCATION ENABLE 11")
        }
    }

    companion object{
        const val RC_ENABLE_LOCATION = 1
        var TAG = "BaseActivity"
        const val PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1235
    }
}
