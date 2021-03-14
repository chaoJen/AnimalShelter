package com.mrr.animalshelter.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mrr.animalshelter.R
import com.mrr.animalshelter.data.element.AnimalArea
import kotlinx.android.synthetic.main.item_bottomsheet.view.*

class AreaAdapter : ListAdapter<AnimalArea, AreaAdapter.AreaViewHolder>(
    object : DiffUtil.ItemCallback<AnimalArea>() {
        override fun areItemsTheSame(oldItem: AnimalArea, newItem: AnimalArea): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: AnimalArea, newItem: AnimalArea): Boolean {
            return oldItem == newItem
        }
    }
) {

    var onItemClickListener: ((area: AnimalArea) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AreaViewHolder {
        return AreaViewHolder(parent)
    }

    override fun onBindViewHolder(holder: AreaViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class AreaViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_bottomsheet, parent, false)
    ) {
        private lateinit var animalArea: AnimalArea
        private val onItemClickListener = View.OnClickListener {
            this@AreaAdapter.onItemClickListener?.invoke(animalArea)
        }

        fun bind(area: AnimalArea) {
            this.animalArea = area
            itemView.tvContent.setText(area.nameResourceId)
            itemView.setOnClickListener(onItemClickListener)
        }
    }
}