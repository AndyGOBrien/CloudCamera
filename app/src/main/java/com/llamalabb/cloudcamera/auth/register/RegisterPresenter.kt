package com.llamalabb.cloudcamera.auth.register

import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.llamalabb.cloudcamera.auth.AuthContract
import com.llamalabb.cloudcamera.auth.AuthContract.AuthView
import com.llamalabb.cloudcamera.auth.AuthPresenter
import com.llamalabb.cloudcamera.ktfiles.*
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
            when {
                ComplexityParams.Email.passes(it) -> registerView.showEmailValidity(true)
                else -> registerView.showEmailValidity(false)
            }
        }
    }

    override fun checkPasswordComplexityDynamic(pw: CharSequence){
        listOf(
                ComplexityParams.Lower,
                ComplexityParams.Upper,
                ComplexityParams.Digit,
                ComplexityParams.Spchr,
                ComplexityParams.Length
        ).forEach { registerView.showComplexityStatus(it, it.passes(pw))}
    }

    private fun isPasswordComplex(password: CharSequence) = ComplexityParams.CompletePassword.passes(password)
}