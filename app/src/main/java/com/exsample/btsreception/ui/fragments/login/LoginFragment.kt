package com.exsample.btsreception.ui.fragments.login

import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import androidx.navigation.fragment.findNavController
import com.exsample.btsreception.R
import com.exsample.btsreception.databinding.FragmentLoginBinding
import com.exsample.btsreception.ui.fragments.BaseFragment

class LoginFragment : BaseFragment(R.layout.fragment_login) {
   private lateinit var binding: FragmentLoginBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentLoginBinding.bind(view)

        initViews()
    }

    private fun initViews() {



        binding.btn.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
        }
    }
}