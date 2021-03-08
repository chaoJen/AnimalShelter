package com.mrr.animalshelter.ui.adapter

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mrr.animalshelter.R
import com.mrr.animalshelter.data.Animal
import com.mrr.animalshelter.data.AnimalDetailDescriptor
import kotlinx.android.synthetic.main.item_animal_description.view.*

class AnimalDetailAdapter : ListAdapter<Animal, AnimalDetailAdapter.AnimalDetailViewHolder>(
    object : DiffUtil.ItemCallback<Animal>() {
        override fun areItemsTheSame(oldItem: Animal, newItem: Animal): Boolean {
            return oldItem.animalId == newItem.animalId
        }

        override fun areContentsTheSame(oldItem: Animal, newItem: Animal): Boolean {
            return oldItem == newItem
        }
    }
) {

    var onBindViewHolderListener: ((position: Int) -> Unit)? = null
    private var mAllShowMoreAnimalIds = mutableListOf<Int>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnimalDetailViewHolder {
        return AnimalDetailViewHolder(parent)
    }

    override fun onBindViewHolder(holder: AnimalDetailViewHolder, position: Int) {
        onBindViewHolderListener?.invoke(position)
        holder.bind(getItem(position))
    }

    inner class AnimalDetailViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_animal, parent, false)
    ) {

        private var animalDetailDescriptor: AnimalDetailDescriptor? = null
        private val onAnimalShelterClickListener = View.OnClickListener {
            // TODO launch shelter webview
        }
        private val onAnimalLocationClickListener = View.OnClickListener {
            itemView.context.run {
                startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("geo:0,0?q=${animalDetailDescriptor?.getShelterName()} ${animalDetailDescriptor?.getShelterAddress()}")))
            }
        }
        private val onShelterTelClickListener = View.OnClickListener {
            itemView.context.run {
                startActivity(Intent(Intent.ACTION_DIAL, Uri.parse("tel:${animalDetailDescriptor?.getShelterTel()}")))
            }
        }
        private val onShowMoreClickListener = View.OnClickListener {
            animalDetailDescriptor?.getAnimalId()?.let {
                mAllShowMoreAnimalIds.add(it)
                notifyDataSetChanged()
            }
        }

        fun bind(animal: Animal) {
            animalDetailDescriptor = AnimalDetailDescriptor(animal)

            itemView.imgActionAnimalShelterWeb.setOnClickListener(onAnimalShelterClickListener)

            itemView.imgActionLocation.setOnClickListener(onAnimalLocationClickListener)
            itemView.tvTitleAnimalAreaPkId.setOnClickListener(onAnimalLocationClickListener)
            itemView.tvContentShelterName.setOnClickListener(onAnimalLocationClickListener)
            itemView.tvContentShelterAddress.setOnClickListener(onAnimalLocationClickListener)

            itemView.imgActionCall.setOnClickListener(onShelterTelClickListener)
            itemView.tvTitleShelterTel.setOnClickListener(onShelterTelClickListener)
            itemView.tvContentShelterTel.setOnClickListener(onShelterTelClickListener)

            val isShowMore = mAllShowMoreAnimalIds.contains(animal.animalId)
            itemView.tvMoreDetail.setOnClickListener(onShowMoreClickListener)
            itemView.tvMoreDetail.visibility = if (isShowMore) View.INVISIBLE else View.VISIBLE
            itemView.tvMoreDetail.isClickable = !isShowMore
            itemView.layMoreDetail.visibility = if (isShowMore) View.VISIBLE else View.GONE
        }
    }
}