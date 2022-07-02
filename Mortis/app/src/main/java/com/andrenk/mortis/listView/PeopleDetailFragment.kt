package com.andrenk.mortis.listView

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.andrenk.mortis.databinding.FragmentPeopleDetailBinding

class PeopleDetailFragment : Fragment() {
    private val viewModel: ListViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentPeopleDetailBinding.inflate(inflater)
        binding.lifecycleOwner = this
        binding.listViewModel = viewModel

        // Inflate the layout for this fragment
        return binding.root
    }
}