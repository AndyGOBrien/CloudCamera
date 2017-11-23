package com.llamalabb.cloudcamera.auth.login

import com.llamalabb.cloudcamera.auth.AuthContract
import com.llamalabb.cloudcamera.auth.AuthContract.AuthView
import com.llamalabb.cloudcamera.auth.AuthPresenter

/**
 * Created by andy on 11/23/17.
 */
class LoginPresenter(loginView: AuthView.Login) :
        AuthContract.LoginPresenter,
        AuthPresenter(loginView) {

    override fun loginUser(email: String, password: String) {

    }

    override fun showRegisterView() {

    }
}