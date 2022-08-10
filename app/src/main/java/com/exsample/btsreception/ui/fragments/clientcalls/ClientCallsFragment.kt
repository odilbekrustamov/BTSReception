package com.exsample.btsreception.ui.fragments.clientcalls

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.exsample.btsreception.R
import com.exsample.btsreception.adapter.CallsAdapter
import com.exsample.btsreception.databinding.FragmentClientCallsBinding
import com.exsample.btsreception.ui.fragments.BaseFragment
import com.exsample.btsreception.ui.fragments.home.HomeFragment
import com.exsample.btsreception.ui.fragments.home.HomeViewModel
import com.exsample.btsreception.utils.KeyValues
import com.exsample.readnumbercall.model.CallNumber
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ClientCallsFragment : BaseFragment(R.layout.fragment_client_calls) {
    private val TAG = ClientCallsFragment::class.java.simpleName
    private lateinit var binding: FragmentClientCallsBinding
    private val viewModel: ClientCallsViewModel by viewModels()
    private var number: String? = null
    private lateinit var adapter: CallsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        number = arguments?.get(KeyValues.NUMBER) as String
        Log.d(TAG, "setOnCallClickListenerggg: $number")
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentClientCallsBinding.bind(view)
        adapter = CallsAdapter()

        initViews()
    }

    private fun initViews() {
        viewModel.getItems(number!!)

        initObserves()
    }

    private fun initObserves() {
        viewModel.callNumbersFromDB.observe(requireActivity(), {
            Log.d(TAG, "initObserves: $it")
            adapter.submitList(it as ArrayList<CallNumber>)
            binding.recyclerView.adapter = adapter
        })

    }

}