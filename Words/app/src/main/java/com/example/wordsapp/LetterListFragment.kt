package com.example.wordsapp

import android.os.Bundle
import android.view.*
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.wordsapp.databinding.FragmentLetterListBinding

class LetterListFragment : Fragment() {
   private var _binding: FragmentLetterListBinding? = null
   private val binding get() = _binding!!
   private lateinit var recyclerView: RecyclerView
   private var isLinearLayoutManager = true

   //In Fragment, no context is available in this keyword (Activity's LayoutManager)
   //But, we can use global context...
   private fun chooseLayout(){
      if(isLinearLayoutManager){
         recyclerView.layoutManager = LinearLayoutManager(context)
      } else {
         recyclerView.layoutManager = GridLayoutManager(context, 4)
      }
      recyclerView.adapter = LetterAdapter()
   }

   //or.. Force (null-safety) access context from the "this" keyword, which is nullable
   private fun setIcon(menuItem: MenuItem?) {
      if(menuItem == null){
         return
      }

      menuItem.icon = if(isLinearLayoutManager)
         ContextCompat.getDrawable(this.requireContext(), R.drawable.ic_linear_layout)
      else
         ContextCompat.getDrawable(this.requireContext(), R.drawable.ic_grid_layout)
   }


   //? The fragment is instantiated, but not with the view
   override fun onCreate(savedInstanceState: Bundle?) {
      super.onCreate(savedInstanceState)
      setHasOptionsMenu(true)
   }

   //? Inflate (fill) the layout here
   override fun onCreateView(
      inflater: LayoutInflater,
      container: ViewGroup?,
      savedInstanceState: Bundle?
   ): View? {
      _binding = FragmentLetterListBinding.inflate(inflater, container, false)
      return binding.root
   }


   //Inflate the toolbar's option menu, different from Activity override, Fragment override got inflater passed in this function
   //and doesn't require return
   override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
      inflater.inflate(R.menu.layout_menu, menu)
      val layoutButton = menu.findItem(R.id.action_switch_layout)

      setIcon(layoutButton)
   }

   //? Settings up bindings after all view is displayed
   override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
      //Bind late init recyclerView here with XML's recycler view from the corresponding fragment
      //No need for findViewById here
      recyclerView = binding.recyclerView
      chooseLayout()
   }

   override fun onDestroyView() {
      super.onDestroyView()

      //Reset the binding to null
      _binding = null
   }
}