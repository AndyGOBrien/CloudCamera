package com.llamalabb.cloudcamera.model

import android.media.Image
import android.net.Uri
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage

/**
 * Created by andy on 12/1/17.
 */
object DataManager {
    private val user = FirebaseAuth.getInstance().currentUser
    private val db = FirebaseDatabase.getInstance()
    private val dbRef = db.reference
    private val dbUserImagesRef = dbRef.child("users").child(user?.uid).child("images")

    private val storage = FirebaseStorage.getInstance()
    private val storageRef = storage.reference

    val images = ArrayList<MyImage>()


    interface DMCallBack{
        fun dataSetChanged()
    }

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

    fun buildListener(callBack: DMCallBack){
        val postListener = object: ValueEventListener {
            override fun onCancelled(p0: DatabaseError?) {}
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                images.clear()
                dataSnapshot.children.forEach {
                    val value = it.getValue(MyImage::class.java)
                    images.add(value!!)
                    Log.d("Image", "image")
                    callBack.dataSetChanged()
                }

            }
        }
        dbUserImagesRef.addListenerForSingleValueEvent(postListener)
        dbUserImagesRef.addValueEventListener(postListener)
    }
}