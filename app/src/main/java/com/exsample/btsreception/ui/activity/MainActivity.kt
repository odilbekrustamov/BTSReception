package com.exsample.btsreception.ui.activity

import android.Manifest
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import com.exsample.btsreception.R
import com.exsample.btsreception.receiver.PhonecallReceiver
import com.exsample.btsreception.ui.fragments.home.HomeViewModel
import com.exsample.readnumbercall.model.CallNumber
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var receiver: PhonecallReceiver
    val requestCode = 1001
    private val TAG = "MainActivity"

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        receiver = PhonecallReceiver()

        initViews()
    }

    private fun initViews() {
        if ( ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED
            && ContextCompat.checkSelfPermission(this,
                Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED
            && ContextCompat.checkSelfPermission(this,
                Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED
        ) {

        } else {
            requestSContactPermissiopn()
            Log.d(TAG, "onCreate: redwcfwwwww")
        }
    }

    private fun requestSContactPermissiopn() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                Manifest.permission.READ_CALL_LOG)
            && ActivityCompat.shouldShowRequestPermissionRationale(this,
                Manifest.permission.WRITE_CALL_LOG)
            && ActivityCompat.shouldShowRequestPermissionRationale(this,
                Manifest.permission.READ_PHONE_STATE)
            && ActivityCompat.shouldShowRequestPermissionRationale(this,
                Manifest.permission.CALL_PHONE)
            && ActivityCompat.shouldShowRequestPermissionRationale(this,
                Manifest.permission.PROCESS_OUTGOING_CALLS)
            && ActivityCompat.shouldShowRequestPermissionRationale(this,
                Manifest.permission.CAMERA)
        ) {
            ActivityCompat.requestPermissions(this,
                arrayOf(Manifest.permission.READ_CALL_LOG, Manifest.permission.WRITE_CALL_LOG, Manifest.permission.READ_PHONE_STATE, Manifest.permission.CALL_PHONE,  Manifest.permission.CAMERA),
                requestCode)
            Log.d(TAG, "requestSContactPermissiopn: wfde")
            initViews()
        } else {
            ActivityCompat.requestPermissions(this,
                arrayOf(Manifest.permission.READ_CALL_LOG, Manifest.permission.WRITE_CALL_LOG, Manifest.permission.READ_PHONE_STATE, Manifest.permission.CALL_PHONE,  Manifest.permission.CAMERA),
                requestCode)
            Log.d(TAG, "requestSContactPermissiopn: fewcre")
            initViews()
        }
    }


    override fun onStart() {
        super.onStart()
        val filter = IntentFilter("android.intent.action.PHONE_STATE")
        filter.addAction("android.intent.action.NEW_OUTGOING_CALL")
        registerReceiver(receiver, filter)
    }

    override fun onStop() {
        super.onStop()
        unregisterReceiver(receiver)
    }
}