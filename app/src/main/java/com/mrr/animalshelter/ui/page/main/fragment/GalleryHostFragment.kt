package com.mrr.animalshelter.ui.page.main.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mrr.animalshelter.R
import com.mrr.animalshelter.ktx.switchFragment
import com.mrr.animalshelter.ui.base.BaseFragment
import com.mrr.animalshelter.ui.page.main.fragment.gallery.GalleryFragment

class GalleryHostFragment : BaseFragment() {

    companion object {
        fun newInstance(): GalleryHostFragment {
            return GalleryHostFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_host, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    private fun initView() {
        switchFragment(
            R.id.layContainer,
            "Gallery",
            onNewInstance = { GalleryFragment.newInstance() }
        )
    }
}