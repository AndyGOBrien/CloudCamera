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
import com.llamalabb.simplefirebaselogin.utils.Utils
import kotlinx.android.synthetic.main.activity_register.*


class RegisterActivity : AppCompatActivity(), AuthContract.AuthView.Register {

    override var presenter: AuthContract.RegisterPresenter = RegisterPresenter(this)

    private lateinit var complexityIndicators: List<View>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        complexityIndicators = listOf(img_has_length, img_has_digit, img_has_lower, img_has_upper, img_has_spchr)
        setClickListeners()
        setOnEditTextListener()
        onStart()
    }

    private fun setClickListeners(){
        show_hide_password.setOnClickListener { presenter.setPasswordVisibility() }
        show_hide_confirm.setOnClickListener { presenter.setPasswordVisibility() }
        register_button.setOnClickListener { registerButtonClicked() }
        register_text.setOnClickListener { registerButtonClicked() }
        google_signin_button.setOnClickListener { startGoogleSignIn() }
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

    override fun showPassword() {
        password_editText.transformationMethod = null
        confirm_editText.transformationMethod = null
    }

    override fun hidePassword() {
        password_editText.transformationMethod = PasswordTransformationMethod()
        confirm_editText.transformationMethod = PasswordTransformationMethod()
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
                    .getDrawable(applicationContext,R.drawable.ic_action_valid))
            else -> img.setImageDrawable(ContextCompat
                    .getDrawable(applicationContext,R.drawable.ic_action_invalid))
        }

    }

    override fun showEmailValidity(isValidEmail: Boolean){
        if(email_editText.text.isNullOrEmpty())
            showFieldValidity(email_card, isValidEmail)
    }

    override fun showConfirmValidity(isConfirmEqual: Boolean){
        if(!confirm_editText.text.isNullOrEmpty())
            showFieldValidity(confirm_card, isConfirmEqual)
    }

    override fun showPasswordValidity(isPasswordValid: Boolean){
        if(!password_editText.text.isNullOrEmpty())
            showFieldValidity(password_card, isPasswordValid)
    }

    private fun showFieldValidity(view: View, isValid: Boolean){
        if(isValid)
            view.background = ContextCompat.getDrawable(this, R.drawable.rounded_edit_text_valid)
        else
            view.background = ContextCompat.getDrawable(this, R.drawable.rounded_edit_text_invalid)
    }

    private fun registerButtonClicked() {
        val email = email_editText.asString()
        val password = password_editText.asString()
        val confirm = confirm_editText.asString()

        presenter.handleRegisterButtonClicked(email, password, confirm)

    }

    private fun setOnEditTextListener(){
        password_editText.setSimpleOnTextChangedListener{
            presenter.checkPasswordComplexityParams(it)
            presenter.checkConfirmIsEqual( confirm_editText.asString(), it )
        }
        confirm_editText.setSimpleOnTextChangedListener {
            presenter.checkConfirmIsEqual(password_editText.asString(), it)
        }
        email_editText.setSimpleOnTextChangedListener{
            presenter.checkEmailValidity(it)
        }
        password_editText.onFocusChangeListener = OnFocusChangeListener { _, hasFocus ->
            presenter.setPasswordComplexityIndicators(hasFocus)
        }
    }

    override fun hidePasswordComplexityIndicators() {
        complexityIndicators.forEach { it.visibility = View.INVISIBLE }
    }

    override fun showPasswordComplexityIndicators() {
        complexityIndicators.forEach { it.visibility = View.VISIBLE }
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