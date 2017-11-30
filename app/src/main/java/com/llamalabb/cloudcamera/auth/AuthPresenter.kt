package com.llamalabb.cloudcamera.auth

import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.llamalabb.cloudcamera.model.MyFirebaseAuth

/**
 * Created by andy on 11/23/17.
 */
abstract class AuthPresenter(private val authView: AuthContract.AuthView)
    : AuthContract.Presenter, MyFirebaseAuth.GoogleAuthCallBack{

    override fun onStart() {}
    override fun showHideText() { authView.showHidePasswordText() }
    override fun handleShowLinkClicked() { authView.showHidePasswordText() }
    override fun googleAuthFailure(msg: String) { authView.showFailure(msg) }
    override fun googleAuthSuccessful() { authView.showSuccess() }
    override fun loginWithGoogle(account: GoogleSignInAccount) {
        MyFirebaseAuth.authWithGoogle(account, this)
    }

}