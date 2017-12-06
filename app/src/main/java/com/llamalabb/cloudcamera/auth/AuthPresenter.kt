package com.llamalabb.cloudcamera.auth

import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.llamalabb.cloudcamera.model.MyFirebaseAuth

/**
 * Created by andy on 11/23/17.
 */
abstract class AuthPresenter(private val authView: AuthContract.AuthView)
    : AuthContract.Presenter, MyFirebaseAuth.GoogleAuthCallBack{

    private var showPassword: Boolean = false

    override fun onStart() {
        showPassword = false
        setPasswordVisibility()
    }
    override fun setPasswordVisibility() {
        if(showPassword) {
            authView.showPassword()
            showPassword = false
        }
        else{
            authView.hidePassword()
            showPassword = true
        }
    }

    override fun googleAuthFailure(msg: String) { authView.showFailure(msg) }
    override fun googleAuthSuccessful() { authView.showSuccess() }
    override fun loginWithGoogle(account: GoogleSignInAccount) {
        MyFirebaseAuth.authWithGoogle(account, this)
    }

}