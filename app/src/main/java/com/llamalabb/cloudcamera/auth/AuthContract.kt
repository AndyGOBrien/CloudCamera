package com.llamalabb.cloudcamera.auth

import android.content.Context
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.llamalabb.simplefirebaselogin.BasePresenter
import com.llamalabb.simplefirebaselogin.BaseView

/**
 * Created by andy on 11/22/17.
 */
interface AuthContract {

    interface AuthView {

        fun showFailure(msg: String)
        fun showSuccess()
        fun showHidePasswordText()
        fun googleSignInButtonClicked()

        interface Login: AuthView, BaseView<LoginPresenter>{
            fun loginButtonClicked()
            fun showRegisterView()
        }

        interface Register: AuthView, BaseView<RegisterPresenter>{
            fun registerButtonClicked()
            fun showLoginView()
        }
    }



    interface Presenter : BasePresenter {
        fun showHideText()
        fun handleShowLinkClicked()
        fun loginWithGoogle(account: GoogleSignInAccount)
    }

    interface LoginPresenter : Presenter {
        fun handleLoginButtonClicked(email: String, password: String)
        fun showRegisterView()

    }

    interface RegisterPresenter : Presenter {
        fun handleRegisterButtonClicked(email: String, password: String, confirm: String)
    }

}