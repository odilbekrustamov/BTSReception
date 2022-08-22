package com.exsample.btsreception.utils

import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.view.View
import android.widget.ImageView
import com.exsample.btsreception.R
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*


object Functions {

    fun dateConvert(date: Date): String{
        val formatter: DateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        val dt = Date()
        val currentTime: String = formatter.format(dt)
        return currentTime
    }

    fun dateBetween(start: Date, end: Date): String{
        return ((end.getTime() - start.getTime()).toInt() / 1000).toString()
    }

}