package com.exsample.btsreception.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.telephony.PhoneStateListener
import android.telephony.TelephonyManager
import android.util.Log
import com.exsample.btsreception.repository.MainRepository
import com.exsample.btsreception.utils.KeyValues.INCOMING
import com.exsample.btsreception.utils.KeyValues.MISSED
import com.exsample.btsreception.utils.KeyValues.OUTGOING
import com.exsample.btsreception.utils.Functions.dateBetween
import com.exsample.btsreception.utils.Functions.dateConvert
import com.exsample.readnumbercall.model.CallNumber
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject


@AndroidEntryPoint
class PhonecallReceiver : BroadcastReceiver() {

    @Inject
    lateinit var mainRepository: MainRepository
    protected var savedContext: Context? = null
    override fun onReceive(context: Context, intent: Intent) {

        savedContext = context
        if (listener == null) {
            listener = PhonecallStartEndDetector()
        }
        val bundle = intent.extras
        val phoneNumber = bundle!!.getString("incoming_number")
        listener!!.setOutgoingNumber(phoneNumber)


        val telephony = context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
        telephony.listen(listener, PhoneStateListener.LISTEN_CALL_STATE)
    }

    inner class PhonecallStartEndDetector : PhoneStateListener() {
        var lastState = TelephonyManager.CALL_STATE_IDLE
        var callStartTime: Date? = null
        var isIncoming = false
        var savedNumber: String? = null

        fun setOutgoingNumber(number: String?) {
            savedNumber = number
        }

        override fun onCallStateChanged(state: Int, incomingNumber: String) {
            super.onCallStateChanged(state, incomingNumber)
            if (lastState == state) {
                return
            }
            when (state) {
                TelephonyManager.CALL_STATE_RINGING -> {
                    isIncoming = true
                    callStartTime = Date()
                    savedNumber = incomingNumber
                    Log.d("TAG", "onCallStateChanged  onIncomingCallStarted: $incomingNumber   $callStartTime")
                }
                TelephonyManager.CALL_STATE_OFFHOOK ->
                    if (lastState != TelephonyManager.CALL_STATE_RINGING) {
                        isIncoming = false
                        callStartTime = Date()
                        Log.d("TAG", "onCallStateChanged  onOutgoingCallStarted: $savedNumber   $callStartTime")
                    }


                TelephonyManager.CALL_STATE_IDLE ->
                    if (lastState == TelephonyManager.CALL_STATE_RINGING) {
                        Log.d("TAG", "onCallStateChanged  onMissedCall: $savedNumber   $callStartTime  ${Date()}")

                        CoroutineScope(Dispatchers.IO).launch {
                            Log.d("TAG", "onCallStateChanged: ${mainRepository.getClientNumber(savedNumber!!)}")

                            if (mainRepository.getClientNumber(savedNumber!!)){
                                val callNumber = CallNumber(callTime = dateConvert(callStartTime!!),
                                    number = savedNumber!!, timeSpent = dateBetween(Date(), callStartTime!!),
                                    callType = MISSED, isSent = false)

                                mainRepository.insertCallNumberToDB(callNumber)
                            }
                        }
                    } else if (isIncoming) {
                        Log.d("TAG", "onCallStateChanged  onIncomingCallEnded: $savedNumber   $callStartTime ${Date()}")

                        CoroutineScope(Dispatchers.IO).launch {
                            Log.d("TAG", "onCallStateChanged SDSD: ${mainRepository.getClientNumber(savedNumber!!)}")
                            if (mainRepository.getClientNumber(savedNumber!!)){

                                val callNumber = CallNumber(callTime = dateConvert(callStartTime!!),
                                    number = savedNumber!!, timeSpent = dateBetween(Date(), callStartTime!!),
                                    callType = INCOMING, isSent = false)
                                mainRepository.insertCallNumberToDB(callNumber)
                            }
                        }
                    } else {
                        Log.d("TAG", "onCallStateChanged  onOutgoingCallEnded: $savedNumber   $callStartTime  ${Date()}")

                        CoroutineScope(Dispatchers.IO).launch {
                            Log.d("TAG", "onCallStateChanged: ${mainRepository.getClientNumber(savedNumber!!)}")

                            if (mainRepository.getClientNumber(savedNumber!!)){
                                Log.d("TAG", "onCallStateChanged: ")
                                val callNumber = CallNumber(callTime = dateConvert(callStartTime!!),
                                    number = savedNumber!!, timeSpent = dateBetween(Date(), callStartTime!!),
                                    callType = OUTGOING, isSent = false)

                                mainRepository.insertCallNumberToDB(callNumber)
                            }
                        }
                    }
            }
            lastState = state
        }
    }

    companion object {
        var listener: PhonecallStartEndDetector? = null
    }
}