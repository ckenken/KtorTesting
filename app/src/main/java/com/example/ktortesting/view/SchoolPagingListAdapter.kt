package com.example.ktortesting.view

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.ktortesting.databinding.ItemSchoolBinding
import com.example.ktortesting.struct.School
import kotlinx.coroutines.Dispatchers

class SchoolPagingListAdapter :
    PagingDataAdapter<School, SchoolPagingListAdapter.SchoolViewHolder>(
        diffCallback = INDIFFERENCE,
        mainDispatcher = Dispatchers.Main,
        workerDispatcher =  Dispatchers.IO,
    ) {

    companion object {
        val INDIFFERENCE = object : DiffUtil.ItemCallback<School>() {
            override fun areItemsTheSame(oldItem: School, newItem: School): Boolean {
                Log.d("ckenken", "areItemsTheSame() = ${oldItem == newItem}")
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: School, newItem: School): Boolean {
                Log.d("ckenken", "areContentsTheSame() = ${oldItem.name == newItem.name}")
                return oldItem.name == newItem.name
            }
        }
    }

    inner class SchoolViewHolder(itemViewBinding: ItemSchoolBinding) :
        RecyclerView.ViewHolder(itemViewBinding.root) {
        private val schoolNameTextView = itemViewBinding.schoolNameTextView

        fun bind(item: School?, position: Int) {
            schoolNameTextView.text = item?.name
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, position: Int): SchoolViewHolder {
        return SchoolViewHolder(
            ItemSchoolBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: SchoolViewHolder, position: Int) {
        holder.bind(getItem(position), position)
    }
}