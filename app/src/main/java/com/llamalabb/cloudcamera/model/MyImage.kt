package com.llamalabb.cloudcamera.model

import com.google.firebase.database.IgnoreExtraProperties

/**
 * Created by andy on 12/1/17.
 */

@IgnoreExtraProperties
data class MyImage(val url: String? = null)