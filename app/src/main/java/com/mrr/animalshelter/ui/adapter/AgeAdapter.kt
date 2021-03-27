package com.mrr.animalshelter.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mrr.animalshelter.R
import com.mrr.animalshelter.data.element.AnimalAge
import kotlinx.android.synthetic.main.item_filter.view.*

class AgeAdapter : ListAdapter<AnimalAge, AgeAdapter.FilterItemViewHolder>(
    object : DiffUtil.ItemCallback<AnimalAge>() {
        override fun areItemsTheSame(oldItem: AnimalAge, newItem: AnimalAge): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: AnimalAge, newItem: AnimalAge): Boolean {
            return oldItem == newItem
        }
    }
) {

    var selectedAge: AnimalAge? = null
    var onItemClickListener: ((age: AnimalAge) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilterItemViewHolder {
        return FilterItemViewHolder(parent)
    }

    override fun onBindViewHolder(holder: FilterItemViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class FilterItemViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_filter, parent, false)
    ) {
        private lateinit var age: AnimalAge
        private val onItemViewClickListener = View.OnClickListener {
            onItemClickListener?.invoke(age)
        }

        fun bind(age: AnimalAge) {
            this.age = age
            itemView.tvContent.text = itemView.context.getString(age.nameResourceId)
            itemView.tvContent.setTextColor(itemView.context.getColor(if (age == selectedAge) android.R.color.white else android.R.color.black))
            itemView.setBackgroundResource(if (age == selectedAge) R.drawable.bg_item_filter_selected else R.drawable.bg_item_filter_unselected)
            itemView.setOnClickListener(onItemViewClickListener)
        }
    }
}