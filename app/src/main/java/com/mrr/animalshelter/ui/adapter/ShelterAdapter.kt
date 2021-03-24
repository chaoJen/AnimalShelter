package com.mrr.animalshelter.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mrr.animalshelter.R
import com.mrr.animalshelter.data.element.AnimalShelter
import kotlinx.android.synthetic.main.item_filter.view.*

class ShelterAdapter : ListAdapter<AnimalShelter, ShelterAdapter.FilterItemViewHolder>(
    object : DiffUtil.ItemCallback<AnimalShelter>() {
        override fun areItemsTheSame(oldItem: AnimalShelter, newItem: AnimalShelter): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: AnimalShelter, newItem: AnimalShelter): Boolean {
            return oldItem == newItem
        }
    }
) {

    var selectedShelter: AnimalShelter? = null
    var onItemClickListener: ((shelter: AnimalShelter) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilterItemViewHolder {
        return FilterItemViewHolder(parent)
    }

    override fun onBindViewHolder(holder: FilterItemViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class FilterItemViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_filter, parent, false)
    ) {
        private lateinit var shelter: AnimalShelter
        private val onItemViewClickListener = View.OnClickListener {
            onItemClickListener?.invoke(shelter)
        }

        fun bind(shelter: AnimalShelter) {
            this.shelter = shelter
            itemView.tvContent.text = itemView.context.getString(shelter.nameResourceId)
            itemView.tvContent.setTextColor(itemView.context.getColor(if (shelter == selectedShelter) android.R.color.white else android.R.color.black))
            itemView.setBackgroundResource(if (shelter == selectedShelter) R.drawable.bg_item_filter_selected else R.drawable.bg_item_filter_unselected)
            itemView.setOnClickListener(onItemViewClickListener)
        }
    }
}