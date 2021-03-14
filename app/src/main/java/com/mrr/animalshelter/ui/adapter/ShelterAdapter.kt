package com.mrr.animalshelter.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mrr.animalshelter.R
import com.mrr.animalshelter.data.element.AnimalShelter
import kotlinx.android.synthetic.main.item_bottomsheet.view.*

class ShelterAdapter : ListAdapter<AnimalShelter, ShelterAdapter.ShelterViewHolder>(
    object : DiffUtil.ItemCallback<AnimalShelter>() {
        override fun areItemsTheSame(oldItem: AnimalShelter, newItem: AnimalShelter): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: AnimalShelter, newItem: AnimalShelter): Boolean {
            return oldItem == newItem
        }
    }
) {

    var onItemClickListener: ((shelter: AnimalShelter) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShelterViewHolder {
        return ShelterViewHolder(parent)
    }

    override fun onBindViewHolder(holder: ShelterViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ShelterViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_bottomsheet, parent, false)
    ) {
        private lateinit var animalShelter: AnimalShelter
        private val onItemClickListener = View.OnClickListener {
            this@ShelterAdapter.onItemClickListener?.invoke(animalShelter)
        }

        fun bind(shelter: AnimalShelter) {
            this.animalShelter = shelter
            itemView.tvContent.setText(shelter.nameResourceId)
            itemView.setOnClickListener(onItemClickListener)
        }
    }
}