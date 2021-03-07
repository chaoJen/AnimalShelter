package com.mrr.animalshelter.ui.page.main.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mrr.animalshelter.R
import com.mrr.animalshelter.data.AnimalFilter
import com.mrr.animalshelter.ui.adapter.AnimalGalleryAdapter
import com.mrr.animalshelter.ui.base.BaseFragment
import com.mrr.animalshelter.ui.base.Scrollable
import com.mrr.animalshelter.ui.page.main.MainViewModel
import kotlinx.android.synthetic.main.fragment_animal_gallery.*

class GalleryFragment : BaseFragment(), Scrollable {

    companion object {
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
        mAdapter.onItemClickListener = { index ->
            // TODO launch animal detail
        }
        rvAnimals.adapter = mAdapter
    }

    private fun observe() {
        mViewModel.animals.observe(viewLifecycleOwner, Observer {
            mAdapter.submitList(it)
        })
        mViewModel.isLoading.observe(viewLifecycleOwner, Observer { isLoading ->
            if (!isLoading) {
                layRefresh.isRefreshing = false
            }
        })
    }

    override fun onScrollToPosition(index: Int) {
        if (viewLifecycleOwner.lifecycle.currentState.isAtLeast(Lifecycle.State.STARTED)) {
            rvAnimals.scrollToPosition(index)
        }
    }
}