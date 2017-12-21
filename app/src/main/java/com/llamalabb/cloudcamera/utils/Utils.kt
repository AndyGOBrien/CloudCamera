package com.llamalabb.simplefirebaselogin.utils

import android.content.Context
import android.widget.Toast

/**
 * Created by andy on 10/18/17.
 */
object Utils {

    private val TAG = "Utils"

    fun showMessageShort(context: Context, msg: String){
        Toast.makeText(context,msg,Toast.LENGTH_SHORT).show()
    }
    fun showMessageLong(context: Context, msg: String){
        Toast.makeText(context,msg,Toast.LENGTH_LONG).show()
    }
}