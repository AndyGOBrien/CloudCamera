package com.llamalabb.cloudcamera.auth.login

import android.content.DialogInterface
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.ActivityCompat.startActivityForResult
import android.support.v7.app.AlertDialog
import android.text.method.PasswordTransformationMethod
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.llamalabb.cloudcamera.main.MainActivity

import com.llamalabb.cloudcamera.R
import com.llamalabb.cloudcamera.auth.AuthContract
import com.llamalabb.cloudcamera.auth.register.RegisterActivity

import kotlinx.android.synthetic.main.activity_login.*
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.GoogleApiClient
import com.llamalabb.cloudcamera.LauncherActivity
import com.llamalabb.cloudcamera.auth.username.CreateUsernameActivity
import com.llamalabb.cloudcamera.ktfiles.asString
import com.llamalabb.cloudcamera.ktfiles.setPasswordText
import com.llamalabb.cloudcamera.ktfiles.setRegularText
import com.llamalabb.cloudcamera.model.MyFirebaseAuth.RC_SIGN_IN


class LoginActivity : AppCompatActivity(), AuthContract.AuthView.Login {


    override var presenter: AuthContract.LoginPresenter = LoginPresenter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        setClickListeners()
        presenter.onStart()
    }

    private fun setClickListeners(){
        register_frame.setOnClickListener { showRegisterView() }
        show_hide_link.setOnClickListener { presenter.setPasswordVisibility() }
        login_button.setOnClickListener { loginButtonClicked() }
        login_text.setOnClickListener { loginButtonClicked() }
        google_signin_button.setOnClickListener { startGoogleSignIn() }
        forgot_password_frame.setOnClickListener { showForgotPasswordMessage() }
    }

    override fun showRegisterView(){
        startActivity(Intent(this, RegisterActivity::class.java))
    }

    override fun showPassword() {
        password_editText.setRegularText()
    }

    override fun hidePassword() {
        password_editText.setPasswordText()
    }

    override fun showFailure(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }

    override fun showCreateUsernameUI(){
        startActivity(Intent(this, CreateUsernameActivity::class.java))
        finish()
    }

    override fun showSuccess() {
        FirebaseAuth.getInstance().currentUser?.let{ user ->
            Toast.makeText(this, "Logged in as " + user.email, Toast.LENGTH_SHORT).show()
            startActivity(Intent(this, LauncherActivity::class.java))
            finish()
        } ?: showFailure("Unknown Login Error")
    }

    override fun loginButtonClicked() {

        val emailHasText = !email_editText.text.isNullOrEmpty()
        val passwordHasText = !password_editText.text.isNullOrEmpty()

        if(emailHasText && passwordHasText) {
            val email = email_editText.asString()
            val password = password_editText.asString()
            presenter.handleLoginButtonClicked(email, password)
        } else {
            showFailure("Please enter Username and Password")
        }
    }

    private fun showForgotPasswordMessage(){


        val builder = AlertDialog.Builder(this)
        val input = EditText(this)
        input.hint = "your email"

        val onPositiveClick = DialogInterface.OnClickListener { dialogInterface, i ->
            presenter.sendPasswordReset(input.asString())
            dialogInterface.dismiss()
        }

        val onNegativeClick = DialogInterface.OnClickListener { dialogInterface, i ->
            dialogInterface.dismiss()
        }

        builder.setTitle("Password Reset")
                .setMessage("Enter your email address")
                .setView(input)
                .setPositiveButton("Send", onPositiveClick)
                .setNegativeButton("Cancel", onNegativeClick)
                .create()
                .show()
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
