package com.llamalabb.cloudcamera.auth.login

import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.llamalabb.cloudcamera.auth.AuthContract
import com.llamalabb.cloudcamera.auth.AuthContract.AuthView
import com.llamalabb.cloudcamera.auth.AuthPresenter
import com.llamalabb.cloudcamera.model.MyFirebaseAuth

/**
 * Created by andy on 11/23/17.
 */
class LoginPresenter(private val loginView: AuthView.Login) :
        AuthContract.LoginPresenter,
        AuthPresenter(loginView),
        MyFirebaseAuth.LoginCallBack{

    override fun accountLoginSuccessful() {
        loginView.showSuccess()
    }

    override fun accountLoginFailure(msg: String?) {
        msg?.let{ loginView.showFailure(it)
            } ?: loginView.showFailure("Unknown Login Error")
    }

    override fun loginWithGoogle(account: GoogleSignInAccount) {
        MyFirebaseAuth.authWithGoogle(account, this)
    }

    override fun showRegisterView() {
        loginView.showRegisterView()
    }

    override fun handleLoginButtonClicked(email: String, password: String) {
        loginUser(email, password)
    }

    override fun sendPasswordReset(email: String){
        MyFirebaseAuth.sendPasswordReset(email, this)
    }

    override fun passwordResetSendFailure(msg: String) {
        loginView.showFailure(msg)
    }

    override fun passwordResetSendSuccess() {

    }

    private fun loginUser(email: String, password: String) {
        MyFirebaseAuth.loginUser(email, password, this)
    }

}