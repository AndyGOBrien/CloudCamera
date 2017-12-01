package com.llamalabb.cloudcamera.main

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.FragmentPagerAdapter
import com.google.firebase.auth.FirebaseAuth
import com.llamalabb.cloudcamera.R
import com.llamalabb.cloudcamera.auth.login.LoginActivity
import kotlinx.android.synthetic.main.activity_main.*
import android.provider.MediaStore
import android.app.Activity
import android.net.Uri
import android.webkit.MimeTypeMap
import com.llamalabb.cloudcamera.model.DataManager
import java.io.IOException


class MainActivity : AppCompatActivity(), FragmentCallBackContract.FragmentHolder {

    private lateinit var adapterViewPager: FragmentPagerAdapter

    private val GET_PHOTO = 1000


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        FirebaseAuth.getInstance().currentUser?.let{ user ->
        } ?: startLoginActivity()

        bnve.enableAnimation(false)
        bnve.enableShiftingMode(false)
        bnve.enableItemShiftingMode(false)
        bnve.setTextVisibility(false)

        adapterViewPager = MyPagerAdapter(supportFragmentManager)
        view_pager.adapter = adapterViewPager

    }

    private fun startLoginActivity(){
        startActivity(Intent(this, LoginActivity::class.java))
        finish()
    }

    override fun launchGallery() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), GET_PHOTO)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == GET_PHOTO && resultCode == Activity.RESULT_OK && data != null && data.data != null) {

            val uri = data.data

            DataManager.uploadFileToStorage(getFileExtension(uri), uri)

            try {
                val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, uri)

            } catch (e: IOException) {
                e.printStackTrace()
            }

        }
    }

    fun getFileExtension(uri: Uri) : String {
        val contentResolver = contentResolver
        val mimeTypeMap = MimeTypeMap.getSingleton()
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri))
    }
}