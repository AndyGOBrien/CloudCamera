package com.llamalabb.cloudcamera.auth.register

import com.llamalabb.cloudcamera.auth.AuthContract
import com.llamalabb.cloudcamera.auth.AuthContract.AuthView
import com.llamalabb.cloudcamera.auth.AuthPresenter

/**
 * Created by andy on 11/23/17.
 */
class RegisterPresenter(val registerView: AuthView.Register) :
        AuthContract.RegisterPresenter,
        AuthPresenter(registerView){

    override fun registerUser(email: String, password: String, confirm: String) {

    }

}