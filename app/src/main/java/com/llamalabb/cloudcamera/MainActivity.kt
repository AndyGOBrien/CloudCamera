package com.llamalabb.cloudcamera

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.auth.FirebaseAuth
import com.llamalabb.cloudcamera.auth.login.LoginActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        FirebaseAuth.getInstance().currentUser?.let{

        } ?: startLoginActivity()
    }

    fun startLoginActivity(){
        startActivity(Intent(this, LoginActivity::class.java))
        finish()
    }
}
