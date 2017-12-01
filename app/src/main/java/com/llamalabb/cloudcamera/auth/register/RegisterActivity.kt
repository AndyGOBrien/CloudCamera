package com.llamalabb.cloudcamera.auth.register

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.widget.CardView
import android.text.method.PasswordTransformationMethod
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
import com.llamalabb.simplefirebaselogin.utils.Utils
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
        login_frame.setOnClickListener{ showLoginView() }
        show_hide_link.setOnClickListener{ presenter.handleShowLinkClicked() }
        register_button.setOnClickListener{ registerButtonClicked() }
        google_signin_button.setOnClickListener{ startGoogleSignIn() }
    }

    override fun showLoginView(){
        startActivity(Intent(this, LoginActivity::class.java))
        finish()
    }

    override fun showFailure(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }

    override fun showSuccess() {
        Utils.showMessageShort(this, "Account Created")
    }

    override fun showVerificationEmailSent() {
        Utils.showMessageShort(this, "Verification Email Sent")
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
            ComplexityParams.Lower -> setImgComplexity(img_has_lower, isComplex)
            ComplexityParams.Upper -> setImgComplexity(img_has_upper, isComplex)
            ComplexityParams.Digit -> setImgComplexity(img_has_digit, isComplex)
            ComplexityParams.Spchr -> setImgComplexity(img_has_spchr, isComplex)
            ComplexityParams.Length -> setImgComplexity(img_has_length, isComplex)
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
        showFieldValidity(email_card, isValidEmail)
    }

    override fun showConfirmValidity(isConfirmEqual: Boolean){
        showFieldValidity(confirm_card, isConfirmEqual)
    }

    override fun showPasswordValidity(isPasswordValid: Boolean){
        showFieldValidity(password_card, isPasswordValid)
    }

    private fun showFieldValidity(cardView: CardView, isValid: Boolean){
        if(isValid)
            cardView.cardBackgroundColor = ContextCompat.getColorStateList(applicationContext,R.color.valid)
        else
            cardView.cardBackgroundColor = ContextCompat.getColorStateList(applicationContext,R.color.invalid)
    }

    private fun registerButtonClicked() {
        val email = email_editText.asString()
        val password = password_editText.asString()
        val confirm = confirm_editText.asString()

        presenter.handleRegisterButtonClicked(email, password, confirm)

    }

    private fun setOnEditTextListener(){
        password_editText.setSimpleOnTextChangedListener{ presenter.checkPasswordComplexityParams(it) }
        confirm_editText.setSimpleOnTextChangedListener { presenter.checkConfirmIsEqual(password_editText.asString(), it) }
        email_editText.setSimpleOnTextChangedListener{ presenter.checkEmailValidity(it) }
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

        if (requestCode == MyFirebaseAuth.RC_SIGN_IN) {
            val account = Auth.GoogleSignInApi.getSignInResultFromIntent(data).signInAccount
            account?.let{ account ->
                presenter.loginWithGoogle(account)
            } ?: showFailure("Unknown Error")
        }
    }
}
