package com.example.ktortesting.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.ktortesting.databinding.ItemSchoolBinding
import com.example.ktortesting.struct.School

class SchoolListAdapter : RecyclerView.Adapter<SchoolListAdapter.SchoolViewHolder>() {

    var items: List<School> = emptyList()
        set(newItems) {
            diffItems(field, newItems).dispatchUpdatesTo(this)
            field = newItems
        }

    override fun onCreateViewHolder(parent: ViewGroup, position: Int) = SchoolViewHolder(
        ItemSchoolBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: SchoolViewHolder, position: Int) {
        holder.bind(items[position], position)
    }

    private fun diffItems(
        oldItems: List<School>,
        newItems: List<School>,
    ) = DiffUtil.calculateDiff(object : DiffUtil.Callback() {

        override fun getOldListSize() = oldItems.size

        override fun getNewListSize() = newItems.size

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            val oldItem = oldItems[oldItemPosition]
            val newItem = newItems[newItemPosition]
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            val oldItem = oldItems[oldItemPosition]
            val newItem = newItems[newItemPosition]
            return oldItem.name == newItem.name
        }
    })


    inner class SchoolViewHolder(itemViewBinding: ItemSchoolBinding): RecyclerView.ViewHolder(itemViewBinding.root) {
        private val schoolNameTextView = itemViewBinding.schoolNameTextView

        fun bind(item: School, position: Int) {
            schoolNameTextView.text = item.name
        }
    }
}