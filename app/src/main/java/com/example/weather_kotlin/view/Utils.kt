package com.example.weather_kotlin.view

import android.view.View
import com.google.android.material.snackbar.Snackbar
import java.text.SimpleDateFormat
import java.util.*


fun Date.format(): String =
    SimpleDateFormat("yyyy-MM-dd hh:mm:ss", Locale.getDefault()).format(this)

fun View.hide() {
    visibility = View.GONE
}

fun View.show() {
    visibility = View.VISIBLE
}

 fun View.showSnackBar (
    text : String,
    actionText : String,
    action : (View) -> Unit,
    lenght : Int = Snackbar.LENGTH_INDEFINITE
) {
    Snackbar.make(this, text, lenght).setAction(actionText, action).show()
}

