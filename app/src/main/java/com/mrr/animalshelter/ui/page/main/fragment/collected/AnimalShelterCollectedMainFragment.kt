package com.mrr.animalshelter.ui.page.main.fragment.collected

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.mrr.animalshelter.R
import com.mrr.animalshelter.core.base.BaseFragment
import com.mrr.animalshelter.ui.page.main.MainViewModel
import com.mrr.animalshelter.ui.page.main.fragment.collected.adapter.AnimalShelterCollectedAdapter
import kotlinx.android.synthetic.main.fragment_collected_main.*

class AnimalShelterCollectedMainFragment : BaseFragment() {

    companion object {
        const val TAG = "TAG_FRAGMENT_COLLECTION"
        fun newInstance(): AnimalShelterCollectedMainFragment {
            return AnimalShelterCollectedMainFragment()
        }
    }

    private val mViewModel: MainViewModel by activityViewModels()
    private var mAdapter = AnimalShelterCollectedAdapter()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_collected_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initAnimalAdapter()
        observe()
    }

    private fun initView() {
        initToolbar()

        val gridLayoutManager = GridLayoutManager(context, 3)
        rvAnimals.layoutManager = gridLayoutManager
    }

    private fun initToolbar() {
        toolbar.title = getString(R.string.toolbar_title_collected)
    }

    private fun initAnimalAdapter() {
        rvAnimals.adapter = mAdapter.apply {
            onItemClickListener = { animal -> mViewModel.launchAnimalShelterCollectedDetail(animal) }
            onItemLongClickListener = { animal -> mViewModel.changeAnimalCollection(animal) }
        }
    }

    private fun observe() {
        mViewModel.collectedAnimals.observe(viewLifecycleOwner, Observer { collectedAnimals ->
            mAdapter.submitList(collectedAnimals)
        })
        mViewModel.collectedAnimalIds.observe(viewLifecycleOwner, Observer { collectedAnimalIds ->
            mAdapter.onCollectedAnimalsChanged(collectedAnimalIds)
        })
        mViewModel.onScrollAnimalShelterCollectedToPositionEvent.observe(viewLifecycleOwner, Observer { position ->
            position?.let { rvAnimals.scrollToPosition(if (it > 3 && it % 3 == 0) it - 1 else it) }
        })
    }
}