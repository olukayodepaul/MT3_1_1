package com.mobbile.paul.mt3_1_1.ui.sales.sales.sales_map_manager


import android.graphics.Color
import android.location.LocationManager
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.github.salomonbrys.kotson.fromJson
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import com.google.maps.android.PolyUtil
import com.mobbile.paul.mt3_1_1.R
import com.mobbile.paul.mt3_1_1.models.GoogleGetApi
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
        var origin : String  = "13.0356745,77.5881522"
        var destination : String  = "13.029727, 77.5933021"
        var sensor: String = "false"
        var mode: String = "false"
        var key: String = "AIzaSyAUoAalxX7BMwO5G6LCPAV28azXPMMms1c"
        initMap(origin, destination, sensor, mode, key)
    }

    private fun initMap(origin: String, destination: String, sensor: String, mode: String, key: String) {

                mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
                mapFragment.getMapAsync(OnMapReadyCallback{
                googleMap = it
                googleMap.isBuildingsEnabled = true
                val latlng1 = LatLng(13.0357389, 77.5881899)
                val latlng2 = LatLng(13.0355787, 77.58870739999999)
                val latlng3 = LatLng(13.0355787, 77.58870739999999)
                val latlng4 = LatLng(13.0392081, 77.58936609999999)
                googleMap.addMarker(MarkerOptions().position(latlng1).title("My Location"))
                googleMap.addMarker(MarkerOptions().position(latlng4 ).title("My Location two"))
                //vmodel.GoogleMapApi(origin, destination, sensor, mode, key).observe(this, observeMapApiResponse)
                    googleMap.addPolyline(
                        PolylineOptions()
                            .add(latlng1)
                            .add(latlng2)
                            .add(latlng3)
                            .add(latlng4)
                            .width(8f)
                            .color(Color.BLUE)
                    )
                    googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latlng1,10f))
            })
    }


    //val observeMapApiResponse = Observer<GoogleGetApi> { data ->

    //}

        /*private fun setPolygonLineOptions() {
        val polygonOptions =  PolylineOptions()

        for (i in result.indices) {
            polygonOptions
                .addAll(result[i])
                .width(20f)
                .color(Color.RED)
                .jointType(JointType.ROUND)
                .endCap(RoundCap())
                .startCap(RoundCap())
         }
        googleMap.addPolyline(polygonOptions)
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(LatLng(13.03, 77.6),10f))
    }*/


    companion object{
        var TAG = "UsersMap"
    }
}


