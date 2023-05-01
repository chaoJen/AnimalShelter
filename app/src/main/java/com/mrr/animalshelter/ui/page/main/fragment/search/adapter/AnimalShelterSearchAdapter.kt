package com.mrr.animalshelter.ui.page.main.fragment.search.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mrr.animalshelter.R
import com.mrr.animalshelter.data.Animal
import com.mrr.animalshelter.ktx.loadImage
import kotlinx.android.synthetic.main.item_animal_gallery.view.*

class AnimalShelterSearchAdapter : ListAdapter<Animal, AnimalShelterSearchAdapter.AnimalViewHolder>(
    object : DiffUtil.ItemCallback<Animal>() {
        override fun areItemsTheSame(oldItem: Animal, newItem: Animal): Boolean {
            return oldItem.animalId == newItem.animalId
        }

        override fun areContentsTheSame(oldItem: Animal, newItem: Animal): Boolean {
            return oldItem == newItem
        }
    }
) {

    var onItemClickListener: ((animal: Animal) -> Unit)? = null
    var onItemLongClickListener: ((animal: Animal) -> Unit)? = null
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
        LayoutInflater.from(parent.context).inflate(R.layout.item_animal_gallery, parent, false)
    ) {
        private lateinit var animal: Animal
        private val onClickListener = View.OnClickListener {
            this@AnimalShelterSearchAdapter.onItemClickListener?.invoke(animal)
        }
        private val onLongClickListener = View.OnLongClickListener {
            this@AnimalShelterSearchAdapter.onItemLongClickListener?.invoke(animal)
            true
        }

        fun bind(position: Int) {
            animal = getItem(position)
            itemView.setOnClickListener(onClickListener)
            itemView.setOnLongClickListener(onLongClickListener)
            itemView.ivCollection.visibility = if (mAllCollectedAnimalIds.contains(animal.animalId)) View.VISIBLE else View.GONE
            itemView.context.loadImage(
                animal.albumFile,
                itemView.ivAnimal
            )
        }
    }
}