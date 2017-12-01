package com.llamalabb.cloudcamera.main

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.llamalabb.cloudcamera.main.gallery.GalleryFragment
import com.llamalabb.cloudcamera.main.home.HomeFragment
import com.llamalabb.cloudcamera.main.photo.AddPhotoFragment
import com.llamalabb.cloudcamera.main.profile.ProfileFragment

/**
 * Created by andy on 12/1/17.
 */
class MyPagerAdapter(fragmentManager: FragmentManager) : FragmentPagerAdapter(fragmentManager) {

    private val NUM_ITEMS = 4

    override fun getItem(position: Int): Fragment? {
        return when(position){
            0 -> HomeFragment.newInstance(position, "Home Page")
            1 -> GalleryFragment.newInstance(position, "Your Gallery")
            2 -> AddPhotoFragment.newInstance(position, "Add Photo")
            3 -> ProfileFragment.newInstance(position, "Your Profile")
            else -> null
        }
    }

    override fun getCount(): Int {
        return NUM_ITEMS
    }
}