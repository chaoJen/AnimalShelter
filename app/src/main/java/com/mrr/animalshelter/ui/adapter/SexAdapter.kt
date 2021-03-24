package com.mrr.animalshelter.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mrr.animalshelter.R
import com.mrr.animalshelter.data.element.AnimalSex
import kotlinx.android.synthetic.main.item_filter.view.*

class SexAdapter : ListAdapter<AnimalSex, SexAdapter.FilterItemViewHolder>(
    object : DiffUtil.ItemCallback<AnimalSex>() {
        override fun areItemsTheSame(oldItem: AnimalSex, newItem: AnimalSex): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: AnimalSex, newItem: AnimalSex): Boolean {
            return oldItem == newItem
        }
    }
) {

    var selectedSex: AnimalSex? = null
    var onItemClickListener: ((sex: AnimalSex) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilterItemViewHolder {
        return FilterItemViewHolder(parent)
    }

    override fun onBindViewHolder(holder: FilterItemViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class FilterItemViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_filter, parent, false)
    ) {
        private lateinit var sex: AnimalSex
        private val onItemViewClickListener = View.OnClickListener {
            onItemClickListener?.invoke(sex)
        }

        fun bind(sex: AnimalSex) {
            this.sex = sex
            itemView.tvContent.text = itemView.context.getString(sex.nameResourceId)
            itemView.tvContent.setTextColor(itemView.context.getColor(if (sex == selectedSex) android.R.color.white else android.R.color.black))
            itemView.setBackgroundResource(if (sex == selectedSex) R.drawable.bg_item_filter_selected else R.drawable.bg_item_filter_unselected)
            itemView.setOnClickListener(onItemViewClickListener)
        }
    }
}