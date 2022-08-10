package com.exsample.btsreception.ui.fragments.profile

import android.os.Bundle
import android.view.View
import com.exsample.btsreception.R
import com.exsample.btsreception.databinding.FragmentProfileBinding
import com.exsample.btsreception.ui.fragments.BaseFragment

class ProfileFragment : BaseFragment(R.layout.fragment_profile) {
    private lateinit var binding: FragmentProfileBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentProfileBinding.bind(view)
        initViews()
    }

    private fun initViews() {
        binding.ivBack.setOnClickListener {
            requireActivity().onBackPressed()
        }
    }

}