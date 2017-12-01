package com.llamalabb.cloudcamera

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.auth.FirebaseAuth
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx
import com.llamalabb.cloudcamera.auth.login.LoginActivity
import com.llamalabb.cloudcamera.model.MyFirebaseAuth

import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bnve.enableAnimation(false)
        bnve.enableShiftingMode(false)
        bnve.enableItemShiftingMode(false)
        bnve.setTextVisibility(false)

        FirebaseAuth.getInstance().currentUser?.let{ user ->

        } ?: startLoginActivity()

//        logout_button.setOnClickListener {
//            MyFirebaseAuth.logoutCurrentUser()
//            startLoginActivity()
//        }
    }

    private fun startLoginActivity(){
        startActivity(Intent(this, LoginActivity::class.java))
        finish()
    }
}