package com.llamalabb.cloudcamera.main

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.FragmentPagerAdapter
import com.google.firebase.auth.FirebaseAuth
import com.llamalabb.cloudcamera.R
import com.llamalabb.cloudcamera.auth.login.LoginActivity
import kotlinx.android.synthetic.main.activity_main.*
import android.net.Uri
import android.webkit.MimeTypeMap
import android.Manifest
import android.annotation.SuppressLint
import android.util.Log
import com.llamalabb.cloudcamera.model.DataManager
import com.theartofdev.edmodo.cropper.CropImage
import com.theartofdev.edmodo.cropper.CropImageView
import permissions.dispatcher.NeedsPermission
import permissions.dispatcher.RuntimePermissions


@RuntimePermissions
class MainActivity : AppCompatActivity(), FragmentCallBackContract.FragmentHolder{

    private lateinit var adapterViewPager: FragmentPagerAdapter

    private val GET_PHOTO = 1000


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

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

    override fun logout() {
        FirebaseAuth.getInstance().signOut()
        startLoginActivity()
    }

    override fun launchGallery() {
        launchGalleryForResultWithPermissionCheck()
    }

    @NeedsPermission(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE)
    fun launchGalleryForResult(){

//        val intent = Intent()
//        intent.type = "image/*"
//        intent.action = Intent.ACTION_GET_CONTENT
//        startActivityForResult(Intent.createChooser(intent, "Select Picture"), GET_PHOTO)


        CropImage.activity()
                .setGuidelines(CropImageView.Guidelines.ON)
                .setFixAspectRatio(true)
                .setAspectRatio(9,10)
                .start(this)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        data?.let{ data ->
            if (resultCode == RESULT_OK) {
                when (requestCode) {
                    GET_PHOTO -> {
                        val uri = data.data
                        DataManager.uploadFileToStorage(getFileExtension(uri), uri)
                    }
                    CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE -> {
                        val result = CropImage.getActivityResult(data)
                        val uri = result.uri
                        Log.d("Crop Result", "Crop")
                        DataManager.uploadFileToStorage("jpg", uri)
                    }
                }
            }
        }
    }

    fun getFileExtension(uri: Uri) : String {
        val contentResolver = contentResolver
        val mimeTypeMap = MimeTypeMap.getSingleton()

        val ext = mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri))

        Log.d("get extension", ext)
        return ext
    }


    @SuppressLint("NeedOnRequestPermissionsResult")
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        onRequestPermissionsResult(requestCode, grantResults)
    }
}