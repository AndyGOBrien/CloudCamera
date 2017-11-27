package com.llamalabb.cloudcamera.auth.register

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.method.PasswordTransformationMethod
import android.view.View
import com.llamalabb.cloudcamera.R
import com.llamalabb.cloudcamera.auth.AuthContract
import com.llamalabb.cloudcamera.auth.login.LoginActivity
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : AppCompatActivity(), AuthContract.AuthView.Register {


    override var presenter: AuthContract.RegisterPresenter = RegisterPresenter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        setClickListeners()
    }

    private fun setClickListeners(){
        val onClickListener = View.OnClickListener{ view ->
            when(view) {
                login_frame -> showLoginView()
                show_hide_link -> presenter.handleShowLinkClicked()
                register_button -> registerButtonClicked()
            }
        }

        register_frame.setOnClickListener(onClickListener)
        show_hide_link.setOnClickListener(onClickListener)
        register_button.setOnClickListener(onClickListener)
    }

    override fun showLoginView(){
        startActivity(Intent(this, LoginActivity::class.java))
    }

    override fun showFailure(msg: String) {

    }

    override fun showSuccess() {

    }

    override fun showHidePasswordText() {
        if(show_hide_link.text == "show") {
            show_hide_link.text = "hide"
            password_editText.transformationMethod = null
            confirm_editText.transformationMethod = null
        } else {
            show_hide_link.text = "show"
            password_editText.transformationMethod = PasswordTransformationMethod()
            confirm_editText.transformationMethod = PasswordTransformationMethod()
        }
    }

    override fun googleSignInButtonClicked() {

    }

    override fun registerButtonClicked() {
        val email = email_editText.text.toString()
        val password = password_editText.text.toString()
        val confirm = confirm_editText.text.toString()

        presenter.handleRegisterButtonClicked(email, password, confirm)

    }
}
