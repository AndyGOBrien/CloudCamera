package com.llamalabb.cloudcamera

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.animation.AnimationUtils
import com.google.firebase.auth.FirebaseAuth
import com.llamalabb.cloudcamera.auth.login.LoginActivity
import com.llamalabb.cloudcamera.auth.username.CreateUsernameActivity
import com.llamalabb.cloudcamera.main.MainActivity
import com.llamalabb.cloudcamera.model.DataManager
import kotlinx.android.synthetic.main.activity_launcher.*

class LauncherActivity : AppCompatActivity(), DataManager.HasUsernameCallBack {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_launcher)

        startAnimation()

        FirebaseAuth.getInstance().currentUser?.let{
            DataManager.hasUsername(this)
        } ?: startLoginActivity()
    }

    private fun startAnimation(){
        val animation = AnimationUtils.loadAnimation(this, R.anim.rotate)
        loading_icon.clearAnimation()
        loading_icon.animation = animation
    }

    private fun stopAnimation(){
        loading_icon.animation?.let{
            loading_icon.clearAnimation()
        }
}

    private fun startMainActivity(){
        startActivity(Intent(this, MainActivity::class.java))
        stopAnimation()
        finish()
    }

    private fun startCreateUsernameActivity(){
        startActivity(Intent(this, CreateUsernameActivity::class.java))
        stopAnimation()
        finish()
    }

    private fun startLoginActivity(){
        startActivity(Intent(this, LoginActivity::class.java))
        stopAnimation()
        finish()
    }

    override fun hasUsernameChecked(hasUsername: Boolean) {
        if(hasUsername) startMainActivity() else startCreateUsernameActivity()
    }

}
