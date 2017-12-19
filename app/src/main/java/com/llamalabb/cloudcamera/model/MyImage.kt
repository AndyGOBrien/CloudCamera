package com.llamalabb.cloudcamera.model

import com.google.firebase.database.IgnoreExtraProperties

/**
 * Created by andy on 12/1/17.
 */

@IgnoreExtraProperties
data class MyImage(
        val id: String = "",
        val url: String = "",
        val owner_id: String? = null,
        val owner_dn: String? = null,
        val upvote_count: Int = 1,
        val downvote_count: Int = 0,
        val upvotes: HashMap<String?, Boolean> = hashMapOf(Pair(owner_id, true)),
        val downvotes: HashMap<String?, Boolean> = hashMapOf(),
        val timestamp: Long = System.currentTimeMillis()
)