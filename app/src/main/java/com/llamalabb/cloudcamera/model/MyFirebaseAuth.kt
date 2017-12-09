package com.llamalabb.cloudcamera.model

import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.llamalabb.simplefirebaselogin.utils.Utils

/**
 * Created by andy on 11/24/17.
 */
object MyFirebaseAuth {

    const val RC_SIGN_IN = 9000

    private var auth: FirebaseAuth = FirebaseAuth.getInstance()

    interface LoginCallBack {
        fun accountLoginSuccessful()
        fun accountLoginFailure(msg: String?)
        fun passwordResetSendSuccess()
        fun passwordResetSendFailure(msg: String)
    }

    interface RegisterCallBack{
        fun accountCreationSuccessful()
        fun accountCreationFailure(msg: String?)
        fun verificationEmailSendSuccess()
        fun verificationEmailSendFailure(msg: String)
    }

    interface GoogleAuthCallBack{
        fun googleAuthSuccessful()
        fun googleAuthFailure(msg: String)
    }

    fun loginUser(email: String, password: String, callBack: LoginCallBack){

        auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener{task: Task<AuthResult> ->
                    if(task.isSuccessful){
                        val user = auth.currentUser
                        user?.let{
                            if (isUserVerified(user)){
                                callBack.accountLoginSuccessful()
                            } else {
                                callBack.accountLoginFailure("Account not verified")
                                logoutCurrentUser()
                            }
                        }
                    } else {
                        callBack.accountLoginFailure(task.exception?.message)
                    }
                }

    }

    fun isUserVerified(user: FirebaseUser) : Boolean {
        //return user.isEmailVerified
        return true
    }

    fun createAccount(email: String, password: String, callBack: RegisterCallBack){
        auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener{ task: Task<AuthResult> ->
                    if (task.isSuccessful) {
                        val user = auth.currentUser
                        callBack.accountCreationSuccessful()
                        user?.let{ sendVerificationEmail(it, callBack)}
                    } else {
                        task.exception?.let{
                            callBack.accountCreationFailure(it.message)
                        } ?: callBack.accountCreationFailure("Unknown Account Creation Error")
                    }
                }
    }

    fun sendVerificationEmail(user: FirebaseUser, callBack:RegisterCallBack){
//        user.sendEmailVerification().addOnCompleteListener {
//            if(it.isSuccessful){
//                logoutCurrentUser()
//                callBack.verificationEmailSendSuccess()
//            } else {
//                it.exception?.message?.let{
//                    callBack.verificationEmailSendFailure(it)
//                } ?: callBack.verificationEmailSendFailure("Email Verification send failure")
//
//            }
//        }
    }

    fun authWithGoogle(acct: GoogleSignInAccount, callBack: GoogleAuthCallBack){
        val credential = GoogleAuthProvider.getCredential(acct.idToken, null)
        auth.signInWithCredential(credential)
                .addOnCompleteListener{task: Task<AuthResult> ->
                    if (task.isSuccessful) {
                        val user = auth.currentUser
                        callBack.googleAuthSuccessful()
                    } else {
                        callBack.googleAuthFailure("Google Auth Failed")
                    }
                }
    }

    fun logoutCurrentUser(){
        FirebaseAuth.getInstance().signOut()
    }

    fun sendPasswordReset(email: String, callBack: LoginCallBack){
        FirebaseAuth.getInstance().sendPasswordResetEmail(email)
                .addOnCompleteListener {
                    if(it.isSuccessful){
                        callBack.passwordResetSendSuccess()
                    } else {
                        it.exception?.message?.let { msg ->
                            callBack.passwordResetSendFailure(msg)
                        } ?: callBack.passwordResetSendFailure("Unknown Error")
                    }
                }
    }
}