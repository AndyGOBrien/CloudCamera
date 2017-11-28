package com.llamalabb.cloudcamera.auth.register

import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.llamalabb.cloudcamera.auth.AuthContract
import com.llamalabb.cloudcamera.auth.AuthContract.AuthView
import com.llamalabb.cloudcamera.auth.AuthPresenter
import com.llamalabb.cloudcamera.model.MyFirebaseAuth
import com.llamalabb.cloudcamera.utils.*


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

        if(!isPasswordComplex(password)){
            registerView.showFailure("Password does not meet complexity requirements")
        } else if(password != confirm) {
            registerView.showFailure("Passwords do not match")
        }

    }

    private fun registerUser(email: String, password: String) {

    }

    override fun accountCreationSuccessful() {

    }

    override fun accountCreationFailure(msg: String?) {

    }

    override fun checkEmailValidity(email: CharSequence?) {
        email?.let{
            with(registerView) {
                if (it.contains(emailValidityParam)) {
                    showEmailValidity(true)
                } else {
                    showEmailValidity(false)
                }
            }
        }
    }

    override fun checkPasswordComplexityDynamic(pw: CharSequence?){
        val complexityParams = arrayOf(lower, upper, digit, spchr)

        pw?.let {
            for (param in 0 until complexityParams.size) {
                val hasCharComplexity = it.contains(complexityParams[param])
                registerView.showComplexityStatus(param, hasCharComplexity)
            }
            val lengthComplexity = it.length in minPassLength..maxPassLength
            registerView.showComplexityStatus(-1, lengthComplexity)
        }
    }

    private fun isPasswordComplex(password: CharSequence) : Boolean{

        return password.contains(passComplexity) && password.length <= maxPassLength
    }

}