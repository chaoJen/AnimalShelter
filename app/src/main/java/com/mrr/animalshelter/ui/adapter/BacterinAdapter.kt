package com.mrr.animalshelter.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mrr.animalshelter.R
import com.mrr.animalshelter.data.element.AnimalBacterin
import kotlinx.android.synthetic.main.item_filter.view.*

class BacterinAdapter : ListAdapter<AnimalBacterin, BacterinAdapter.FilterItemViewHolder>(
    object : DiffUtil.ItemCallback<AnimalBacterin>() {
        override fun areItemsTheSame(oldItem: AnimalBacterin, newItem: AnimalBacterin): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: AnimalBacterin, newItem: AnimalBacterin): Boolean {
            return oldItem == newItem
        }
    }
) {

    var selectedBacterin: AnimalBacterin? = null
    var onItemClickListener: ((bacterin: AnimalBacterin) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilterItemViewHolder {
        return FilterItemViewHolder(parent)
    }

    override fun onBindViewHolder(holder: FilterItemViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class FilterItemViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_filter, parent, false)
    ) {
        private lateinit var bacterin: AnimalBacterin
        private val onItemViewClickListener = View.OnClickListener {
            onItemClickListener?.invoke(bacterin)
        }

        fun bind(bacterin: AnimalBacterin) {
            this.bacterin = bacterin
            itemView.tvContent.text = itemView.context.getString(bacterin.nameResourceId)
            itemView.tvContent.setTextColor(itemView.context.getColor(if (bacterin == selectedBacterin) android.R.color.white else android.R.color.black))
            itemView.setBackgroundResource(if (bacterin == selectedBacterin) R.drawable.bg_item_filter_selected else R.drawable.bg_item_filter_unselected)
            itemView.setOnClickListener(onItemViewClickListener)
        }
    }
}