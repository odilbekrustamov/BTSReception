package com.exsample.btsreception.ui.fragments.home

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.exsample.btsreception.R
import com.exsample.btsreception.adapter.ClientAdapter
import com.exsample.btsreception.databinding.FragmentHomeBinding
import com.exsample.btsreception.helper.OnItemClickEvent
import com.exsample.btsreception.model.ClientList
import com.exsample.btsreception.ui.fragments.BaseFragment
import com.exsample.btsreception.utils.KeyValues.NUMBER
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class HomeFragment : BaseFragment(R.layout.fragment_home) {
    private val TAG = HomeFragment::class.java.simpleName
    private lateinit var binding: FragmentHomeBinding
    private val viewModel: HomeViewModel by viewModels()
    private lateinit var adapter: ClientAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentHomeBinding.bind(view)

        insertClientNumbers()

        initViews()
    }

    private fun insertClientNumbers() {

        viewModel.insertClientNumberToDB(ClientList("973870910"))
        viewModel.insertClientNumberToDB(ClientList("+998973870912"))
        viewModel.insertClientNumberToDB(ClientList("+998914690811"))
    }

    private fun initViews() {
        adapter = ClientAdapter(object : OnItemClickEvent{
            override fun setOnCallClickListener(number: String) {
                val intentDial = Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + number))
                context!!.startActivity(intentDial)
            }

            override fun setOnOpenFragmentClickListener(number: String) {
                Log.d("TAG", "setOnCallClickListener: $number")
                findNavController().navigate(R.id.action_homeFragment_to_clientCallsFragment, bundleOf(NUMBER to number))
            }

        })

        binding.ivScanner.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_scannerFragment)
        }

        binding.ivProfile.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_profileFragment)
        }

        viewModel.getClient()

        initObserves()
    }

    private fun initObserves() {
        viewModel.clientListsFromDB.observe(requireActivity(), {
            adapter.submitList(it as ArrayList<ClientList>)
            binding.recyclerView.adapter = adapter
        })
    }


}