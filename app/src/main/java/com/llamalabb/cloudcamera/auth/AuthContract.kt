package com.llamalabb.cloudcamera.auth

import com.llamalabb.simplefirebaselogin.BasePresenter
import com.llamalabb.simplefirebaselogin.BaseView

/**
 * Created by andy on 11/22/17.
 */
interface AuthContract {

    interface AuthView: BaseView<Presenter> {

        fun showFailure(msg: String)
        fun showSuccess()
        fun showLinkClicked()
        fun googleSignInButtonClicked()

        interface Login : AuthView{
            fun loginButtonClicked()
            fun showRegisterView()
        }

        interface Register : AuthView{

        }
    }

    interface Presenter : BasePresenter, LoginPresenter, RegisterPresenter {
        fun showHideText()
    }

    interface LoginPresenter {
        fun loginUser(email: String, password: String)
        fun showRegisterView()

    }

    interface RegisterPresenter {
        fun registerUser(email: String, password: String, confirm: String)
    }

}