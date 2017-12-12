package com.llamalabb.cloudcamera.model

import com.google.firebase.database.IgnoreExtraProperties

/**
 * Created by andy on 12/1/17.
 */

@IgnoreExtraProperties
data class MyImage(
        val id: String = "",
        val url: String = "",
        val owner: String? = "",
        val upvote_count: Int = 1,
        val downvotes_count: Int = 0,
        val upvotes: HashMap<String?, Boolean> = hashMapOf(Pair(owner, true)),
        val downvotes: HashMap<String?, Boolean> = hashMapOf(),
        val timestamp: Long = System.currentTimeMillis()
)