package com.llamalabb.cloudcamera.model

import android.content.Context
import android.util.Log
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.llamalabb.cloudcamera.R
import com.mindorks.placeholderview.SwipePlaceHolderView
import com.mindorks.placeholderview.annotations.NonReusable
import com.mindorks.placeholderview.annotations.Resolve
import com.mindorks.placeholderview.annotations.View
import com.mindorks.placeholderview.annotations.swipe.SwipeCancelState
import com.mindorks.placeholderview.annotations.swipe.SwipeOut
import com.mindorks.placeholderview.annotations.swipe.SwipeOutState
import com.mindorks.placeholderview.annotations.swipe.SwipeInState
import com.mindorks.placeholderview.annotations.swipe.SwipeIn



/**
 * Created by andy on 12/12/17.
 */
@NonReusable
class VoteCard(val context: Context, val image: MyImage, val swipeView: SwipePlaceHolderView){

    @View(R.id.vote_imageView)
    private lateinit var voteImageView: ImageView

    @Resolve
    fun onResolved(){
        Glide.with(context).load(image.url).into(voteImageView)
    }

    @SwipeOut
    private fun onSwipedOut(){
        Log.d("EVENT", "onSwipedOut")
        swipeView.addView(this)
        DataManager.voteImageDown(image.id)
    }

    @SwipeCancelState
    private fun onSwipeCancelState(){
        Log.d("EVENT", "onSwipeCancelState")
    }

    @SwipeIn
    private fun onSwipeIn() {
        Log.d("EVENT", "onSwipedIn")
        DataManager.voteImageUp(image.id)
    }

    @SwipeInState
    private fun onSwipeInState() {
        Log.d("EVENT", "onSwipeInState")
    }

    @SwipeOutState
    private fun onSwipeOutState() {
        Log.d("EVENT", "onSwipeOutState")
    }


}