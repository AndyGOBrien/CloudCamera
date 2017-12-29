package com.llamalabb.cloudcamera.main.gallery

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.llamalabb.cloudcamera.main.gallery.tabs.popular.PopularImagesGalleryFragment
import com.llamalabb.cloudcamera.main.gallery.tabs.user.UserImagesGalleryFragment

/**
 * Created by andy on 12/1/17.
 */
class MyPagerAdapter(fragmentManager: FragmentManager) : FragmentPagerAdapter(fragmentManager) {

    private val NUM_ITEMS = 2

    override fun getItem(position: Int): Fragment? {
        return when(position){
            0 -> UserImagesGalleryFragment.newInstance(position, "Your Images")
            1 -> PopularImagesGalleryFragment.newInstance(position, "Popular Images")
            else -> null
        }
    }

    override fun getCount(): Int {
        return NUM_ITEMS
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return when(position){
            0 -> "Your Images"
            1 -> "Popular Images"
            else -> null
        }
    }
}