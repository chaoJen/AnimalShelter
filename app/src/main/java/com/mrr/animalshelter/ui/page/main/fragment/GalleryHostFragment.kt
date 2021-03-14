package com.mrr.animalshelter.ui.page.main.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.mrr.animalshelter.R
import com.mrr.animalshelter.data.element.AnimalKind
import com.mrr.animalshelter.data.element.AnimalSex
import com.mrr.animalshelter.ktx.switchFragment
import com.mrr.animalshelter.ui.base.BaseFragment
import com.mrr.animalshelter.ui.page.main.MainViewModel
import com.mrr.animalshelter.ui.page.main.fragment.gallery.AnimalDetailFragment
import com.mrr.animalshelter.ui.page.main.fragment.gallery.GalleryFragment
import kotlinx.android.synthetic.main.fragment_host_gallery.*

class GalleryHostFragment : BaseFragment() {

    companion object {
        const val TAG = "TAG_FRAGMENT_GALLERY_HOST"
        fun newInstance(): GalleryHostFragment {
            return GalleryHostFragment()
        }
    }

    private val mViewModel: MainViewModel by activityViewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_host_gallery, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        observe()
        mViewModel.updateCollectionAnimalsData()
    }

    private fun initView() {
        switchFragment(
            R.id.layContainer,
            GalleryFragment.TAG,
            onNewInstance = { GalleryFragment.newInstance() }
        )
    }

    private fun observe() {
        mViewModel.onLaunchGalleryAnimalDetailToPositionEvent.observe(viewLifecycleOwner, Observer { position ->
            switchFragment(
                R.id.layContainer,
                AnimalDetailFragment.TAG,
                onNewInstance = { AnimalDetailFragment.newInstance(position ?: 0) }
            )
        })
        mViewModel.animalFilter.observe(viewLifecycleOwner, Observer { filter ->
            tvFilterArea.setText(filter.area.nameResourceId)
            tvFilterShelter.setText(filter.shelter.nameResourceId)
            ivFilterAnimalKind.setImageResource(
                when (filter.kind) {
                    AnimalKind.All -> R.drawable.ic_animal_all
                    AnimalKind.Cat -> R.drawable.ic_animal_cat
                    AnimalKind.Dog -> R.drawable.ic_animal_dog
                    AnimalKind.Other -> R.drawable.ic_animal_giraffe
                }
            )
            tvFilterAnimalKind.setText(filter.kind.nameResourceId)
            ivFilterAnimalSexMale.visibility = if (filter.sex != AnimalSex.Female) View.VISIBLE else View.GONE
            ivFilterAnimalSexFemale.visibility = if (filter.sex != AnimalSex.Male) View.VISIBLE else View.GONE
            tvFilterAnimalAge.setText(filter.age.nameResourceId)
            tvFilterAnimalBodyType.setText(filter.bodyType.nameResourceId)
            tvFilterAnimalColour.setText(filter.colour.nameResourceId)
            tvFilterAnimalBacterin.setText(filter.bacterin.nameResourceId)
            tvFilterAnimalSterilization.setText(filter.sterilization.nameResourceId)
        })
    }
}