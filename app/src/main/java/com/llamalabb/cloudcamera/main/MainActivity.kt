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
import android.support.v4.app.Fragment
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import com.llamalabb.cloudcamera.main.gallery.GalleryFragment
import com.llamalabb.cloudcamera.main.home.HomeFragment
import com.llamalabb.cloudcamera.main.photo.AddPhotoFragment
import com.llamalabb.cloudcamera.main.profile.ProfileFragment
import com.llamalabb.cloudcamera.model.DataManager
import com.theartofdev.edmodo.cropper.CropImage
import com.theartofdev.edmodo.cropper.CropImageView
import permissions.dispatcher.NeedsPermission
import permissions.dispatcher.RuntimePermissions


@RuntimePermissions
class MainActivity : AppCompatActivity(), FragmentCallBackContract.FragmentHolder{

    private val TAG = "MainActivity"

    private lateinit var adapterViewPager: FragmentPagerAdapter

    private val GET_PHOTO = 1000


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bnve.enableAnimation(false)
        bnve.enableShiftingMode(false)
        bnve.enableItemShiftingMode(false)
        bnve.setTextVisibility(false)
        bnve.setOnNavigationItemSelectedListener {
            loadFragment(it.itemId)
            true
        }
    }

    private fun loadFragment(id: Int){
        var fragment: Fragment = when(id){
            R.id.menu_home -> HomeFragment.newInstance(0,"Home")
            R.id.menu_gallery -> GalleryFragment.newInstance(1, "Gallery")
            R.id.menu_add_photo -> AddPhotoFragment.newInstance(2, "Add Photo")
            R.id.menu_profile -> ProfileFragment.newInstance(3, "Profile")
            else -> return
        }
        supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .commit()
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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.logout_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.logout_menu_item -> logout()
            else -> return super.onOptionsItemSelected(item)
        }
        return true
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
        if (resultCode != RESULT_OK && data == null) {
            Log.d(TAG, resultCode.toString())
            return
        }
        when (requestCode) {
            GET_PHOTO -> getPhotoActivityResult(data!!)
            CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE -> cropImageActivityResult(data!!)
        }
    }


    private fun cropImageActivityResult(data: Intent){
        val result = CropImage.getActivityResult(data)
        val uri = result.uri
        Log.d(TAG, "Crop")
        DataManager.uploadFileToStorage("jpg", uri)
    }

    private fun getPhotoActivityResult(data: Intent){
        val uri = data.data
        DataManager.uploadFileToStorage(getFileExtension(uri), uri)
    }

    private fun getFileExtension(uri: Uri) : String {
        val contentResolver = contentResolver
        val mimeTypeMap = MimeTypeMap.getSingleton()
        val ext = mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri))
        Log.d(TAG, "extention: " + ext)
        return ext
    }


    @SuppressLint("NeedOnRequestPermissionsResult")
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        onRequestPermissionsResult(requestCode, grantResults)
    }
}