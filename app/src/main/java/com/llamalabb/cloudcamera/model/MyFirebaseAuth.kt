package com.llamalabb.cloudcamera.model

import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider

/**
 * Created by andy on 11/24/17.
 */
object MyFirebaseAuth {

    const val RC_SIGN_IN = 9000

    private var auth: FirebaseAuth = FirebaseAuth.getInstance()

    interface LoginCallBack {
        fun accountLoginSuccessful()
        fun accountLoginFailure(msg: String?)
    }

    interface RegisterCallBack{
        fun accountCreationSuccessful()
        fun accountCreationFailure(msg: String?)
    }

    fun loginUser(email: String, password: String, callBack: LoginCallBack){
        auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener{task: Task<AuthResult> ->
                    if(task.isSuccessful){
                        val user = auth.currentUser
                        callBack.accountLoginSuccessful()
                    } else {
                        callBack.accountLoginFailure(task.exception?.message)
                    }
                }

    }

    fun createAccount(email: String, password: String, callBack: RegisterCallBack){
        auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener{ task: Task<AuthResult> ->
                    if (task.isSuccessful) {
                        val user = auth.currentUser
                        callBack.accountCreationSuccessful()
                    } else {
                        task.exception?.let{
                            callBack.accountCreationFailure(it.message)
                        } ?: callBack.accountCreationFailure("Unknown Account Creation Error")
                    }
                }
    }

    fun authWithGoogle(acct: GoogleSignInAccount, callBack: LoginCallBack){
        val credential = GoogleAuthProvider.getCredential(acct.idToken, null)
        auth.signInWithCredential(credential)
                .addOnCompleteListener{task: Task<AuthResult> ->
                    if (task.isSuccessful) {
                        val user = auth.currentUser
                        callBack.accountLoginSuccessful()
                    } else {
                        callBack.accountLoginFailure("Google Auth Failed")
                    }
                }
    }
}