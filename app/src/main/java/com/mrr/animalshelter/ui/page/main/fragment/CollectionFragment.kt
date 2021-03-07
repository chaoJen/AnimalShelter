package com.mrr.animalshelter.ui.page.main.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mrr.animalshelter.R
import com.mrr.animalshelter.ui.base.BaseFragment
import com.mrr.animalshelter.ui.base.Scrollable

class CollectionFragment : BaseFragment(), Scrollable {

    companion object {
        val TAG = CollectionFragment::class.java.simpleName
        fun newInstance(): CollectionFragment {
            return CollectionFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_animal_collection, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onScrollToPosition(index: Int) {
        // TODO scroll to position
    }
}