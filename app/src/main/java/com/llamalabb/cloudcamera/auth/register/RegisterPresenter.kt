package com.llamalabb.cloudcamera.auth.register

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

    override fun handleRegisterButtonClicked(email: String, password: String, confirm: String) {

        if(!isEmailValid(email)){
            registerView.showFailure("Please enter a valid email address")
        } else if(!isPasswordComplex(password)) {
            registerView.showFailure("Password does not meet complexity requirements")
        } else if(password != confirm) {
            registerView.showFailure("Passwords do not match")
        } else {
            registerUser(email, password)
        }
    }

    private fun registerUser(email: String, password: String) {
        MyFirebaseAuth.createAccount(email, password, this)
    }

    private fun isEmailValid(email: CharSequence) = ComplexityParams.Email.passes(email)

    override fun accountCreationSuccessful() {
        registerView.showSuccess()
    }

    override fun accountCreationFailure(msg: String?) {
        registerView.showFailure("Account Creation failure")
    }

    override fun checkEmailValidity(email: CharSequence){
        registerView.showEmailValidity(isEmailValid(email))
    }

    override fun checkPasswordComplexityParams(pw: CharSequence){
        listOf(ComplexityParams.Lower,
                ComplexityParams.Upper,
                ComplexityParams.Digit,
                ComplexityParams.Spchr,
                ComplexityParams.Length
        ).forEach { registerView.showComplexityStatus(it, it.passes(pw)) }
    }

    private fun isPasswordComplex(password: CharSequence) = ComplexityParams.CompletePassword.passes(password)
}