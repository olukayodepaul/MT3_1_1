package com.mobbile.paul.mt3_1_1.ui.sales.sales.sales_map_manager


import android.location.LocationManager
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.mobbile.paul.mt3_1_1.R
import com.mobiletraderv.paul.daggertraining.BaseActivity
import javax.inject.Inject

class UsersMap : BaseActivity() {

    @Inject
    internal lateinit var modelFactory: ViewModelProvider.Factory
    lateinit var vmodel: Sales_Map_Manager_ViewModel
    lateinit var mapFragment: SupportMapFragment
    lateinit var googleMap: GoogleMap
    lateinit var locationManager: LocationManager
    private var hasGPS = false //get location using GPS
    private var hasNetwork = false //get location using Network


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_users_map)
        vmodel = ViewModelProviders.of(this, modelFactory)[Sales_Map_Manager_ViewModel::class.java]
        showProgressBar(true)
        initMap()
        var origin : String  = "13.000,13.0000"
        var destination : String  = "13.000,13.0000"
        var sensor: String = "false"
        var mode: String = "false"
        var key: String = "AIzaSyAUoAalxX7BMwO5G6LCPAV28azXPMMms1c"
        vmodel.GoogleMapApi(origin, destination, sensor, mode, key)
           }

    private fun getUrl(origin: LatLng, dest: LatLng): String {
        return ""
    }

    private fun initMap() {
        mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(OnMapReadyCallback{
            showProgressBar(false)
            googleMap = it
            googleMap.isBuildingsEnabled = true

            val latlng = LatLng(13.03, 77.6)
            googleMap.addMarker(MarkerOptions().position(latlng).title("My Location"))

            val latlng2 = LatLng(9.89, 78.11)
            googleMap.addMarker(MarkerOptions().position(latlng2).title("My Location two"))

            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latlng,10f))
        })
    }

    companion object{
        var TAG = "UsersMap"
    }
}
