package com.mrr.animalshelter.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mrr.animalshelter.R
import com.mrr.animalshelter.data.element.AnimalSterilization
import kotlinx.android.synthetic.main.item_filter.view.*

class SterilizationAdapter : ListAdapter<AnimalSterilization, SterilizationAdapter.FilterItemViewHolder>(
    object : DiffUtil.ItemCallback<AnimalSterilization>() {
        override fun areItemsTheSame(oldItem: AnimalSterilization, newItem: AnimalSterilization): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: AnimalSterilization, newItem: AnimalSterilization): Boolean {
            return oldItem == newItem
        }
    }
) {

    var selectedSterilization: AnimalSterilization? = null
    var onItemClickListener: ((sterilization: AnimalSterilization) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilterItemViewHolder {
        return FilterItemViewHolder(parent)
    }

    override fun onBindViewHolder(holder: FilterItemViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class FilterItemViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_filter, parent, false)
    ) {
        private lateinit var sterilization: AnimalSterilization
        private val onItemViewClickListener = View.OnClickListener {
            onItemClickListener?.invoke(sterilization)
        }

        fun bind(sterilization: AnimalSterilization) {
            this.sterilization = sterilization
            itemView.tvContent.text = itemView.context.getString(sterilization.nameResourceId)
            itemView.tvContent.setTextColor(itemView.context.getColor(if (sterilization == selectedSterilization) android.R.color.white else android.R.color.black))
            itemView.setBackgroundResource(if (sterilization == selectedSterilization) R.drawable.bg_item_filter_selected else R.drawable.bg_item_filter_unselected)
            itemView.setOnClickListener(onItemViewClickListener)
        }
    }
}