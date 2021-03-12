package com.mrr.animalshelter.ui.page.main.fragment.gallery

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mrr.animalshelter.R
import com.mrr.animalshelter.data.AnimalFilter
import com.mrr.animalshelter.ui.adapter.AnimalGalleryAdapter
import com.mrr.animalshelter.ui.base.BaseFragment
import com.mrr.animalshelter.ui.page.main.MainViewModel
import kotlinx.android.synthetic.main.fragment_animal_gallery.*

class GalleryFragment : BaseFragment() {

    companion object {
        const val TAG = "TAG_FRAGMENT_GALLERY"
        fun newInstance(): GalleryFragment {
            return GalleryFragment()
        }
    }

    private val mViewModel: MainViewModel by activityViewModels()
    private var mAdapter = AnimalGalleryAdapter()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_animal_gallery, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initAnimalAdapter()
        observe()
        mViewModel.pullAnimals(AnimalFilter())
    }

    private fun initView() {
        val gridLayoutManager = GridLayoutManager(context, 3)
        rvAnimals.layoutManager = gridLayoutManager
        rvAnimals.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val lastVisibleItemPosition: Int = gridLayoutManager.findLastVisibleItemPosition()
                if (lastVisibleItemPosition >= gridLayoutManager.itemCount - lastVisibleItemPosition) {
                    mViewModel.pullAnimals(AnimalFilter())
                }
            }
        })
        layRefresh.setOnRefreshListener {
            mViewModel.resetAnimals(AnimalFilter())
        }
    }

    private fun initAnimalAdapter() {
        mAdapter.onItemClickListener = { animal ->
            mViewModel.launchGalleryAnimalDetail(animal)
        }
        rvAnimals.adapter = mAdapter
    }

    private fun observe() {
        mViewModel.animals.observe(viewLifecycleOwner, Observer {
            mAdapter.submitList(it)
        })
        mViewModel.collectedAnimalIds.observe(viewLifecycleOwner, Observer { collectedAnimalIds ->
            mAdapter.onCollectedAnimalsChanged(collectedAnimalIds)
        })
        mViewModel.isAnimalLoading.observe(viewLifecycleOwner, Observer { isLoading ->
            if (!isLoading) {
                layRefresh.isRefreshing = false
            }
        })
        mViewModel.onScrollGalleryToPositionEvent.observe(viewLifecycleOwner, Observer { position ->
            position?.let { rvAnimals.scrollToPosition(if (it > 3 && it % 3 == 0) it - 1 else it) }
        })
    }
}