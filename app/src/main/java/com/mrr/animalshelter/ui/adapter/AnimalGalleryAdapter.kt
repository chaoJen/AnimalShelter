package com.mrr.animalshelter.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mrr.animalshelter.R
import com.mrr.animalshelter.data.Animal
import kotlinx.android.synthetic.main.item_animal.view.*

class AnimalGalleryAdapter : ListAdapter<Animal, AnimalGalleryAdapter.AnimalViewHolder>(
    object : DiffUtil.ItemCallback<Animal>() {
        override fun areItemsTheSame(oldItem: Animal, newItem: Animal): Boolean {
            return oldItem.animalId == newItem.animalId
        }

        override fun areContentsTheSame(oldItem: Animal, newItem: Animal): Boolean {
            return oldItem == newItem
        }
    }
) {

    var onItemClickListener: ((index: Int) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnimalViewHolder {
        return AnimalViewHolder(parent)
    }

    override fun onBindViewHolder(holder: AnimalViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class AnimalViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_animal, parent, false)
    ) {
        fun bind(animal: Animal) {
            Glide.with(itemView.context)
                .load(animal.albumFile)
                .into(itemView.ivAnimal)
        }
    }
}