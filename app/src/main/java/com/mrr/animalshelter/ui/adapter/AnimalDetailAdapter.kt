package com.mrr.animalshelter.ui.adapter

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mrr.animalshelter.R
import com.mrr.animalshelter.data.Animal
import com.mrr.animalshelter.data.AnimalDetailDescriptor
import com.mrr.animalshelter.ui.page.web.WebViewActivity
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
    var onCollectListener: ((animal: Animal) -> Unit)? = null
    var onUnCollectListener: ((animalId: Int) -> Unit)? = null
    private var mAllShowMoreAnimalIds = mutableListOf<Int>()
    private var mAllCollectedAnimalIds = listOf<Int>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnimalDetailViewHolder {
        return AnimalDetailViewHolder(parent)
    }

    override fun onBindViewHolder(holder: AnimalDetailViewHolder, position: Int) {
        onBindViewHolderListener?.invoke(position)
        holder.bind(getItem(position))
    }

    fun onCollectedAnimalsChanged(collectedAnimalIds: List<Int>) {
        mAllCollectedAnimalIds = collectedAnimalIds
        notifyDataSetChanged()
    }

    inner class AnimalDetailViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_animal_description, parent, false)
    ) {

        private lateinit var animal: Animal
        private lateinit var animalDetailDescriptor: AnimalDetailDescriptor
        private val onAnimalShelterClickListener = View.OnClickListener {
            itemView.context?.run {
                startActivity(WebViewActivity.getStartActivityIntent(this, animalDetailDescriptor.getAdoptionWebPageUrl()))
            }
        }
        private val onAnimalLocationClickListener = View.OnClickListener {
            itemView.context.run {
                startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("geo:0,0?q=${animalDetailDescriptor.getShelterName()} ${animalDetailDescriptor.getShelterAddress()}")))
            }
        }
        private val onShelterTelClickListener = View.OnClickListener {
            itemView.context.run {
                startActivity(Intent(Intent.ACTION_DIAL, Uri.parse("tel:${animalDetailDescriptor.getShelterTel()}")))
            }
        }
        private val onCollectionClickListener = View.OnClickListener {
            if (mAllCollectedAnimalIds.contains(animal.animalId)) {
                onUnCollectListener?.invoke(animal.animalId)
            } else {
                onCollectListener?.invoke(animal)
            }
        }
        private val onShowMoreClickListener = View.OnClickListener {
            animalDetailDescriptor.getAnimalId().let {
                mAllShowMoreAnimalIds.add(it)
                notifyDataSetChanged()
            }
        }

        fun bind(animal: Animal) {
            this.animal = animal
            animalDetailDescriptor = AnimalDetailDescriptor(animal)

            Glide.with(itemView.context)
                .load(animalDetailDescriptor.getAlbumFile())
                .fitCenter()
                .into(itemView.imgAnimal)

            animalDetailDescriptor.getAnimalArea()?.badgeNameResourceId?.let { itemView.tvTopperShelterBadge.setText(it) }
            itemView.tvTopperShelterName.text = animalDetailDescriptor.getShelterName()
            itemView.tvTopperShelterAddress.text = animalDetailDescriptor.getShelterAddress()
            itemView.tvContentAnimalSex.setText(animalDetailDescriptor.getAnimalSexResourceId())
            itemView.tvContentAnimalAge.setText(animalDetailDescriptor.getAnimalAgeResourceId())
            itemView.tvContentAnimalBodyType.setText(animalDetailDescriptor.getAnimalBodyTypeResourceId())
            itemView.tvContentAnimalColour.text = animalDetailDescriptor.getAnimalColour()
            itemView.tvContentAnimalRemark.visibility = if (animalDetailDescriptor.getAnimalRemark().isNotBlank()) View.VISIBLE else View.GONE
            itemView.tvContentAnimalRemark.text = animalDetailDescriptor.getAnimalRemark()
            itemView.tvContentShelterTel.text = animalDetailDescriptor.getShelterTel()
            itemView.tvContentAnimalFoundPlace.text = animalDetailDescriptor.getAnimalFoundPlace()
            itemView.tvContentAnimalBacterin.setText(animalDetailDescriptor.getAnimalBacterinResourceId())
            itemView.tvContentAnimalSterilization.setText(animalDetailDescriptor.getAnimalSterilizationResourceId())
            itemView.tvContentAnimalId.text = "${animalDetailDescriptor.getAnimalId()}"
            itemView.tvContentAnimalSubId.text = animalDetailDescriptor.getAnimalSubId()
            itemView.tvDate.text = animalDetailDescriptor.getDayDiff(itemView.context, "yyyy/MM/dd")
            itemView.imgActionCollection.setImageResource(if (mAllCollectedAnimalIds.contains(animal.animalId)) R.drawable.ic_action_collection_fill else R.drawable.ic_action_collection_outline)

            itemView.imgActionAnimalShelterWeb.setOnClickListener(onAnimalShelterClickListener)
            itemView.tvTopperShelterBadge.setOnClickListener(onAnimalLocationClickListener)
            itemView.tvTopperShelterName.setOnClickListener(onAnimalLocationClickListener)
            itemView.tvTopperShelterAddress.setOnClickListener(onAnimalLocationClickListener)
            itemView.imgActionLocation.setOnClickListener(onAnimalLocationClickListener)
            itemView.imgActionCall.setOnClickListener(onShelterTelClickListener)
            itemView.tvMoreDetail.setOnClickListener(onShowMoreClickListener)
            itemView.tvTitleShelterTel.setOnClickListener(onShelterTelClickListener)
            itemView.tvContentShelterTel.setOnClickListener(onShelterTelClickListener)
            itemView.imgActionCollection.setOnClickListener(onCollectionClickListener)

            val isShowMore = mAllShowMoreAnimalIds.contains(animal.animalId)
            itemView.tvMoreDetail.visibility = if (isShowMore) View.INVISIBLE else View.VISIBLE
            itemView.tvMoreDetail.isClickable = !isShowMore
            itemView.layMoreDetail.visibility = if (isShowMore) View.VISIBLE else View.GONE
        }
    }
}