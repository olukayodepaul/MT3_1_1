package com.mobbile.paul.mt3_1_1.ui.sales.sales.sales_map_manager


import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.location.LocationManager
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.TextView
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
import com.mobbile.paul.mt3_1_1.ui.sales.sales.salesentries.SalesEntries
import com.mobiletraderv.paul.daggertraining.BaseActivity
import kotlinx.android.synthetic.main.activity_users_map.*
import javax.inject.Inject

class UsersMap : BaseActivity() {

    @Inject
    internal lateinit var modelFactory: ViewModelProvider.Factory
    lateinit var vmodel: SalesMapManagerViewModel
    lateinit var mapFragment: SupportMapFragment
    lateinit var googleMap: GoogleMap
    lateinit var locationManager: LocationManager
    private var hasGPS = false //get location using GPS
    private var hasNetwork = false //get location using Network
    lateinit var durt: TextView
    lateinit var dis: TextView
    var urno: String = ""
    var begin_lat_origin: String = ""
    var begin_lng_origin: String = ""



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_users_map)
        vmodel = ViewModelProviders.of(this, modelFactory)[SalesMapManagerViewModel::class.java]
        durt = findViewById(R.id.duration)
        dis = findViewById(R.id.kilometer)
        showProgressBar(true)
        setUpData()

        urno = intent.getStringExtra("urno")
        begin_lat_origin = intent.getStringExtra("lat")
        begin_lng_origin = intent.getStringExtra("lng")
        Log.d(TAG,urno+" "+ begin_lat_origin +" "+begin_lng_origin)

    }

    private fun setUpData(){

        textView5.setOnClickListener {
            var inten = Intent(this, SalesEntries::class.java)
            inten.putExtra("urno", urno)
            startActivity(inten)
        }

        imageView8.setOnClickListener {
            var ads: String = "$begin_lat_origin,$begin_lng_origin"
            startMapIntent(this,ads ,'w', 't')
            Log.d(TAG,"CHECK THIS")
        }

        //var origin: String = "$begin_lat_origin,$begin_lng_origin"
        //var destination: String = "6.465085, 3.715622"
        //var sensor: String = "false"
        //var mode: String = "d"
        //var key: String = getString(R.string.keys)
        //initMap(origin, destination, sensor, mode, key)
    }

    private fun initMap(origin: String, destination: String, sensor: String, mode: String, key: String) {

        mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(OnMapReadyCallback {
            googleMap = it
            vmodel.GoogleMapApi(origin, destination, sensor, mode, key).observe(this, observeMapApiResponse)
        })
    }



    val observeMapApiResponse = Observer<GoogleGetApi> { data ->

        var result = ArrayList<LatLng>()
        lateinit var startlatLng: LatLng
        lateinit var endlatLng: LatLng

        for (i in 0..(data!!.routes[0].legs[0].steps.size - 1)) {
            startlatLng = LatLng(
                data.routes[0].legs[0].steps[i].start_location.lat.toDouble(),
                data.routes[0].legs[0].steps[i].start_location.lng.toDouble()
            )
            endlatLng = LatLng(
                data.routes[0].legs[0].steps[i].end_location.lat.toDouble(),
                data.routes[0].legs[0].steps[i].end_location.lng.toDouble()
            )
            result.add(startlatLng)
            result.add(endlatLng)
        }

        var getStartLat: Double = data.routes[0].legs[0].start_location.lat
        var getStartLng: Double = data.routes[0].legs[0].start_location.lng
        var getEndLat: Double = data.routes[0].legs[0].end_location.lat
        var getEndtLng: Double = data.routes[0].legs[0].end_location.lng
        var startAddress : String = data.routes[0].legs[0].start_address
        var endAddress : String = data.routes[0].legs[0].end_address
        var distanceCovered : String = data.routes[0].legs[0].distance.text
        var duration : String = data.routes[0].legs[0].duration.text

        durt.text = duration
        dis.text = distanceCovered

        setMakerPosition(getStartLat, getStartLng, getEndLat, getEndtLng)
        setPolygonLineOptions(result)
    }

    private fun setMakerPosition(getStartLat : Double, getStartLng : Double,
                                           getEndLat : Double, getEndtLng : Double){
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
}


