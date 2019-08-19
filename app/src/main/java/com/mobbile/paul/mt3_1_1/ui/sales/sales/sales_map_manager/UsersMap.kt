package com.mobbile.paul.mt3_1_1.ui.sales.sales.sales_map_manager


import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.mobbile.paul.mt3_1_1.R
import com.mobiletraderv.paul.daggertraining.BaseActivity
import javax.inject.Inject

class UsersMap : BaseActivity() {

    @Inject
    internal lateinit var modelFactory: ViewModelProvider.Factory
    lateinit var vmodel: Sales_Map_Manager_ViewModel
    lateinit var mapFragment: SupportMapFragment
    lateinit var googleMap: GoogleMap


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_users_map)
        vmodel = ViewModelProviders.of(this, modelFactory)[Sales_Map_Manager_ViewModel::class.java]
        showProgressBar(true)
        initMap()
    }

    private fun initMap() {
        mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(OnMapReadyCallback{
            showProgressBar(false)
            googleMap = it
        })
    }

    companion object{
        var TAG = "UsersMap"
    }
}
