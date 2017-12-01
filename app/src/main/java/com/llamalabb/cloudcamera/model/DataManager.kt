package com.llamalabb.cloudcamera.model

import android.net.Uri
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage

/**
 * Created by andy on 12/1/17.
 */
object DataManager {
    val user = FirebaseAuth.getInstance().currentUser
    val db = FirebaseDatabase.getInstance()
    val dbRef = db.reference
    val dbUserImagesRef = dbRef.child("users").child(user?.uid).child("images")

    val storage = FirebaseStorage.getInstance()
    val storageRef = storage.reference




    fun uploadFileToStorage(fileEx: String, uri: Uri){
        val imgRef = storageRef.child("users/" + user?.uid + "/" + "images/" + System.currentTimeMillis() + "." + fileEx)
        imgRef.putFile(uri).addOnSuccessListener {
            val image = MyImage(it.downloadUrl.toString())
            saveImageUrl(image)
        }
    }

    fun saveImageUrl(image: MyImage){
        val imageUploadId = dbRef.push().key
        dbUserImagesRef.child(imageUploadId).setValue(image)
    }

}