package com.coding.team.meetpin.activities

import android.graphics.Point
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.DragEvent
import android.view.MotionEvent
import android.view.View
import com.coding.team.meetpin.R

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.activity_maps.pin

class MapActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    var dropX : Float = 0.0f
    var dropY : Float = 0.0f
    lateinit var coord : LatLng
//    Geocoder geocoder = new Geocoder(mContext);
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        val mapFragment = supportFragmentManager
                .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
        pin.setOnTouchListener( View.OnTouchListener(function = { view, motionEvent ->
            if (motionEvent.action == MotionEvent.ACTION_DOWN) {
                var shadowBuilder = View.DragShadowBuilder(view)
                view.startDrag(null, shadowBuilder, view, 0)

                return@OnTouchListener true
            } else if (motionEvent.action == MotionEvent.ACTION_UP){
                dropX = motionEvent.rawX
                dropY = motionEvent.rawY
                coord = pointToCoord(dropX.toInt(), dropY.toInt())

                return@OnTouchListener false
            } else{
                    return@OnTouchListener false
                }
            })
        )

    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        val cracow = LatLng(50.06, 19.94)
        mMap.addMarker(MarkerOptions().position(cracow).title("Marker in Cracow"))
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(cracow, 15f))
    }
    fun pointToCoord(x: Int,y: Int): LatLng{
        var projection = mMap.getProjection()
        return projection.fromScreenLocation(Point(x, y))
    }

}
