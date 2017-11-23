package com.llamalabb.cloudcamera.auth

import com.llamalabb.simplefirebaselogin.BasePresenter
import com.llamalabb.simplefirebaselogin.BaseView

/**
 * Created by andy on 11/22/17.
 */
interface AuthContract {

    interface AuthView {

        fun showFailure(msg: String)
        fun showSuccess()
        fun showLinkClicked()
        fun googleSignInButtonClicked()

        interface Login: AuthView, BaseView<LoginPresenter>{
            fun loginButtonClicked()
            fun showRegisterView()
        }

        interface Register: AuthView, BaseView<RegisterPresenter>{
            fun registerButtonClicked()
        }
    }



    interface Presenter : BasePresenter {
        fun showHideText()
    }

    interface LoginPresenter : Presenter {
        fun loginUser(email: String, password: String)
        fun showRegisterView()

    }

    interface RegisterPresenter : Presenter {
        fun registerUser(email: String, password: String, confirm: String)
    }

}