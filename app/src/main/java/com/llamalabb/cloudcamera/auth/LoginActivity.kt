package com.llamalabb.cloudcamera.auth

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.llamalabb.cloudcamera.R
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        setClickListeners()

    }

    fun startRegisterActivity(){
        startActivity(Intent(this, RegisterActivity::class.java))
    }

    fun setClickListeners(){
        val onClickListener = View.OnClickListener{ v ->
            when(v) {
                register_frame -> startRegisterActivity()
            }
        }

        register_frame.setOnClickListener(onClickListener)
    }
}
