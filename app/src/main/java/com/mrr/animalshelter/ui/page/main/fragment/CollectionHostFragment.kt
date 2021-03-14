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
import com.mrr.animalshelter.ui.page.main.fragment.collection.CollectionAnimalDetailFragment
import com.mrr.animalshelter.ui.page.main.fragment.collection.CollectionFragment
import kotlinx.android.synthetic.main.fragment_host_collection.*

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
        layRefresh.setOnRefreshListener { mViewModel.updateCollectionAnimalsData() }
    }

    private fun observe() {
        mViewModel.collectionAnimals.observe(viewLifecycleOwner, Observer { collectedAnimals ->
            layRefresh.isEnabled = collectedAnimals.isNotEmpty()
        })
        mViewModel.isCollectionDataPulling.observe(viewLifecycleOwner, Observer { isPulling ->
            if (!isPulling) {
                layRefresh.isRefreshing = false
            }
        })
        mViewModel.onLaunchCollectionAnimalDetailToPositionEvent.observe(viewLifecycleOwner, Observer { position ->
            switchFragment(
                R.id.layContainer,
                CollectionAnimalDetailFragment.TAG,
                onNewInstance = { CollectionAnimalDetailFragment.newInstance(position ?: 0) }
            )
        })
        mViewModel.onBackCollectionAnimalDetailEvent.observe(viewLifecycleOwner, Observer {
            val existedFragment = childFragmentManager.findFragmentByTag(CollectionAnimalDetailFragment.TAG)
            if (existedFragment?.isVisible == true) {
                childFragmentManager.popBackStack()
            }
        })
    }
}