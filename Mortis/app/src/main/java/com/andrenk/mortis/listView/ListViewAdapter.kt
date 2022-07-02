package com.andrenk.mortis.listView

import android.view.LayoutInflater
import android.view.ViewGroup

import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.andrenk.mortis.databinding.ListItemBinding
import com.andrenk.mortis.network.PeopleModel

class ListViewAdapter(private val clickListener: ListViewListener) :
    ListAdapter<PeopleModel, ListViewAdapter.PeopleModelViewHolder>(DiffCallback) {

    class PeopleModelViewHolder(
        private var binding: ListItemBinding
    ) : RecyclerView.ViewHolder(binding.root){
        fun bind(clickListener: ListViewListener, people: PeopleModel) {
            binding.people = people
            binding.clickListener = clickListener
            binding.executePendingBindings()
        }
    }

    companion object DiffCallback : DiffUtil.ItemCallback<PeopleModel>() {
        override fun areItemsTheSame(oldItem: PeopleModel, newItem: PeopleModel): Boolean {
            return oldItem.name == newItem.name
        }

        override fun areContentsTheSame(oldItem: PeopleModel, newItem: PeopleModel): Boolean {
            return oldItem.name == newItem.name
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PeopleModelViewHolder {
        return PeopleModelViewHolder(
            ListItemBinding.inflate(
                LayoutInflater.from(
                    parent.context
                ),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: PeopleModelViewHolder, position: Int) {
        val people = getItem(position)
        holder.bind(clickListener, people)
    }
}

class ListViewListener(val clickListener: (people: PeopleModel) -> Unit) {
    fun onClick(people: PeopleModel) = clickListener(people)
}