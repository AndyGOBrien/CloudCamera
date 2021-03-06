package com.llamalabb.cloudcamera.model

/**
 * Created by andy on 12/7/17.
 */
data class User(
        var firstname: String? = null,
        var lastname: String? = null,
        var username: String? = null,
        var displayname: String? = null,
        var emailaddress: String? = null,
        var imagesowned: HashMap<String, String>? = null
)