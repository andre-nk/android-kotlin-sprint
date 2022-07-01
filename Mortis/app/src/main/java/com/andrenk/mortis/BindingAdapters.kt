package com.andrenk.mortis

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.andrenk.mortis.listView.ListViewAdapter
import com.andrenk.mortis.network.PeopleModel

@BindingAdapter("listData")
fun bindRecyclerView(
    recyclerView: RecyclerView,
    data: List<PeopleModel>?
) {
    val adapter = recyclerView.adapter as ListViewAdapter
    adapter.submitList(data)
}