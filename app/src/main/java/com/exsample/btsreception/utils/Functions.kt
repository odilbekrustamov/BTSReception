package com.exsample.btsreception.utils

import java.text.SimpleDateFormat
import java.util.*

object Functions {

    fun dateConvert(date: Date): String{
        return SimpleDateFormat("MM-dd-yyyy  HH:ss").format(date)
    }

    fun dateBetween(start: Date, end: Date): String{
        return (end.getTime() - start.getTime()).toInt().toString()
    }

}