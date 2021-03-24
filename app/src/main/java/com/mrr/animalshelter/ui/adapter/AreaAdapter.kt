package com.mrr.animalshelter.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mrr.animalshelter.R
import com.mrr.animalshelter.data.element.AnimalArea
import kotlinx.android.synthetic.main.item_filter.view.*

class AreaAdapter : ListAdapter<AnimalArea, AreaAdapter.FilterItemViewHolder>(
    object : DiffUtil.ItemCallback<AnimalArea>() {
        override fun areItemsTheSame(oldItem: AnimalArea, newItem: AnimalArea): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: AnimalArea, newItem: AnimalArea): Boolean {
            return oldItem == newItem
        }
    }
) {

    var selectedArea: AnimalArea = AnimalArea.All
    var onItemClickListener: ((area: AnimalArea) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilterItemViewHolder {
        return FilterItemViewHolder(parent)
    }

    override fun onBindViewHolder(holder: FilterItemViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class FilterItemViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_filter, parent, false)
    ) {
        private lateinit var area: AnimalArea
        private val onItemViewClickListener = View.OnClickListener {
            onItemClickListener?.invoke(area)
        }

        fun bind(area: AnimalArea) {
            this.area = area
            itemView.tvContent.text = itemView.context.getString(area.nameResourceId)
            itemView.tvContent.setTextColor(itemView.context.getColor(if (area == selectedArea) android.R.color.white else android.R.color.black))
            itemView.setBackgroundResource(if (area == selectedArea) R.drawable.bg_item_filter_selected else R.drawable.bg_item_filter_unselected)
            itemView.setOnClickListener(onItemViewClickListener)
        }
    }
}