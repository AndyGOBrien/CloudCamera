package com.llamalabb.cloudcamera.ktfiles

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import android.widget.ImageView
import com.llamalabb.cloudcamera.GlideApp

/**
 * Created by andy on 11/28/17.
 */

class TextWatcherFactory{
    fun create(sOnTextChanged : (sequence: CharSequence) -> Unit) : TextWatcher{
        return object: TextWatcher{
            override fun afterTextChanged(p0: Editable) {}
            override fun beforeTextChanged(p0: CharSequence, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence, p1: Int, p2: Int, p3: Int)= sOnTextChanged(p0)
        }
    }
}


fun EditText.setSimpleOnTextChangedListener(listener : (text: CharSequence) -> Unit) {
    this.addTextChangedListener(TextWatcherFactory().create(listener))
}

fun EditText.asString() = this.text.toString()

fun ImageView.loadImage(url : String){
    GlideApp.with(context)
            .load(url)
            .centerCrop()
            .fitCenter()
            .into(this)
}
