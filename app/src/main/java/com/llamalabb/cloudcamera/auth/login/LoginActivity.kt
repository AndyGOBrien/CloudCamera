package com.llamalabb.cloudcamera.auth.login

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.method.PasswordTransformationMethod
import android.view.View
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.llamalabb.cloudcamera.MainActivity

import com.llamalabb.cloudcamera.R
import com.llamalabb.cloudcamera.auth.AuthContract
import com.llamalabb.cloudcamera.auth.register.RegisterActivity

import kotlinx.android.synthetic.main.activity_login.*
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.GoogleApiClient
import com.llamalabb.cloudcamera.model.MyFirebaseAuth.RC_SIGN_IN


class LoginActivity : AppCompatActivity(), AuthContract.AuthView.Login {
    override var presenter: AuthContract.LoginPresenter = LoginPresenter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        setClickListeners()
    }

    private fun setClickListeners(){
        val onClickListener = View.OnClickListener{ view ->
            when(view) {
                register_frame -> showRegisterView()
                show_hide_link -> presenter.handleShowLinkClicked()
                login_button -> loginButtonClicked()
                google_signin_button -> startGoogleSignIn()
            }
        }

        register_frame.setOnClickListener(onClickListener)
        show_hide_link.setOnClickListener(onClickListener)
        login_button.setOnClickListener(onClickListener)
        google_signin_button.setOnClickListener(onClickListener)
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
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }

    override fun showSuccess() {
        FirebaseAuth.getInstance().currentUser?.let{ user ->
            Toast.makeText(this, "Logged in as " + user.email, Toast.LENGTH_SHORT).show()
            startActivity(Intent(this, MainActivity::class.java))
        } ?: showFailure("Unknown Login Error")
    }

    override fun loginButtonClicked() {
        val email = email_editText.text.toString()
        val password = email_editText.text.toString()
        presenter.handleLoginButtonClicked(email, password)
    }

    private fun startGoogleSignIn() {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.web_clint_id))
                .requestEmail()
                .build()

        val googleApiClient = GoogleApiClient.Builder(this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .addOnConnectionFailedListener {
                    Toast.makeText(this, it.errorMessage, Toast.LENGTH_SHORT).show()
                }.build()

        val signInIntent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient)
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        super.onActivityResult(requestCode, resultCode, data)

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            val account = Auth.GoogleSignInApi.getSignInResultFromIntent(data).signInAccount
            account?.let{ account ->
                presenter.loginWithGoogle(account)
            } ?: showFailure("Unknown Error")
        }
    }

}
