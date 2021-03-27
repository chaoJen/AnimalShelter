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
import com.mrr.animalshelter.ui.page.main.fragment.collection.CollectionDetailFragment
import com.mrr.animalshelter.ui.page.main.fragment.collection.CollectionFragment

class CollectionHostFragment : BaseFragment() {

    companion object {
        const val TAG = "TAG_FRAGMENT_COLLECTION_HOST"
        fun newInstance(): CollectionHostFragment {
            return CollectionHostFragment()
        }
    }

    private val mViewModel: MainViewModel by activityViewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_host_collection, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        observe()
    }

    private fun initView() {
        switchFragment(
            R.id.layContainer,
            CollectionFragment.TAG,
            onNewInstance = { CollectionFragment.newInstance() }
        )
    }

    private fun observe() {
        mViewModel.onLaunchCollectionAnimalDetailToPositionEvent.observe(viewLifecycleOwner, Observer { position ->
            switchFragment(
                R.id.layContainer,
                CollectionDetailFragment.TAG,
                onNewInstance = { CollectionDetailFragment.newInstance(position ?: 0) }
            )
        })
    }
}