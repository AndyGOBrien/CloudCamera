package com.llamalabb.cloudcamera.model

import android.net.Uri
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage

/**
 * Created by andy on 12/1/17.
 */
object DataManager {

    private val TAG = "DataManager"

    private val user = FirebaseAuth.getInstance().currentUser

    private val db = FirebaseDatabase.getInstance()
    private val dbRef = db.reference
    private val dbUserRef = dbRef.child("users").child(user?.uid)
    private val dbUserImagesRef = dbUserRef.child("imagesowned")
    private val dbImagesRef = dbRef.child("images")

    private val storage = FirebaseStorage.getInstance()
    private val storageRef = storage.reference

    val images = ArrayList<String>()
    val voteList = ArrayList<MyImage>()

    init{
        voteListListener()
    }

    interface DMCallBack{
        fun dataSetChanged()
    }

    interface HasUsernameCallBack {
        fun hasUsernameChecked(hasUsername: Boolean)
    }

    interface UsernameAvailableCallBack {
        fun usernameAvailableChecked(isUsernameAvailable: Boolean)
        fun usernameSetSuccess(displayName: String)
    }

    fun uploadFileToStorage(fileEx: String, uri: Uri){
        val imgRef = storageRef.child("users/" + user?.uid + "/" + "images/" + System.currentTimeMillis() + "." + fileEx)
        imgRef.putFile(uri).addOnSuccessListener {
            val image = MyImage(id=dbRef.push().key,url=it.downloadUrl.toString(), owner=user?.uid)
            uploadImageToDatabase(image)
        }
    }

    private fun uploadImageToDatabase(image: MyImage){
        dbImagesRef.child(image.id).setValue(image)
    }

    fun isUsernameAvailable(username: String, callBack: UsernameAvailableCallBack){
        val rawUsername = username.trim().toLowerCase()
        dbRef.child("usernames").child(rawUsername).addListenerForSingleValueEvent(object: ValueEventListener{
            override fun onDataChange(dataSnapshot: DataSnapshot) { callBack.usernameAvailableChecked(!dataSnapshot.exists()) }
            override fun onCancelled(dbError: DatabaseError) {
                Log.d("DataManager", dbError.message)
            }
        })
    }

    fun hasUsername(callBackHas: HasUsernameCallBack){
        dbUserRef.child("username").addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) { callBackHas.hasUsernameChecked(dataSnapshot.exists()) }
            override fun onCancelled(dbError: DatabaseError) {
                Log.d("DataManager", dbError.message)
            }
        })
    }

    fun setUsername(displayName: String, callBack: UsernameAvailableCallBack){
        val displayNameTrim = displayName.trim()
        val dbDisplayNameRef = dbUserRef.child("displayname")
        dbDisplayNameRef.setValue(displayNameTrim)
                .addOnSuccessListener { callBack.usernameSetSuccess(displayNameTrim) }
    }

    fun userGalleryListener(callBack: DMCallBack){
        val userImagesListener = object: ValueEventListener {
            override fun onCancelled(p0: DatabaseError?) {}
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                images.clear()
                dataSnapshot.children.forEach {
                    images.add(it.value as String)
                }
                callBack.dataSetChanged()
            }
        }

        dbUserImagesRef.addListenerForSingleValueEvent(userImagesListener)
        dbUserImagesRef.addValueEventListener(userImagesListener)
    }

    fun voteListListener(){
        val newImageRef = dbImagesRef.limitToFirst(20)
        val voteImagesListener = object: ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                voteList.clear()
                snapshot.children.forEach{
                    val value = it.getValue(MyImage::class.java)
                    value?.let{
                        if(!it.downvotes.containsKey(user?.uid) || !it.upvotes.containsKey(user?.uid)){
                            voteList.add(it)
                        }
                    }
                }
            }
            override fun onCancelled(dbError: DatabaseError) { Log.d(TAG, dbError.message) }
        }
        newImageRef.addValueEventListener(voteImagesListener)
    }

    fun voteImageUp(imageId: String){
         dbImagesRef.child(imageId).child("upvotes").child(user?.uid).setValue("true")
    }

    fun voteImageDown(imageId: String){
        dbImagesRef.child(imageId).child("downvotes").child(user?.uid).setValue("true")
    }

}