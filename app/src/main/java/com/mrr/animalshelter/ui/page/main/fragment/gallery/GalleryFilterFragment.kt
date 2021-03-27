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
import com.mrr.animalshelter.data.element.*
import com.mrr.animalshelter.ui.adapter.*
import com.mrr.animalshelter.ui.base.BaseFragment
import com.mrr.animalshelter.ui.page.main.MainViewModel
import kotlinx.android.synthetic.main.fragment_galleryfilter.*

class GalleryFilterFragment : BaseFragment() {

    companion object {
        const val TAG = "TAG_FRAGMENT_GALLERY_FILTER"
        fun newInstance() = GalleryFilterFragment()
    }

    private val mViewModel: MainViewModel by activityViewModels()
    private val mAreaAdapter = AreaAdapter().apply { submitList(AnimalArea.values().toList()) }
    private val mShelterAdapter = ShelterAdapter()
    private val mKindAdapter = KindAdapter().apply { submitList(AnimalKind.values().toList()) }
    private val mSexAdapter = SexAdapter().apply { submitList(AnimalSex.values().toList()) }
    private val mAgeAdapter = AgeAdapter().apply { submitList(AnimalAge.values().toList()) }
    private val mBodyTypeAdapter = BodyTypeAdapter().apply { submitList(AnimalBodyType.values().toList()) }
    private val mColourAdapter = ColourAdapter().apply { submitList(AnimalColour.values().toList()) }
    private val mBacterinAdapter = BacterinAdapter().apply { submitList(AnimalBacterin.values().toList()) }
    private val mSterilizationAdapter = SterilizationAdapter().apply { submitList(AnimalSterilization.values().toList()) }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_galleryfilter, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        observe()
    }

    private fun initView() {
        initToolbar()

        rvFilterArea.apply {
            layoutManager = GridLayoutManager(requireContext(), 3)
            adapter = mAreaAdapter
            mAreaAdapter.onItemClickListener = { mViewModel.filterArea(it) }
        }
        rvFilterShelter.apply {
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            adapter = mShelterAdapter
            mShelterAdapter.onItemClickListener = { mViewModel.filterShelter(it) }
        }
        rvFilterKind.apply {
            layoutManager = GridLayoutManager(requireContext(), 4)
            adapter = mKindAdapter
            mKindAdapter.onItemClickListener = { mViewModel.filterKind(it) }
        }
        rvFilterSex.apply {
            layoutManager = GridLayoutManager(requireContext(), 3)
            adapter = mSexAdapter
            mSexAdapter.onItemClickListener = { mViewModel.filterSex(it) }
        }
        rvFilterAge.apply {
            layoutManager = GridLayoutManager(requireContext(), 3)
            adapter = mAgeAdapter
            mAgeAdapter.onItemClickListener = { mViewModel.filterAge(it) }
        }
        rvFilterBodyType.apply {
            layoutManager = GridLayoutManager(requireContext(), 4)
            adapter = mBodyTypeAdapter
            mBodyTypeAdapter.onItemClickListener = { mViewModel.filterBodyType(it) }
        }
        rvFilterColour.apply {
            layoutManager = GridLayoutManager(requireContext(), 4)
            adapter = mColourAdapter
            mColourAdapter.onItemClickListener = { mViewModel.filterColour(it) }
        }
        rvFilterBacterin.apply {
            layoutManager = GridLayoutManager(requireContext(), 3)
            adapter = mBacterinAdapter
            mBacterinAdapter.onItemClickListener = { mViewModel.filterBacterin(it) }
        }
        rvFilterSterilization.apply {
            layoutManager = GridLayoutManager(requireContext(), 3)
            adapter = mSterilizationAdapter
            mSterilizationAdapter.onItemClickListener = { mViewModel.filterSterilization(it) }
        }
    }

    private fun initToolbar() {
        toolbar.title = getString(R.string.toolbar_title_galleryfilter)
        toolbar.inflateMenu(R.menu.menu_filter)
        toolbar.setOnMenuItemClickListener { item ->
            if (item.itemId == R.id.item_reset) {
                mViewModel.resetFilter()
            }
            true
        }
        toolbar.setNavigationIcon(R.drawable.ic_back)
        toolbar.setNavigationOnClickListener { parentFragmentManager.popBackStack() }
    }

    private fun observe() {
        mViewModel.animalFilter.observe(viewLifecycleOwner, Observer { filter ->
            if (mAreaAdapter.selectedArea != filter.area) {
                mAreaAdapter.apply {
                    selectedArea = filter.area
                    notifyDataSetChanged()
                }

                val shelters = AnimalShelter.find(filter.area)
                mShelterAdapter.submitList(shelters)
                rvFilterShelter.visibility = if (shelters.isNotEmpty()) View.VISIBLE else View.GONE
            }
            mShelterAdapter.apply {
                selectedShelter = filter.shelter
                notifyDataSetChanged()
            }
            mKindAdapter.apply {
                selectedKind = filter.kind
                notifyDataSetChanged()
            }
            mSexAdapter.apply {
                selectedSex = filter.sex
                notifyDataSetChanged()
            }
            mAgeAdapter.apply {
                selectedAge = filter.age
                notifyDataSetChanged()
            }
            mBodyTypeAdapter.apply {
                selectedBodyType = filter.bodyType
                notifyDataSetChanged()
            }
            mColourAdapter.apply {
                selectedColour = filter.colour
                notifyDataSetChanged()
            }
            mBacterinAdapter.apply {
                selectedBacterin = filter.bacterin
                notifyDataSetChanged()
            }
            mSterilizationAdapter.apply {
                selectedSterilization = filter.sterilization
                notifyDataSetChanged()
            }
        })
    }
}