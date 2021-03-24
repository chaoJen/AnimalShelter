package com.mrr.animalshelter.ui.page.main.fragment.gallery

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.mrr.animalshelter.R
import com.mrr.animalshelter.data.element.AnimalArea
import com.mrr.animalshelter.data.element.AnimalKind
import com.mrr.animalshelter.data.element.AnimalShelter
import com.mrr.animalshelter.ui.adapter.AnimalKindAdapter
import com.mrr.animalshelter.ui.adapter.AreaAdapter
import com.mrr.animalshelter.ui.adapter.ShelterAdapter
import com.mrr.animalshelter.ui.base.BaseFragment
import com.mrr.animalshelter.ui.page.main.MainViewModel
import kotlinx.android.synthetic.main.fragment_gallery_filter.*
import kotlinx.android.synthetic.main.toolbar.*

class GalleryFilterFragment : BaseFragment() {

    companion object {
        const val TAG = "TAG_FRAGMENT_GALLERY_FILTER"
        fun newInstance() = GalleryFilterFragment()
    }

    private val mViewModel: MainViewModel by activityViewModels()
    private val mAreaAdapter = AreaAdapter().apply { submitList(AnimalArea.values().toList()) }
    private val mShelterAdapter = ShelterAdapter()
    private val mKindAdapter = AnimalKindAdapter().apply { submitList(AnimalKind.values().toList()) }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_gallery_filter, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        observe()
    }

    private fun initView() {
        toolbar.title = getString(R.string.toolbar_title_galleryfilter)
        rvFilterArea.apply {
            layoutManager = GridLayoutManager(requireContext(), 3)
            adapter = mAreaAdapter
            mAreaAdapter.onItemClickListener = { mViewModel.changeFilterAnimalArea(it) }
        }
        rvFilterShelter.apply {
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            adapter = mShelterAdapter
            mShelterAdapter.onItemClickListener = { mViewModel.changeFilterAnimalShelter(it) }
        }
        rvFilterKind.apply {
            layoutManager = GridLayoutManager(requireContext(), 4)
            adapter = mKindAdapter
            mKindAdapter.onItemClickListener = { mViewModel.changeFilterAnimalKind(it) }
        }
    }

    private fun observe() {
        mViewModel.animalFilter.observe(viewLifecycleOwner, Observer { filter ->
            if (mAreaAdapter.selectedArea != filter.area) {
                mAreaAdapter.selectedArea = filter.area
                mAreaAdapter.notifyDataSetChanged()

                val shelters = AnimalShelter.find(filter.area)
                mShelterAdapter.submitList(shelters)
                rvFilterShelter.visibility = if (shelters.isNotEmpty()) View.VISIBLE else View.GONE
            }
            mShelterAdapter.selectedShelter = filter.shelter
            mShelterAdapter.notifyDataSetChanged()

            mKindAdapter.selectedKind = filter.kind
            mKindAdapter.notifyDataSetChanged()
        })
    }
}