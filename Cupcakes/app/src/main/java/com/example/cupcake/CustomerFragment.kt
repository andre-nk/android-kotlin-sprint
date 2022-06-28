package com.example.cupcake

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.cupcake.databinding.FragmentCustomerBinding
import com.example.cupcake.model.OrderViewModel

class CustomerFragment: Fragment() {
    private var binding: FragmentCustomerBinding? = null
    private val sharedViewModel: OrderViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val fragmentBinding = FragmentCustomerBinding.inflate(inflater, container, false)
        binding = fragmentBinding
        return fragmentBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.apply {
            viewModel = sharedViewModel
            nextButton.setOnClickListener {
                updateDataAndGoToNextScreen()
            }
            cancelButton.setOnClickListener {
                sharedViewModel.resetOrder()
                findNavController().navigate(R.id.action_customerFragment_to_startFragment)
            }
        }
    }

    private fun updateDataAndGoToNextScreen(){
        sharedViewModel.setCustomerInfo(
            binding?.customerNameInputText?.text.toString(),
            binding?.customerAddressInputText?.text.toString()
        )

        findNavController().navigate(R.id.action_customerFragment_to_pickupFragment)
    }
}