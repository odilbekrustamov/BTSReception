package com.exsample.btsreception.ui.fragments.scanner

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.SurfaceHolder
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Toast
import com.exsample.btsreception.R
import com.exsample.btsreception.databinding.FragmentScannerBinding
import com.exsample.btsreception.ui.fragments.BaseFragment
import com.google.android.gms.vision.CameraSource
import com.google.android.gms.vision.Detector
import com.google.android.gms.vision.barcode.Barcode
import com.google.android.gms.vision.barcode.BarcodeDetector


class ScannerFragment : BaseFragment(R.layout.fragment_scanner) {
    private lateinit var binding: FragmentScannerBinding
    private lateinit var cameraSource: CameraSource
    private lateinit var barcodeDetector: BarcodeDetector
    private var scannedValue = ""
    private var valueType = 0
    private var isFlashOff = false

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

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
    }

    private fun initViews() {

        binding.onOff.setOnClickListener {
            if (isFlashOff){
                binding.onOff.setImageResource(R.drawable.ic_flashlight_off_balue)
                isFlashOff = false
            }else{
                binding.onOff.setImageResource(R.drawable.ic_flashlight_turned_blue)
                isFlashOff = true

            }
        }
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
            }
            Barcode.URL ->{
                Log.d("TAG", "URL -${scannedBarcode.url.title}, ${scannedBarcode.url.url}")
            }
            Barcode.PRODUCT ->{
                Log.d("TAG", "Product - ${scannedBarcode.displayValue}")
            }
            Barcode.EMAIL ->{
                Log.d("TAG", "Email address- ${scannedBarcode.email.address}\n, " +
                        "${scannedBarcode.email.subject}\n,${scannedBarcode.email.body}\n," +
                        "${scannedBarcode.email.type}")
            }
            Barcode.PHONE -> {
                Log.d("TAG", "Phone number- ${scannedBarcode.phone.number}")
            }
            else ->{
                Log.d("TAG", scannedValue)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        cameraSource.stop()
    }


}