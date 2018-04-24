package com.coding.team.meetpin.activities


import android.graphics.Point
import android.location.Address
import android.location.Geocoder
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.DragEvent
import android.view.MotionEvent
import android.view.View
import android.widget.Toast
import com.coding.team.meetpin.R

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.activity_maps.map
import kotlinx.android.synthetic.main.activity_maps.pin
import java.util.*

class MapActivity : AppCompatActivity(), OnMapReadyCallback {


    private lateinit var mMap: GoogleMap
    var dropX : Float = 0.0f
    var dropY : Float = 0.0f
    lateinit var shadowBuilder: View.DragShadowBuilder
    lateinit var coord : LatLng
//    var geocoder = Geocoder(this, Locale.getDefault())
    lateinit var address : List<Address>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        val mapFragment = supportFragmentManager
                .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
        pin.setOnTouchListener(View.OnTouchListener(function = { view, motionEvent ->
            if (motionEvent.action == MotionEvent.ACTION_DOWN) {
                shadowBuilder = View.DragShadowBuilder(view)
                view.startDragAndDrop(null, shadowBuilder, view, 0)

                return@OnTouchListener true
            } else {

                return@OnTouchListener false
            }
        })
        )
        pin.setOnDragListener(View.OnDragListener(function = { view, dragEvent ->
            if (dragEvent.action == DragEvent.ACTION_DROP) {
                var array: IntArray = intArrayOf(0,0)
                view.getLocationOnScreen(array)
                dropX = array.get(0).toFloat()
                dropY = array.get(1).toFloat()
//
//                println(dropX.toString() +" " + dropY.toString())
                coord = mMap.getProjection().fromScreenLocation(Point(dropX.toInt(), dropY.toInt()))
//                println(coord.latitude.toString() +" " + coord.longitude.toString())
                var geocoder = Geocoder(applicationContext)
                address = geocoder.getFromLocation((coord.latitude), (coord.longitude), 1)

                Toast.makeText(applicationContext, address.get(0).getAddressLine(0), Toast.LENGTH_SHORT).show()
                return@OnDragListener false

            } else if (dragEvent.action == DragEvent.ACTION_DRAG_ENDED) {
//                println("hehe")
//                var location: IntArray= intArrayOf(0,1)


//                dropX = dragEvent.x
//                dropY = dragEvent.y
////
////                println(dropX.toString() +" " + dropY.toString())
//                coord = mMap.getProjection().fromScreenLocation(Point(dropX.toInt(), dropY.toInt()))
////                println(coord.latitude.toString() +" " + coord.longitude.toString())
//                var geocoder = Geocoder(applicationContext)
//                address = geocoder.getFromLocation((coord.latitude), (coord.longitude), 1)
//
//                Toast.makeText(applicationContext, address.get(0).getAddressLine(0), Toast.LENGTH_SHORT).show()
                return@OnDragListener false
            } else

                return@OnDragListener false
        })
        )


    }


    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        val cracow = LatLng(50.06, 19.94)
        mMap.addMarker(MarkerOptions().position(cracow).title("Marker in Cracow"))
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(cracow, 15f))
        mMap.setOnMapClickListener(GoogleMap.OnMapClickListener(function = { point ->
            var geocoder = Geocoder(applicationContext)
            address = geocoder.getFromLocation((point.latitude), (point.longitude),1)
            Toast.makeText(applicationContext, address.get(0).getAddressLine(0), Toast.LENGTH_SHORT).show()
        })
        )
    }

//    fun pointToCoord(x: Int,y: Int): LatLng{
//        var projection = mMap.getProjection()
//        return projection.fromScreenLocation(Point(x, y))
//    }

}
