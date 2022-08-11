package com.exsample.btsreception.utils

import java.text.DateFormat
import java.text.SimpleDateFormat
import java.time.LocalTime
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