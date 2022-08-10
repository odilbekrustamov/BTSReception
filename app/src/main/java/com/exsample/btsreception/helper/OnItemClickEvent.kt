package com.exsample.btsreception.helper

import java.util.*

interface OnItemClickEvent {

    fun setOnCallClickListener(number: String)
    fun setOnOpenFragmentClickListener(number: String)

}