package com.mrr.animalshelter.ui.adapter

import android.view.LayoutInflater
import android.view.View
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

    var onItemClickListener: ((position: Int) -> Unit)? = null
    private var mAllCollectedAnimalIds = listOf<Int>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnimalViewHolder {
        return AnimalViewHolder(parent)
    }

    override fun onBindViewHolder(holder: AnimalViewHolder, position: Int) {
        holder.bind(position)
    }

    fun onCollectedAnimalsChanged(collectedAnimalIds: List<Int>) {
        mAllCollectedAnimalIds = collectedAnimalIds
        notifyDataSetChanged()
    }

    inner class AnimalViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_animal, parent, false)
    ) {
        private var itemPosition: Int = 0
        private val onItemClickListener = View.OnClickListener {
            this@AnimalGalleryAdapter.onItemClickListener?.invoke(itemPosition)
        }

        fun bind(position: Int) {
            itemPosition = position
            itemView.setOnClickListener(onItemClickListener)
            itemView.ivCollection.visibility = if (mAllCollectedAnimalIds.contains(getItem(position).animalId)) View.VISIBLE else View.GONE
            Glide.with(itemView.context)
                .load(getItem(position).albumFile)
                .into(itemView.ivAnimal)
        }
    }
}