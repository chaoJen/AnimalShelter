package com.mrr.animalshelter.ui.page.main.fragment.collection

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.mrr.animalshelter.R
import com.mrr.animalshelter.ui.adapter.AnimalGalleryAdapter
import com.mrr.animalshelter.ui.base.BaseFragment
import com.mrr.animalshelter.ui.page.main.MainViewModel
import kotlinx.android.synthetic.main.fragment_animal_gallery.*

class CollectionFragment : BaseFragment() {

    companion object {
        const val TAG = "TAG_FRAGMENT_COLLECTION"
        fun newInstance(): CollectionFragment {
            return CollectionFragment()
        }
    }

    private val mViewModel: MainViewModel by activityViewModels()
    private var mAdapter = AnimalGalleryAdapter()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_animal_collection, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initAnimalAdapter()
        observe()
    }

    private fun initView() {
        val gridLayoutManager = GridLayoutManager(context, 3)
        rvAnimals.layoutManager = gridLayoutManager
        layRefresh.setOnRefreshListener { mViewModel.updateCollectionAnimalsData() }
    }

    private fun initAnimalAdapter() {
        rvAnimals.adapter = mAdapter.apply {
            onItemClickListener = { animal -> mViewModel.launchCollectionAnimalDetail(animal) }
            onItemLongClickListener = { animal -> mViewModel.changeAnimalCollection(animal) }
        }
    }

    private fun observe() {
        mViewModel.collectionAnimals.observe(viewLifecycleOwner, Observer { collectedAnimals ->
            layRefresh.isEnabled = collectedAnimals.isNotEmpty()
            mAdapter.submitList(collectedAnimals)
        })
        mViewModel.collectionAnimalIds.observe(viewLifecycleOwner, Observer { collectedAnimalIds ->
            mAdapter.onCollectedAnimalsChanged(collectedAnimalIds)
        })
        mViewModel.onScrollCollectionGalleryToPositionEvent.observe(viewLifecycleOwner, Observer { position ->
            position?.let { rvAnimals.scrollToPosition(if (it > 3 && it % 3 == 0) it - 1 else it) }
        })
        mViewModel.isCollectionDataPulling.observe(viewLifecycleOwner, Observer { isPulling ->
            if (!isPulling) {
                layRefresh.isRefreshing = false
            }
        })
    }
}