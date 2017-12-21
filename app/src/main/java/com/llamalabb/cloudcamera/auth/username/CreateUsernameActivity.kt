package com.llamalabb.cloudcamera.auth.username

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.llamalabb.cloudcamera.R
import com.llamalabb.cloudcamera.ktfiles.asString
import com.llamalabb.cloudcamera.main.MainActivity
import com.llamalabb.cloudcamera.model.DataManager
import com.llamalabb.simplefirebaselogin.utils.Utils
import kotlinx.android.synthetic.main.activity_create_username.*

class CreateUsernameActivity : AppCompatActivity(), DataManager.UsernameAvailableCallBack{


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_username)

        continue_button.setOnClickListener { checkUsername() }
        continue_text.setOnClickListener { checkUsername() }
    }

    private fun checkUsername(){
        lockUI()
        val alphaDigitOnly = Regex("[a-z0-9]*")
        val un = username_editText.asString()
        val lowerUN = un.toLowerCase()
        if(lowerUN.length >= 6 && lowerUN.matches(alphaDigitOnly)){
            DataManager.isUsernameAvailable(lowerUN, this)
        } else {
            unlockUI()
            Utils.showMessageShort(this, "Invalid username, numbers and alpha characters only")
        }
    }

    override fun usernameAvailableChecked(isUsernameAvailable: Boolean) {
        if(isUsernameAvailable){
            DataManager.setUsername(username_editText.asString(), this)
        } else {
            unlockUI()
            Utils.showMessageShort(this, "Username is not available")
        }
    }

    override fun usernameSetSuccess( displayName: String ) {
        Utils.showMessageShort(this, "Logged in as " + displayName)
        startMainActivity()
    }

    private fun startMainActivity(){
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

    private fun lockUI(){
        username_editText.isEnabled = false
        continue_button.isEnabled = false
        continue_text.isEnabled = false
    }

    private fun unlockUI(){
        username_editText.isEnabled = true
        continue_button.isEnabled = true
        continue_text.isEnabled = true
    }
}
