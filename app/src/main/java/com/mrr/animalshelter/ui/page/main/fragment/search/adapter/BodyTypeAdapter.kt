package com.mrr.animalshelter.ui.page.main.fragment.search.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mrr.animalshelter.R
import com.mrr.animalshelter.data.element.AnimalBodyType
import kotlinx.android.synthetic.main.item_filter.view.*

class BodyTypeAdapter : ListAdapter<AnimalBodyType, BodyTypeAdapter.FilterItemViewHolder>(
    object : DiffUtil.ItemCallback<AnimalBodyType>() {
        override fun areItemsTheSame(oldItem: AnimalBodyType, newItem: AnimalBodyType): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: AnimalBodyType, newItem: AnimalBodyType): Boolean {
            return oldItem == newItem
        }
    }
) {

    var selectedBodyType: AnimalBodyType? = null
    var onItemClickListener: ((bodyType: AnimalBodyType) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilterItemViewHolder {
        return FilterItemViewHolder(parent)
    }

    override fun onBindViewHolder(holder: FilterItemViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class FilterItemViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_filter, parent, false)
    ) {
        private lateinit var bodyType: AnimalBodyType
        private val onItemViewClickListener = View.OnClickListener {
            onItemClickListener?.invoke(bodyType)
        }

        fun bind(bodyType: AnimalBodyType) {
            this.bodyType = bodyType
            itemView.tvContent.text = itemView.context.getString(bodyType.nameResourceId)
            itemView.tvContent.setTextColor(itemView.context.getColor(if (bodyType == selectedBodyType) android.R.color.white else android.R.color.black))
            itemView.setBackgroundResource(if (bodyType == selectedBodyType) R.drawable.bg_item_filter_selected else R.drawable.bg_item_filter_unselected)
            itemView.setOnClickListener(onItemViewClickListener)
        }
    }
}