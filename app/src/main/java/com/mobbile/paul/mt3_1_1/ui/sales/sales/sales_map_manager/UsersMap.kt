package com.mobbile.paul.mt3_1_1.ui.sales.sales.sales_map_manager


import android.content.Context
import android.location.Location
import android.location.LocationManager
import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
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
    }

    private fun getUrl(origin: LatLng, dest: LatLng): String {
        val str_origin = "origin=" + origin.latitude + "," + origin.longitude
        val str_dest = "destination=" + dest.latitude + "," + dest.longitude
        val sensor = "sensor=false"
        val parameters = "$str_origin&$str_dest&$sensor"
        val output = "json"
        val url = "https://maps.googleapis.com/maps/api/directions/$output?$parameters"
        return url
    }

    private fun initMap() {
        mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(OnMapReadyCallback{
            showProgressBar(false)
            googleMap = it
            googleMap.isBuildingsEnabled = true
            val latlng = LatLng(13.03, 77.6)
            googleMap.addMarker(MarkerOptions().position(latlng).title("My Location"))
            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latlng,10f))
        })
    }

    companion object{
        var TAG = "UsersMap"
    }
}
