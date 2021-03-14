package com.mrr.animalshelter.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mrr.animalshelter.R
import com.mrr.animalshelter.data.element.AnimalColour
import kotlinx.android.synthetic.main.item_bottomsheet.view.*

class ColourAdapter : ListAdapter<AnimalColour, ColourAdapter.ColourViewHolder>(
    object : DiffUtil.ItemCallback<AnimalColour>() {
        override fun areItemsTheSame(oldItem: AnimalColour, newItem: AnimalColour): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: AnimalColour, newItem: AnimalColour): Boolean {
            return oldItem == newItem
        }
    }
) {

    var onItemClickListener: ((colour: AnimalColour) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ColourViewHolder {
        return ColourViewHolder(parent)
    }

    override fun onBindViewHolder(holder: ColourViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ColourViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_bottomsheet, parent, false)
    ) {
        private lateinit var animalColour: AnimalColour
        private val onItemClickListener = View.OnClickListener {
            this@ColourAdapter.onItemClickListener?.invoke(animalColour)
        }

        fun bind(colour: AnimalColour) {
            this.animalColour = colour
            itemView.tvContent.setText(colour.nameResourceId)
            itemView.setOnClickListener(onItemClickListener)
        }
    }
}