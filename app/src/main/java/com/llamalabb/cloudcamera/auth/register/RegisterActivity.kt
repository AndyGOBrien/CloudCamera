package com.llamalabb.cloudcamera.auth.register

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.llamalabb.cloudcamera.R
import com.llamalabb.cloudcamera.auth.AuthContract

class RegisterActivity : AppCompatActivity(), AuthContract.AuthView.Register {


    override var presenter: AuthContract.RegisterPresenter = RegisterPresenter(this)

    override fun showFailure(msg: String) {

    }

    override fun showSuccess() {

    }

    override fun showLinkClicked() {

    }

    override fun googleSignInButtonClicked() {

    }

    override fun registerButtonClicked() {

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
    }
}
