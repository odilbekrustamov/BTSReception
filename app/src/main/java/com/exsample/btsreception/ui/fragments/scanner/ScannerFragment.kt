package com.exsample.btsreception.ui.fragments.scanner

import android.os.Bundle
import android.view.View
import com.exsample.btsreception.R
import com.exsample.btsreception.databinding.FragmentScannerBinding
import com.exsample.btsreception.ui.fragments.BaseFragment


class ScannerFragment : BaseFragment(R.layout.fragment_scanner) {
    private lateinit var binding: FragmentScannerBinding
    private val TAG = ScannerFragment::class.java.simpleName


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentScannerBinding.bind(view)

        initViews()
    }


    private fun initViews() {

    }


}