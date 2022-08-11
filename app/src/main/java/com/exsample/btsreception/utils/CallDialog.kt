package com.exsample.btsreception.utils

import android.app.Activity
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.Window
import com.exsample.btsreception.databinding.CallDialogLayoutBinding
import com.exsample.btsreception.helper.OnClickEvent

class CallDialog(private val onClickEvent: OnClickEvent) {


    fun showCalendarDialog(activity: Activity?, number: String, name: String?) {
        val binding = CallDialogLayoutBinding.inflate(LayoutInflater.from(activity))
        val dialog = Dialog(activity!!)
        dialog.apply {
            requestWindowFeature(Window.FEATURE_NO_TITLE)
            setCancelable(false)
            setContentView(binding.root)
            window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            setCancelable(true)
        }

        binding.tvNumber.text = number

        name?.let {
            binding.tvName.text = name
        }

        binding.llEnabledCall.setOnClickListener {
            onClickEvent.setOnNotCallClickListener()
            dialog.dismiss()
        }

        binding.llCall.setOnClickListener {
            onClickEvent.setOnCallClickListener(number)
            dialog.dismiss()
        }

        dialog.show()
    }

}