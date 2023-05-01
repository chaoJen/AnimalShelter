package com.mrr.animalshelter.ui.page.main.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.mrr.animalshelter.R
import com.mrr.animalshelter.ktx.switchFragment
import com.mrr.animalshelter.core.base.BaseFragment
import com.mrr.animalshelter.ui.page.main.MainViewModel
import com.mrr.animalshelter.ui.page.main.fragment.search.AnimalShelterSearchDetailFragment
import com.mrr.animalshelter.ui.page.main.fragment.search.AnimalShelterSearchFilterFragment
import com.mrr.animalshelter.ui.page.main.fragment.search.AnimalShelterSearchMainFragment

class AnimalShelterSearchHostFragment : BaseFragment() {

    companion object {
        const val TAG = "TAG_FRAGMENT_ANIMAL_SHELTER_SEARCH_HOST"
        fun newInstance(): AnimalShelterSearchHostFragment {
            return AnimalShelterSearchHostFragment()
        }
    }

    private val mViewModel: MainViewModel by activityViewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_search_host, container, false)
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
            AnimalShelterSearchMainFragment.TAG,
            onNewInstance = { AnimalShelterSearchMainFragment.newInstance() }
        )
    }

    private fun observe() {
        mViewModel.onLaunchAnimalShelterSearchDetailToPositionEvent.observe(viewLifecycleOwner, Observer { position ->
            switchFragment(
                R.id.layContainer,
                AnimalShelterSearchDetailFragment.TAG,
                onNewInstance = { AnimalShelterSearchDetailFragment.newInstance(position ?: 0) }
            )
        })
        mViewModel.onLaunchSearchFilterEvent.observe(viewLifecycleOwner, Observer {
            switchFragment(
                R.id.layContainer,
                AnimalShelterSearchFilterFragment.TAG,
                onNewInstance = { AnimalShelterSearchFilterFragment.newInstance() }
            )
        })
    }
}