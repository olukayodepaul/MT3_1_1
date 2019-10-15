package com.mobbile.paul.mt3_1_1.ui.sales.sales.sales_map_manager


import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.graphics.Color
import android.location.LocationManager
import android.net.Uri
import android.os.Bundle
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
import android.view.View
import com.google.android.gms.location.*
import com.mobbile.paul.mt3_1_1.BaseActivity
import com.mobbile.paul.mt3_1_1.ui.sales.SalesViewpager
import com.mobbile.paul.mt3_1_1.ui.sales.sales.salesentries.SalesEntries
import com.mobbile.paul.mt3_1_1.util.Utils.Companion.LATLNG
import com.mobbile.paul.mt3_1_1.util.Utils.Companion.PREFS_FILENAME
import com.mobbile.paul.mt3_1_1.util.Utils.Companion.insideRadius
import com.mobbile.paul.mt3_1_1.util.Utils.Companion.isInternetAvailable
import com.mobbile.paul.mt3_1_1.viewmodel.CloseOutlets
import kotlinx.android.synthetic.main.activity_users_map.*
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList
import kotlin.Char as Char1

class UsersMap : BaseActivity() {

    @Inject
    internal lateinit var modelFactory: ViewModelProvider.Factory

    lateinit var vmodel: SalesMapManagerViewModel

    lateinit var mapFragment: SupportMapFragment

    lateinit var googleMap: GoogleMap

    lateinit var data: GoogleGetApi

    lateinit var durt: TextView

    lateinit var dis: TextView

    var urno: String? = ""

    var token: String? = ""

    var startinglat: String? = ""

    var startinglng: String? = ""

    var outletname: String? = ""

    var visit_sequence: String? = ""

    var defaulttoken: String? = ""

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
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
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

        defaulttoken = intent.getStringExtra("defaulttoken")

        if(sharePref!!.getString("assertiondate", "")!=SimpleDateFormat("yyyy-MM-dd").format(Date())) {
            Errorchecker(4, "Attendant Error","Please Clock out to proceed. Thanks!")
        }

        backbtn.setOnClickListener {
            onBackPressed()
        }

        navigations.setOnClickListener {
            val mode = pref!!.getString("mode", "").toString().single()
            val ads = "$startinglat,$startinglng"
            startMapIntent(this, ads, mode, 't')
        }

        clockoutbtn.setOnClickListener {
            requestLocation()
        }

        resumebtn.setOnClickListener {
            outletClose()
        }

        r_outlet_name.text = outletname

        if (!isInternetAvailable(this)) {
            msgError("No Internet Connection! Thanks!")
        } else {
            checkLocationPermission()
        }

        vmodel.MutableProcess().observe(this, ObserveClseOutlets)
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
                Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION
            ), PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION
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
        val mode = pref!!.getString("mode", "")
        mLocationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        hasGps = mLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
        if (hasGps) {
            if (!startinglng.isNullOrBlank() || !startinglng.isNullOrBlank()) {
                initMap(
                    "$startinglat,$startinglng",
                    "$endinglat,$endinglng",
                    "false",
                    mode!!,
                    getString(R.string.keys)
                )
            }
        } else {
            GPSRationaleEnable()
        }
    }

    private fun GPSRationaleEnable() {
        val mode = pref!!.getString("mode", "")
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
                    mode!!,
                    getString(R.string.keys)
                )
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
            resumebtn.visibility = View.VISIBLE
            clockoutbtn.visibility =View.VISIBLE
    }

    fun calculateRouteDistance() {
            var distanceCovered: String = data.routes[0].legs[0].distance.text
            var duration: String = data.routes[0].legs[0].duration.text
            durt.text = duration
           dis.text = distanceCovered
    }

    fun locationRouteOnMap() {
        //for the google data
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
            .setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN))
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

    fun startMapIntent(ctx: Context, ads: String, mode: Char1, avoid: Char1): Any {
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

        mLocationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        hasGps = mLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)

        val accessPermissionStatus =
            ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
        val coarsePermissionStatus =
            ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)

        if (accessPermissionStatus == PackageManager.PERMISSION_DENIED
            && coarsePermissionStatus == PackageManager.PERMISSION_DENIED
        ) {
            requestLocationPermission()
        } else if (!hasGps) {
            callGpsIntent()
        } else {
            fusedLocationClient.lastLocation.addOnCompleteListener {

                if (it.isSuccessful && it.result != null) {

                    val mLastLocation = it.result

                    val checkCustomerOutlet: Boolean = insideRadius(
                        mLastLocation!!.latitude,
                        mLastLocation!!.longitude,
                        endinglat!!.toDouble(),
                        endinglng!!.toDouble()
                    )
                    if (!checkCustomerOutlet) {
                        msgError("You are not at the corresponding outlet. Thanks!")
                    } else {
                        /*vmodel.confirmTask(
                            pref!!.getInt("employee_id_user", 0),
                            dateStamp,
                            mLastLocation.latitude,
                            mLastLocation.longitude
                        ).observe(this, observeClockOut)*/
                        val intent = Intent(this, SalesEntries::class.java)
                        intent.putExtra("urno", urno)
                        intent.putExtra("token", token)
                        intent.putExtra("outletname", outletname)
                        intent.putExtra("defaulttoken", defaulttoken)
                        intent.putExtra("visit_sequence", visit_sequence)
                        intent.putExtra("clat", mLastLocation.latitude)
                        intent.putExtra("clng", mLastLocation.latitude)
                        intent.putExtra("arrivallat", endinglat)
                        intent.putExtra("arrivalng", endinglng)
                        intent.putExtra("distance", dis.text.toString())
                        intent.putExtra("arivaltime", SimpleDateFormat("HH:mm:ss").format(Date()))
                        startActivity(intent)
                    }
                } else {
                    msgError("Please Change your GPS setting to Higher Accuracy. Thanks!")
                }

            }
        }
    }

    /*@SuppressLint("SimpleDateFormat")
    private var observeClockOut = Observer<salesEntryResponses> {
        if (it != null) {
            when (it.status) {
                200 -> {
                    val intent = Intent(this, SalesEntries::class.java)
                    intent.putExtra("urno", urno)
                    intent.putExtra("token", token)
                    intent.putExtra("outletname", outletname)
                    intent.putExtra("defaulttoken", defaulttoken)
                    intent.putExtra("visit_sequence", visit_sequence)
                    intent.putExtra("clat", it.curlat)
                    intent.putExtra("clng", it.curlng)
                    intent.putExtra("distance", dis.text.toString())
                    intent.putExtra("arivaltime", SimpleDateFormat("HH:mm:ss").format(Date()))
                    startActivity(intent)
                }
                400 -> {
                    msgError(it.msg)
                }
            }
        }
    }*/

    @SuppressLint("SimpleDateFormat")
    fun outletClose() {

        mLocationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        hasGps = mLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)

        val accessPermissionStatus =
            ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
        val coarsePermissionStatus =
            ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)

        if (accessPermissionStatus == PackageManager.PERMISSION_DENIED
            && coarsePermissionStatus == PackageManager.PERMISSION_DENIED
        ) {
            requestLocationPermission()

        } else if (!hasGps) {
            callGpsIntent()
        } else {
            showProgressBar(true)
            fusedLocationClient.lastLocation.addOnCompleteListener {

                if (it.isSuccessful && it.result != null) {

                    val mLastLocation = it.result

                    val checkCustomerOutlet: Boolean = insideRadius(
                        mLastLocation!!.latitude,
                        mLastLocation.longitude,
                        endinglat!!.toDouble(),
                        endinglng!!.toDouble()
                    )
                    if (!checkCustomerOutlet) {
                        showProgressBar(false)
                        msgError("You are not at the corresponding outlet. Thanks!")
                    } else {
                        vmodel.setOutletClose(
                            pref!!.getInt("employee_id_user", 0),
                            urno.toString(),
                            SimpleDateFormat("yyyy-MM-dd").format(Date()),
                            SimpleDateFormat("HH:mm:ss").format(Date()),
                            mLastLocation.latitude.toString(),
                            mLastLocation.longitude.toString(),
                            dis.text.toString(),
                            visit_sequence!!
                        )
                    }
                } else {
                    msgError("Please Change your GPS setting to Higher Accuracy, if not. Thanks!")
                }
            }
        }
    }

    val ObserveClseOutlets = Observer<CloseOutlets> {
        showProgressBar(false)
        if (it.status == 200) {
            Errorchecker(1, "", it.msg)
        } else {
            Errorchecker(2, "Error", it.msg)
        }
    }

    private fun Errorchecker(status: Int, title: String, msg: String) {
        val builder = AlertDialog.Builder(this, R.style.AlertDialogDanger)
        builder.setMessage(msg)
            .setTitle(title)
            .setIcon(R.drawable.icons8_google_alerts_100)
            .setCancelable(false)
            .setPositiveButton("Ok") { _, _ ->
                if (status == 1) {
                    sharePref!!.edit().clear().apply()
                    val editor = sharePref!!.edit()
                    editor.putString("starting_lat", endinglat)
                    editor.putString("starting_lng", endinglng)
                    editor.putString("assertiondate", SimpleDateFormat("yyyy-MM-dd").format(Date()))
                    editor.apply()
                    val intent = Intent(this, SalesViewpager::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                    startActivity(intent)
                }else if(status == 4){
                    val intent = Intent(this, SalesViewpager::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                    startActivity(intent)
                }
            }
        val dialog = builder.create()
        dialog.show()
    }

    private fun msgError(msg: String) {
        val builder = AlertDialog.Builder(this, R.style.AlertDialogDanger)
        builder.setMessage(msg)
            .setTitle("Notice!")
            .setIcon(R.drawable.icons8_google_alerts_100)
            .setCancelable(false)
            .setNegativeButton("OK") { _, _ ->
            }
        val dialog = builder.create()
        dialog.show()
    }
}
