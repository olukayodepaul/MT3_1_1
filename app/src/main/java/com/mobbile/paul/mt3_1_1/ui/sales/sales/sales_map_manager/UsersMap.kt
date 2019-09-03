package com.mobbile.paul.mt3_1_1.ui.sales.sales.sales_map_manager


import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.graphics.Color
import android.location.Location
import android.location.LocationManager
import android.net.Uri
import android.os.Bundle
import android.os.Looper
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import com.mobbile.paul.mt3_1_1.R
import com.mobbile.paul.mt3_1_1.models.GoogleGetApi
import android.provider.Settings
import com.google.android.gms.location.*
import com.mobbile.paul.mt3_1_1.models.EmployeesApi
import com.mobbile.paul.mt3_1_1.models.salesEntryResponses
import com.mobbile.paul.mt3_1_1.ui.sales.sales.salesentries.SalesEntries
import com.mobbile.paul.mt3_1_1.util.Utils.Companion.LATLNG
import com.mobbile.paul.mt3_1_1.util.Utils.Companion.PREFS_FILENAME
import com.mobbile.paul.mt3_1_1.util.Utils.Companion.insideRadius
import com.mobiletraderv.paul.daggertraining.BaseActivity
import kotlinx.android.synthetic.main.activity_users_map.*
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList

class UsersMap : BaseActivity() {

    @Inject
    internal lateinit var modelFactory: ViewModelProvider.Factory
    lateinit var vmodel: SalesMapManagerViewModel
    lateinit var mapFragment: SupportMapFragment
    lateinit var googleMap: GoogleMap

    private var mLastLocation: Location? = null

    lateinit var data: GoogleGetApi

    lateinit var durt: TextView

    lateinit var dis: TextView

    var urno: String? = ""

    var token: String? = ""

    var startinglat: String? = ""

    var startinglng: String? = ""

    var outletname: String? = ""

    var visit_sequence: String? = ""

    private lateinit var fusedLocationClient: FusedLocationProviderClient

    lateinit var mLocationManager: LocationManager

    var sharePref: SharedPreferences? = null

    var pref: SharedPreferences? = null

    var endinglat: String? = ""

    var endinglng: String? = ""

    private var hasGps = false

    var dateStamp: String = ""

    @SuppressLint("SimpleDateFormat")
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_users_map)
        vmodel = ViewModelProviders.of(this, modelFactory)[SalesMapManagerViewModel::class.java]
        durt = findViewById(R.id.duration)
        dis = findViewById(R.id.kilometer)
        showProgressBar(true)
        sharePref = getSharedPreferences(LATLNG, Context.MODE_PRIVATE)
        pref = getSharedPreferences(PREFS_FILENAME, Context.MODE_PRIVATE)

        dateStamp = SimpleDateFormat("yyyy-MM-dd").format(Date())

        startinglat = sharePref!!.getString("starting_lat", "")

        startinglng = sharePref!!.getString("starting_lng", "")

        endinglat = intent.getStringExtra("lat")

        endinglng = intent.getStringExtra("lng")

        urno = intent.getStringExtra("urno")

        token = intent.getStringExtra("token")

        outletname = intent.getStringExtra("outletname")

        visit_sequence = intent.getStringExtra("visit_sequence")

        checkLocationPermission()

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        backbtn.setOnClickListener {
            onBackPressed()
        }

        imageView8.setOnClickListener {
            val ads = "$startinglat,$startinglng"
            startMapIntent(this, ads, 'w', 't')
            Log.d(TAG, "CHECK THIS")
        }

        clockoutbtn.setOnClickListener {
            requestLocation()
        }

        r_outlet_name.text = outletname
    }

    fun checkLocationPermission() {

        val accessPermissionStatus =
            ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
        val coarsePermissionStatus =
            ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)

        if (accessPermissionStatus == PackageManager.PERMISSION_DENIED && coarsePermissionStatus == PackageManager.PERMISSION_DENIED
        ) {
            requestLocationPermission()
        } else {
            checkIfGpsIsEnale()
        }
    }

    private fun requestLocationPermission() {
        ActivityCompat.requestPermissions(
            this, arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ),
            PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION
        )
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION -> {
                if (grantResults.isEmpty() || grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                    checkIfGpsIsEnale()
                } else {
                    requestLocationPermission()
                }
            }
        }
    }

    private fun checkIfGpsIsEnale() {
        mLocationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        hasGps = mLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
        if (hasGps) {
            if (!startinglng.isNullOrBlank() || !startinglng.isNullOrBlank()) {
                initMap(
                    "$startinglat,$startinglng",
                    "$endinglat,$endinglng",
                    "false",
                    "d",
                    getString(R.string.keys)
                )
                //requestLocation()
            }

        } else {
            GPSRationaleEnable()
        }
    }

    private fun GPSRationaleEnable() {
        mLocationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        hasGps = mLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
        if (!hasGps) {
            val builder = AlertDialog.Builder(this)
            builder.setMessage("Your location need to be put On")
                .setTitle("GPS Enable")
                .setCancelable(false)
                .setNegativeButton("OK") { _, _ ->
                    callGpsIntent()
                }
            val dialog = builder.create()
            dialog.show()
        } else {
            if (!startinglng.isNullOrBlank() || !startinglng.isNullOrBlank()) {
                initMap(
                    "$startinglat,$startinglng",
                    "$endinglat,$endinglng",
                    "false",
                    "d",
                    getString(R.string.keys)
                )
                //requestLocation()
            }
        }
    }

    private fun callGpsIntent() {
        val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
        startActivityForResult(intent, ENABLE_GPS)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            ENABLE_GPS -> {
                GPSRationaleEnable()
            }
        }
    }

    private fun initMap(
        origin: String,
        destination: String,
        sensor: String,
        mode: String,
        key: String
    ) {

        mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(OnMapReadyCallback {
            showProgressBar(false)
            googleMap = it
            vmodel.GoogleMapApi(origin, destination, sensor, mode, key)
                .observe(this, observeMapApiResponse)
        })
    }


    val observeMapApiResponse = Observer<GoogleGetApi> {

        data = it

        setMakerPosition(
            startinglat!!.toDouble(),
            startinglng!!.toDouble(),
            endinglat!!.toDouble(),
            endinglng!!.toDouble()
        )
        calculateRouteDistance()
    }

    fun calculateRouteDistance() {

        var distanceCovered: String = data.routes[0].legs[0].distance.text
        var duration: String = data.routes[0].legs[0].duration.text
        durt.text = duration
        dis.text = distanceCovered
    }

    fun locationRouteOnMap() {

        for (i in 0..(data!!.routes[0].legs[0].steps.size - 1)) {

        }
    }

    private fun setMakerPosition(
        getStartLat: Double, getStartLng: Double,
        getEndLat: Double, getEndtLng: Double
    ) {
        val startLatLng = LatLng(getStartLat, getStartLng)
        val endLatLng = LatLng(getEndLat, getEndtLng)
        googleMap.addMarker(MarkerOptions().position(startLatLng))
        googleMap.addMarker(MarkerOptions().position(endLatLng))
        googleMap.animateCamera(
            CameraUpdateFactory.newLatLngZoom(
                startLatLng,
                15f
            )
        )
    }

    private fun setPolygonLineOptions(result: ArrayList<LatLng>) {
        googleMap.addPolyline(
            PolylineOptions()
                .addAll(result)
                .width(5f)
                .color(Color.BLUE)
                .jointType(JointType.ROUND)
                .endCap(RoundCap())
                .startCap(RoundCap())
        )
    }

    companion object {
        var TAG = "UsersMap"
        const val PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1235
        var ENABLE_GPS = 1000
    }

    fun startMapIntent(ctx: Context, ads: String, mode: Char, avoid: Char): Any {
        val uri = Uri.parse("google.navigation:q=$ads&mode=$mode&avoid=$avoid")
        val mIntent = Intent(Intent.ACTION_VIEW, uri)
        mIntent.`package` = "com.google.android.apps.maps"
        return if (mIntent.resolveActivity(ctx.packageManager) != null) {
            ctx.startActivity(mIntent)
            true
        } else
            false
    }


    private fun requestLocation() {

        val accessPermissionStatus =
            ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
        val coarsePermissionStatus =
            ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)

        if (accessPermissionStatus == PackageManager.PERMISSION_DENIED
            && coarsePermissionStatus == PackageManager.PERMISSION_DENIED
        ) {
            requestLocationPermission()
        } else {

            fusedLocationClient.lastLocation.addOnCompleteListener {

                if (it.isSuccessful) {

                    mLastLocation = it.result

                    var checkCustomerOutlet: Boolean = insideRadius(
                        mLastLocation!!.latitude,
                        mLastLocation!!.longitude,
                        endinglat!!.toDouble(),
                        endinglng!!.toDouble()
                    )

                    if (!checkCustomerOutlet) {
                        //msgError("You are not at the corresponding outlet. Thanks!")

                        val im = pref!!.getInt("employee_id_user", 0)
                        vmodel.confirmTask(im, dateStamp, mLastLocation!!.latitude, mLastLocation!!.longitude).observe(this, observeClockOut)

                    } else {
                        val im = pref!!.getInt("employee_id_user", 0)
                        vmodel.confirmTask(im, dateStamp, mLastLocation!!.latitude, mLastLocation!!.longitude).observe(this, observeClockOut)
                    }
                }
            }
        }
    }

    @SuppressLint("SimpleDateFormat")
    private var observeClockOut = Observer<salesEntryResponses> {
        if (it != null) {
            when (it.status) {
                200 -> {
                    val intent = Intent(this, SalesEntries::class.java)
                    intent.putExtra("urno", urno)
                    intent.putExtra("token", token)
                    intent.putExtra("outletname", outletname)
                    intent.putExtra("visit_sequence", visit_sequence)
                    intent.putExtra("clat", it.curlat)
                    intent.putExtra("clng", it.curlng)
                    intent.putExtra("distance", durt.text.toString())
                    intent.putExtra("arivaltime", SimpleDateFormat("HH:mm:ss").format(Date()))
                    startActivity(intent)
                }
                400 -> {
                    msgError(it.msg)
                }
            }
        }
    }

    private fun msgError(msg: String) {
        val builder = AlertDialog.Builder(this, R.style.AlertDialogDanger)
        builder.setMessage(msg)
            .setTitle("Notice!")
            .setIcon(R.drawable.icons8_error_90)
            .setCancelable(false)
            .setNegativeButton("OK") { _, _ ->
            }
        val dialog = builder.create()
        dialog.show()
    }
}



