package com.exsample.btsreception

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class BTSApp: Application(){

    private val TAG = "ImperativeApp"

    override fun onCreate() {
        super.onCreate()
    }
}