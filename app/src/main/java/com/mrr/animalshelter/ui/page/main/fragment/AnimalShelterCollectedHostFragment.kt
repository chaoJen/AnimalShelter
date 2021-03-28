package com.mrr.animalshelter.ui.page.main.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.mrr.animalshelter.R
import com.mrr.animalshelter.ktx.switchFragment
import com.mrr.animalshelter.ui.base.BaseFragment
import com.mrr.animalshelter.ui.page.main.MainViewModel
import com.mrr.animalshelter.ui.page.main.fragment.collected.AnimalShelterCollectedDetailFragment
import com.mrr.animalshelter.ui.page.main.fragment.collected.AnimalShelterCollectedMainFragment

class AnimalShelterCollectedHostFragment : BaseFragment() {

    companion object {
        const val TAG = "TAG_FRAGMENT_COLLECTION_HOST"
        fun newInstance(): AnimalShelterCollectedHostFragment {
            return AnimalShelterCollectedHostFragment()
        }
    }

    private val mViewModel: MainViewModel by activityViewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_collected_host, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        observe()
    }

    private fun initView() {
        switchFragment(
            R.id.layContainer,
            AnimalShelterCollectedMainFragment.TAG,
            onNewInstance = { AnimalShelterCollectedMainFragment.newInstance() }
        )
    }

    private fun observe() {
        mViewModel.onLaunchCollectionAnimalDetailToPositionEvent.observe(viewLifecycleOwner, Observer { position ->
            switchFragment(
                R.id.layContainer,
                AnimalShelterCollectedDetailFragment.TAG,
                onNewInstance = { AnimalShelterCollectedDetailFragment.newInstance(position ?: 0) }
            )
        })
    }
}