package com.misenpai.anivault.core.util

import android.content.Context
import android.widget.Toast
import androidx.compose.ui.text.intl.Locale
import java.text.SimpleDateFormat
import java.util.*

fun Context.showToast(message: String, duration: Int = Toast.LENGTH_SHORT){
    Toast.makeText(this,message,duration).show()
}

fun String.isValidEmail(): Boolean{
    return android.util.Patterns.EMAIL_ADDRESS.matcher(this).matches()
}

fun Date.formatToString(pattern:String = "yyyy-MM-dd"): String{
    val formatter = SimpleDateFormat(pattern, java.util.Locale.getDefault())
    return formatter.format(this)
}

fun Int?.orZero():Int = this?:0
fun Double?.orZero(): Double = this?:0.0
fun String?.orEmpty(): String = this?:""