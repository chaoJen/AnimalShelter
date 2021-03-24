package com.mrr.animalshelter.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mrr.animalshelter.R
import com.mrr.animalshelter.data.element.AnimalKind
import kotlinx.android.synthetic.main.item_filter.view.*

class AnimalKindAdapter : ListAdapter<AnimalKind, AnimalKindAdapter.FilterItemViewHolder>(
    object : DiffUtil.ItemCallback<AnimalKind>() {
        override fun areItemsTheSame(oldItem: AnimalKind, newItem: AnimalKind): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: AnimalKind, newItem: AnimalKind): Boolean {
            return oldItem == newItem
        }
    }
) {

    var selectedKind: AnimalKind? = null
    var onItemClickListener: ((kind: AnimalKind) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilterItemViewHolder {
        return FilterItemViewHolder(parent)
    }

    override fun onBindViewHolder(holder: FilterItemViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class FilterItemViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_filter, parent, false)
    ) {
        private lateinit var kind: AnimalKind
        private val onItemViewClickListener = View.OnClickListener {
            onItemClickListener?.invoke(kind)
        }

        fun bind(kind: AnimalKind) {
            this.kind = kind
            itemView.tvContent.text = itemView.context.getString(kind.nameResourceId)
            itemView.tvContent.setTextColor(itemView.context.getColor(if (kind == selectedKind) android.R.color.white else android.R.color.black))
            itemView.setBackgroundResource(if (kind == selectedKind) R.drawable.bg_item_filter_selected else R.drawable.bg_item_filter_unselected)
            itemView.setOnClickListener(onItemViewClickListener)
        }
    }
}