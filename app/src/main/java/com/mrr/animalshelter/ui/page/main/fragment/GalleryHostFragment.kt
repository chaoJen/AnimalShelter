package com.mrr.animalshelter.ui.page.main.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.mrr.animalshelter.R
import com.mrr.animalshelter.data.AnimalFilter
import com.mrr.animalshelter.data.element.AnimalArea
import com.mrr.animalshelter.data.element.AnimalKind
import com.mrr.animalshelter.data.element.AnimalSex
import com.mrr.animalshelter.data.element.AnimalShelter
import com.mrr.animalshelter.ktx.switchFragment
import com.mrr.animalshelter.ui.adapter.AreaAdapter
import com.mrr.animalshelter.ui.adapter.ShelterAdapter
import com.mrr.animalshelter.ui.base.BaseFragment
import com.mrr.animalshelter.ui.page.main.MainViewModel
import com.mrr.animalshelter.ui.page.main.fragment.gallery.AnimalDetailFragment
import com.mrr.animalshelter.ui.page.main.fragment.gallery.GalleryFragment
import kotlinx.android.synthetic.main.fragment_host_gallery.*
import kotlinx.android.synthetic.main.include_bottomsheet_area.view.*

class GalleryHostFragment : BaseFragment(), View.OnClickListener {

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

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.layFilterArea -> showAreaBottomSheet()
            R.id.layFilterShelter -> mViewModel.showFilterBottomSheetShelter()
            R.id.layFilterAnimalKind -> mViewModel.changeFilterAnimalKind()
            R.id.layFilterAnimalSex -> mViewModel.changeFilterAnimalSex()
            R.id.layFilterAnimalAge -> mViewModel.changeFilterAnimalAge()
            R.id.layFilterAnimalBodyType -> mViewModel.changeFilterAnimalBodyType()
        }
    }

    private fun initView() {
        switchFragment(
            R.id.layContainer,
            GalleryFragment.TAG,
            onNewInstance = { GalleryFragment.newInstance() }
        )
        layFilterArea.setOnClickListener(this)
        layFilterShelter.setOnClickListener(this)
        layFilterAnimalKind.setOnClickListener(this)
        layFilterAnimalSex.setOnClickListener(this)
        layFilterAnimalAge.setOnClickListener(this)
        layFilterAnimalBodyType.setOnClickListener(this)
        layRefresh.setOnRefreshListener { mViewModel.resetAnimals() }
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
            updateFilterBar(filter)
        })
        mViewModel.isGalleryDataPulling.observe(viewLifecycleOwner, Observer { isPulling ->
            if (!isPulling) {
                layRefresh.isRefreshing = false
            }
        })
        mViewModel.onShowFilterBottomSheetShelterEvent.observe(viewLifecycleOwner, Observer { shelters ->
            shelters?.let { showShelterBottomSheet(it) }
        })
    }

    private fun updateFilterBar(filter: AnimalFilter) {
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

        layFilterShelter.isClickable = AnimalShelter.find(filter.area).size > 1
    }

    private fun showAreaBottomSheet() = context?.run {
        BottomSheetDialog(this).apply{
            val dialogView = layoutInflater.inflate(R.layout.include_bottomsheet_area, null)
            dialogView.rvFilterArea.layoutManager = GridLayoutManager(this@run, 3)
            dialogView.rvFilterArea.adapter = AreaAdapter().apply {
                onItemClickListener = { area ->
                    dismiss()
                    mViewModel.changeFilterAnimalArea(area)
                }
                submitList(AnimalArea.values().toList())
            }
            setContentView(dialogView)
            dismissWithAnimation = true
        }.show()
    }

    private fun showShelterBottomSheet(shelters: List<AnimalShelter>) = context?.run {
        BottomSheetDialog(this).apply{
            val dialogView = layoutInflater.inflate(R.layout.include_bottomsheet_shelter, null)
            dialogView.rvFilterArea.layoutManager = LinearLayoutManager(this@run)
            dialogView.rvFilterArea.adapter = ShelterAdapter().apply {
                onItemClickListener = { shelter ->
                    dismiss()
                    mViewModel.changeFilterAnimalShelter(shelter)
                }
                submitList(shelters)
            }
            setContentView(dialogView)
            dismissWithAnimation = true
        }.show()
    }
}