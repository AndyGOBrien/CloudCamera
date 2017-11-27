package com.llamalabb.cloudcamera.auth.login

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.method.PasswordTransformationMethod
import android.view.View

import com.llamalabb.cloudcamera.R
import com.llamalabb.cloudcamera.auth.AuthContract
import com.llamalabb.cloudcamera.auth.register.RegisterActivity

import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity(), AuthContract.AuthView.Login {
    override var presenter: AuthContract.LoginPresenter = LoginPresenter(this)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        setClickListeners()

    }

    private fun setClickListeners(){
        val onClickListener = View.OnClickListener{ v ->
            when(v) {
                register_frame -> presenter.showRegisterView()

                show_hide_link -> presenter.showHideText()
            }
        }

        register_frame.setOnClickListener(onClickListener)
        show_hide_link.setOnClickListener(onClickListener)
    }

    override fun showRegisterView(){
        startActivity(Intent(this, RegisterActivity::class.java))
    }

    override fun showHidePasswordText(){
        if(show_hide_link.text == "show") {
            show_hide_link.text = "hide"
            password_editText.transformationMethod = null
        } else {
            show_hide_link.text = "show"
            password_editText.transformationMethod = PasswordTransformationMethod()
        }
    }

    override fun showFailure(msg: String) {
    }

    override fun showSuccess() {

    }

    override fun googleSignInButtonClicked() {

    }

    override fun loginButtonClicked() {

    }


}
