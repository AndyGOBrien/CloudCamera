package com.llamalabb.cloudcamera.auth

import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.llamalabb.cloudcamera.ktfiles.ComplexityParams
import com.llamalabb.simplefirebaselogin.BasePresenter
import com.llamalabb.simplefirebaselogin.BaseView

/**
 * Created by andy on 11/22/17.
 */
interface AuthContract {

    interface AuthView {

        fun showFailure(msg: String)
        fun showSuccess()
        fun showPassword()
        fun hidePassword()

        interface Login: AuthView, BaseView<LoginPresenter>{
            fun loginButtonClicked()
            fun showRegisterView()
            fun showCreateUsernameUI()
        }

        interface Register: AuthView, BaseView<RegisterPresenter>{
            fun showComplexityStatus(complexityParam: ComplexityParams, isComplex: Boolean)
            fun showEmailValidity(isValidEmail: Boolean, isEmpty: Boolean)
            fun showConfirmValidity(isConfirmEqual: Boolean, isEmpty: Boolean)
            fun showPasswordValidity(isPasswordValid: Boolean, isEmpty: Boolean)
            fun showVerificationEmailSent()
            fun hidePasswordComplexityIndicators()
            fun showPasswordComplexityIndicators()
        }
    }

    interface Presenter : BasePresenter {
        fun setPasswordVisibility()
        fun loginWithGoogle(account: GoogleSignInAccount)
    }

    interface LoginPresenter : Presenter {
        fun handleLoginButtonClicked(email: String, password: String)
        fun showRegisterView()
        fun sendPasswordReset(email: String)
    }

    interface RegisterPresenter : Presenter {
        fun handleRegisterButtonClicked(email: String, password: String, confirm: String)
        fun checkPasswordComplexityParams(pw: CharSequence)
        fun checkEmailValidity(email: CharSequence)
        fun checkConfirmIsEqual(password: CharSequence, confirm: CharSequence)
        fun setPasswordComplexityIndicators(show: Boolean)
    }

}