//package com.coding.team.meetpin.activities
//
//import android.support.v4.app.Fragment
//import android.support.v4.app.FragmentManager
//import android.support.v4.app.FragmentPagerAdapter
//import com.google.android.gms.maps.MapFragment
//
//open class MapPagerAdapter(fragmentManager: FragmentManager) :
//        FragmentPagerAdapter(fragmentManager) {
//
//    private val NUM_ITEMS = 3
//
//    override public fun getCount(): Int {
//        return NUM_ITEMS
//    }
//
//    override fun getItem(position: Int): Fragment? {
//        when (position) {
//            0 -> MapFragment.newInstance()
//            1 -> MapFragment.newInstance()
//            2 -> MapFragment.newInstance()
//            else -> {
//                return null
//            }
//        }
//        return null
//    }
//}