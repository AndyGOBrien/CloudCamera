package com.llamalabb.cloudcamera.auth.register

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.text.method.PasswordTransformationMethod
import android.view.View
import android.view.View.*
import android.widget.ImageView
import android.widget.Toast
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.GoogleApiClient
import com.llamalabb.cloudcamera.*
import com.llamalabb.cloudcamera.auth.AuthContract
import com.llamalabb.cloudcamera.auth.login.LoginActivity
import com.llamalabb.cloudcamera.ktfiles.*
import com.llamalabb.cloudcamera.model.MyFirebaseAuth
import kotlinx.android.synthetic.main.activity_register.*


class RegisterActivity : AppCompatActivity(), AuthContract.AuthView.Register {


    override var presenter: AuthContract.RegisterPresenter = RegisterPresenter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        setClickListeners()
        setOnEditTextListener()
    }

    private fun setClickListeners(){
        val onClickListener = View.OnClickListener{ view ->
            when(view) {
                login_frame -> showLoginView()
                show_hide_link -> presenter.handleShowLinkClicked()
                register_button -> registerButtonClicked()
                google_signin_button -> googleSignInButtonClicked()
            }
        }

        register_frame.setOnClickListener(onClickListener)
        show_hide_link.setOnClickListener(onClickListener)
        register_button.setOnClickListener(onClickListener)
        google_signin_button.setOnClickListener(onClickListener)

        google_signin_button.setOnClickListener {  }
    }

    override fun showLoginView(){
        startActivity(Intent(this, LoginActivity::class.java))
    }

    override fun showFailure(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
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

    override fun showComplexityStatus(complexityParam: ComplexityParams, isComplex: Boolean){
        when(complexityParam){
            is ComplexityParams.Lower -> setImgComplexity(img_has_lower, isComplex)
            is ComplexityParams.Upper -> setImgComplexity(img_has_upper, isComplex)
            is ComplexityParams.Digit -> setImgComplexity(img_has_digit, isComplex)
            is ComplexityParams.Spchr -> setImgComplexity(img_has_spchr, isComplex)
            is ComplexityParams.Length -> setImgComplexity(img_has_length, isComplex)
        }
    }

    private fun setImgComplexity(img: ImageView, isComplex: Boolean){
        when(isComplex){
            true -> img.setImageDrawable(ContextCompat
                    .getDrawable(applicationContext,R.drawable.green_check_mark))
            else -> img.setImageDrawable(ContextCompat
                    .getDrawable(applicationContext,R.drawable.red_x))
        }

    }

    override fun showEmailValidity(isValidEmail: Boolean){
        if(isValidEmail)
            email_card.cardBackgroundColor = ContextCompat.getColorStateList(applicationContext,R.color.valid)
        else
            email_card.cardBackgroundColor = ContextCompat.getColorStateList(applicationContext,R.color.invalid)
    }

    override fun googleSignInButtonClicked() {
        startGoogleSignIn()
    }

    private fun registerButtonClicked() {
        val email = email_editText.text.toString()
        val password = password_editText.text.toString()
        val confirm = confirm_editText.text.toString()

        presenter.handleRegisterButtonClicked(email, password, confirm)

    }

    private fun setOnEditTextListener(){
        password_editText.setSimpleOnTextChangedListener { password ->
            password?.let(presenter::checkPasswordComplexityDynamic)
        }

        email_editText.setSimpleOnTextChangedListener { email ->
            presenter.checkEmailValidity(email)
        }

        password_editText.onFocusChangeListener = OnFocusChangeListener { _, hasFocus ->
            pw_complexity_frame.apply{ visibility = if (hasFocus) VISIBLE else INVISIBLE }
        }
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
        startActivityForResult(signInIntent, MyFirebaseAuth.RC_SIGN_IN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        super.onActivityResult(requestCode, resultCode, data)

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == MyFirebaseAuth.RC_SIGN_IN) {
            val account = Auth.GoogleSignInApi.getSignInResultFromIntent(data).signInAccount
            account?.let{ account ->
                presenter.loginWithGoogle(account)
            } ?: showFailure("Unknown Error")
        }
    }
}
