package com.mrr.animalshelter.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mrr.animalshelter.R
import com.mrr.animalshelter.data.element.AnimalColour
import kotlinx.android.synthetic.main.item_filter.view.*

class ColourAdapter : ListAdapter<AnimalColour, ColourAdapter.FilterItemViewHolder>(
    object : DiffUtil.ItemCallback<AnimalColour>() {
        override fun areItemsTheSame(oldItem: AnimalColour, newItem: AnimalColour): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: AnimalColour, newItem: AnimalColour): Boolean {
            return oldItem == newItem
        }
    }
) {

    var selectedColour: AnimalColour? = null
    var onItemClickListener: ((colour: AnimalColour) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilterItemViewHolder {
        return FilterItemViewHolder(parent)
    }

    override fun onBindViewHolder(holder: FilterItemViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class FilterItemViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_filter, parent, false)
    ) {
        private lateinit var colour: AnimalColour
        private val onItemViewClickListener = View.OnClickListener {
            onItemClickListener?.invoke(colour)
        }

        fun bind(colour: AnimalColour) {
            this.colour = colour
            itemView.tvContent.text = itemView.context.getString(colour.nameResourceId)
            itemView.tvContent.setTextColor(itemView.context.getColor(if (colour == selectedColour) android.R.color.white else android.R.color.black))
            itemView.setBackgroundResource(if (colour == selectedColour) R.drawable.bg_item_filter_selected else R.drawable.bg_item_filter_unselected)
            itemView.setOnClickListener(onItemViewClickListener)
        }
    }
}