package com.llamalabb.cloudcamera.auth.register

import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.firebase.auth.FirebaseAuth
import com.llamalabb.cloudcamera.auth.AuthContract
import com.llamalabb.cloudcamera.auth.AuthContract.AuthView
import com.llamalabb.cloudcamera.auth.AuthPresenter
import com.llamalabb.cloudcamera.model.MyFirebaseAuth

/**
 * Created by andy on 11/23/17.
 */
class RegisterPresenter(private val registerView: AuthView.Register) :
        AuthContract.RegisterPresenter,
        AuthPresenter(registerView),
        MyFirebaseAuth.RegisterCallBack{
    override fun loginWithGoogle(account: GoogleSignInAccount) {

    }

    override fun handleRegisterButtonClicked(email: String, password: String, confirm: String) {

    }

    private fun registerUser(email: String, password: String) {

    }

    override fun accountCreationSuccessful() {

    }

    override fun accountCreationFailure(msg: String?) {

    }

}