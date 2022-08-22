package com.exsample.btsreception.ui.fragments.scanner

import android.annotation.SuppressLint
import android.content.Intent
import android.hardware.Camera
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.SurfaceHolder
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.EditText
import android.widget.Toast
import com.exsample.btsreception.R
import com.exsample.btsreception.databinding.FragmentScannerBinding
import com.exsample.btsreception.helper.OnClickEvent
import com.exsample.btsreception.ui.fragments.BaseFragment
import com.exsample.btsreception.utils.CallDialog
import com.google.android.gms.vision.CameraSource
import com.google.android.gms.vision.Detector
import com.google.android.gms.vision.barcode.Barcode
import com.google.android.gms.vision.barcode.BarcodeDetector
import java.lang.reflect.Field

class ScannerFragment : BaseFragment(R.layout.fragment_scanner) {
    private lateinit var binding: FragmentScannerBinding
    private lateinit var cameraSource: CameraSource
    private lateinit var barcodeDetector: BarcodeDetector
    private var scannedValue = ""
    private var valueType = 0
    private var isFlashOff = false

    private var camera: Camera? = null
    var flashmode = false

    @SuppressLint("MissingPermission")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentScannerBinding.inflate(inflater,container,false)

        setupControls()
        val aniSlide: Animation = AnimationUtils.loadAnimation(
            requireActivity(), R.anim.scanner_animation)
        binding.barcodeLine.startAnimation(aniSlide)
        initViews()
        return binding.root
    }

    private fun initViews() {
        binding.onOff.setOnClickListener {
            if (isFlashOff){
                binding.onOff.setImageResource(R.drawable.ic_flashlight_off_balue)
                isFlashOff = false
                flashOnButton()
            }else{
                binding.onOff.setImageResource(R.drawable.ic_flashlight_turned_blue)
                isFlashOff = true
                flashOnButton()
            }
        }

        binding.ivClean.setOnClickListener {
            binding.etBarCode.text.clear()
        }

        binding.ivSearch.setOnClickListener {
            openCall(binding.etBarCode.text.toString())
        }
    }

    private fun openCall(text: String) {
        Log.d("TAG", "openCall: $text")

        val dialog = CallDialog(object : OnClickEvent {
            override fun setOnCallClickListener(number: String) {
                val callIntent = Intent(Intent.ACTION_CALL)
                callIntent.data = Uri.parse("tel:" + number)
                callIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                context!!.startActivity(callIntent)
                requireActivity().onBackPressed()
            }

            override fun setOnNotCallClickListener() {
                requireActivity().onBackPressed()
            }

        })
        dialog.showCalendarDialog(requireActivity(), "+998977751779", "Odilbek")
    }

    private fun flashOnButton() {
        camera = getCamera(cameraSource)
        if (camera != null) {
            try {
                val param = camera!!.parameters
                param.flashMode =
                    if (!flashmode) Camera.Parameters.FLASH_MODE_TORCH else Camera.Parameters.FLASH_MODE_OFF
                camera!!.parameters = param
                flashmode = !flashmode
                if (flashmode) {
                    Log.d("TAG", "flashOnButton: Flash Switched ON")
                } else {
                    Log.d("TAG", "flashOnButton: Flash Switched Off")
                }
            } catch (e: java.lang.Exception) {
                e.printStackTrace()
            }
        }
    }

    private fun getCamera(cameraSource: CameraSource): Camera? {
        val declaredFields: Array<Field> = CameraSource::class.java.declaredFields
        for (field in declaredFields) {
            if (field.getType() === Camera::class.java) {
                field.setAccessible(true)
                try {
                    val camera = field.get(cameraSource) as Camera
                    return if (camera != null) {
                        camera
                    } else null
                } catch (e: IllegalAccessException) {
                    e.printStackTrace()
                }
                break
            }
        }
        return null
    }

    private fun setupControls(){
        barcodeDetector =
            BarcodeDetector.Builder(requireContext()).setBarcodeFormats(Barcode.ALL_FORMATS).build()

        cameraSource = CameraSource.Builder(requireContext(),barcodeDetector)
            .setRequestedPreviewSize(1920,1080)
            .setAutoFocusEnabled(true)
            .build()

        binding.cameraSurfaceView.holder?.addCallback(object: SurfaceHolder.Callback{
            @SuppressLint("MissingPermission")
            override fun surfaceCreated(p0: SurfaceHolder) {
                try {
                    cameraSource.start(p0)
                }catch (e: Exception){
                    e.printStackTrace()
                }
            }
            @SuppressLint("MissingPermission")
            override fun surfaceChanged(p0: SurfaceHolder, p1: Int, p2: Int, p3: Int) {
                try {
                    cameraSource.start(p0)
                }catch (e: Exception){
                    e.printStackTrace()
                }

            }
            override fun surfaceDestroyed(p0: SurfaceHolder) {
                cameraSource.stop()
            }
        })

        barcodeDetector.setProcessor(object: Detector.Processor<Barcode>{
            override fun release() {
                Toast.makeText(requireContext(),"Scanner has been closed!", Toast.LENGTH_LONG).show()
            }
            override fun receiveDetections(p0: Detector.Detections<Barcode>) {
                val barcodes = p0.detectedItems
                if(barcodes.size()>0){
                    val scannedBarcode: Barcode = barcodes.valueAt(0)
                    if(barcodes.size() ==1){
                        Log.d("Scanner","${scannedBarcode.format}, ${scannedBarcode.valueFormat}")
                        scannedValue = scannedBarcode.rawValue
                        valueType = scannedBarcode.valueFormat
                        requireActivity().runOnUiThread {
                            cameraSource.stop()
                            binding.barcodeLine.clearAnimation()
                            binding.barcodeLine.visibility = View.GONE
                            checkValueType(scannedBarcode,valueType)
                        }
                    }else{
                        Log.d("Scanner","${scannedBarcode.format}, ${scannedBarcode.valueFormat}")

                        Toast.makeText(requireContext(),"value -else", Toast.LENGTH_SHORT).show()
                    }
                }else{
                    Log.d("Scanner","dwcxcsvdewrvce")
                }
            }
        })
    }

    @SuppressLint("SetTextI18n")
    private fun checkValueType(scannedBarcode:Barcode,valueType:Int){
        when(valueType){
            Barcode.WIFI ->{
                Log.d("TAG", "SSID - ${scannedBarcode.wifi.ssid} \n" +
                        "Password - ${scannedBarcode.wifi.password} \n" +
                        "Encryption type - ${scannedBarcode.wifi.encryptionType}")
                openCall("")
            }
            Barcode.URL ->{
                Log.d("TAG", "URL -${scannedBarcode.url.title}, ${scannedBarcode.url.url}")
                openCall("")
            }
            Barcode.PRODUCT ->{
                Log.d("TAG", "Product - ${scannedBarcode.displayValue}")
                openCall("")
            }
            Barcode.EMAIL ->{
                Log.d("TAG", "Email address- ${scannedBarcode.email.address}\n, " +
                        "${scannedBarcode.email.subject}\n,${scannedBarcode.email.body}\n," +
                        "${scannedBarcode.email.type}")
                openCall("")
            }
            Barcode.PHONE -> {
                Log.d("TAG", "Phone number- ${scannedBarcode.phone.number}")
                openCall("")
            }
            else ->{
                Log.d("TAG", scannedValue)
                openCall("")
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        cameraSource.stop()
    }
}