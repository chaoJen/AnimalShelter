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
import com.mrr.animalshelter.ui.page.main.fragment.gallery.AnimalDetailFragment
import com.mrr.animalshelter.ui.page.main.fragment.gallery.GalleryFragment

class GalleryHostFragment : BaseFragment() {

    companion object {
        private const val TAG_FRAGMENT_GALLERY = "TAG_FRAGMENT_GALLERY"
        private const val TAG_FRAGMENT_DETAIL = "TAG_FRAGMENT_DETAIL"
        fun newInstance(): GalleryHostFragment {
            return GalleryHostFragment()
        }
    }

    private val mViewModel: MainViewModel by activityViewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_host, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        observe()
    }

    private fun initView() {
        switchFragment(
            R.id.layContainer,
            TAG_FRAGMENT_GALLERY,
            onNewInstance = { GalleryFragment.newInstance() }
        )
    }

    private fun observe() {
        mViewModel.onLaunchAnimalDetailToPositionEvent.observe(viewLifecycleOwner, Observer { position ->
            switchFragment(
                R.id.layContainer,
                TAG_FRAGMENT_DETAIL,
                onNewInstance = { AnimalDetailFragment.newInstance(position ?: 0) }
            )
        })
    }
}