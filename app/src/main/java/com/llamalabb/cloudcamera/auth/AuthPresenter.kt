package com.llamalabb.cloudcamera.auth

/**
 * Created by andy on 11/23/17.
 */
abstract class AuthPresenter(val authView: AuthContract.AuthView) : AuthContract.Presenter {
    override fun onStart() {

    }

    override fun showHideText() {
        authView.showHidePasswordText()
    }

}