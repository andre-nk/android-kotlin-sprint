package com.andrenk.mortis.listView

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.andrenk.mortis.R
import com.andrenk.mortis.databinding.FragmentListBinding

class ListFragment : Fragment() {
    private val viewModel: ListViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentListBinding.inflate(inflater)

        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        binding.listView.adapter = ListViewAdapter(ListViewListener { people ->
            viewModel.selectPeople(people)
            val navController = Navigation.findNavController(requireActivity(), R.id.list_view)
            navController.navigate(R.id.action_listFragment_to_peopleDetailFragment)
        })

        return binding.root
    }
}