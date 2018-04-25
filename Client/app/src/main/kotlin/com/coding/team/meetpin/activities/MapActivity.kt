package com.coding.team.meetpin.activities


import android.graphics.Point
import android.location.Address
import android.location.Geocoder
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.Display
import android.view.DragEvent
import android.view.MotionEvent
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import com.coding.team.meetpin.R

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions


class MapActivity : AppCompatActivity(), OnMapReadyCallback, GoogleMap.OnMarkerDragListener {


    private lateinit var mMap: GoogleMap
    var maxY : Int = 0
    var maxX : Int = 0
    lateinit var coord:LatLng
    lateinit var address : List<Address>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        val mapFragment = supportFragmentManager
                .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
        var displayMetrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(displayMetrics)
        maxY= displayMetrics.heightPixels
        maxX = displayMetrics.widthPixels

    }


    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        val cracow = LatLng(50.06, 19.94)
        mMap.addMarker(MarkerOptions().position(cracow).title("Marker in Cracow"))
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(cracow, 15f))
        mMap.setOnMarkerDragListener(this)
        coord = mMap.projection.fromScreenLocation(Point(70, maxY-150))
        mMap.addMarker(MarkerOptions().position(coord).draggable(true))
    }

    override fun onMarkerDragStart(p0: Marker?) {

    }

    override fun onMarkerDrag(p0: Marker?) {

    }

    override fun onMarkerDragEnd(p0: Marker?) {
        mMap.addMarker(MarkerOptions().position(coord).draggable(true))
        var geocoder = Geocoder(applicationContext)
        address= geocoder.getFromLocation(p0!!.position.latitude,p0!!.position.longitude,1)
        Toast.makeText(applicationContext, address.get(0).getAddressLine(0), Toast.LENGTH_SHORT).show()
    }


}


