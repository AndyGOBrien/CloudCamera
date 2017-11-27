package com.llamalabb.cloudcamera.model

import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider

/**
 * Created by andy on 11/24/17.
 */
class GoogleAuth {

    private var mAuth: FirebaseAuth = FirebaseAuth.getInstance()
    private lateinit var mCallBack: LoginCallBack


    interface LoginCallBack{
        fun accountLoginSuccessful()
        fun accountLoginFailure(msg: String?)
        fun accountCreationSuccessful()
        fun accountCreationFailure(msg: String?)
    }

    fun loginUser(email: String, password: String, callBack: LoginCallBack){
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener{task: Task<AuthResult> ->
                    if(task.isSuccessful){
                        val user = mAuth.currentUser
                        mCallBack.accountLoginSuccessful()
                    } else {
                        mCallBack.accountLoginFailure(task.exception?.message)
                    }
                }

    }

    fun authWithGoogle(acct: GoogleSignInAccount, callBack: LoginCallBack){
        val credential = GoogleAuthProvider.getCredential(acct.idToken, null)
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener{task: Task<AuthResult> ->
                    if (task.isSuccessful) {
                        val user = mAuth.currentUser
                        mCallBack.accountLoginSuccessful()
                    } else {
                        mCallBack.accountLoginFailure("Google Auth Failed")
                    }
                }
    }

    fun createAccount(email: String, password: String, callBack: LoginCallBack){
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener{ task: Task<AuthResult> ->
                    if (task.isSuccessful) {
                        val user = mAuth.currentUser
                        mCallBack.accountCreationSuccessful()
                    } else {
                        mCallBack.accountCreationFailure(task.exception?.message)
                    }
                }
    }

}