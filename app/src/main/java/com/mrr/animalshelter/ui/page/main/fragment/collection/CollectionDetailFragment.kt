package com.mrr.animalshelter.ui.page.main.fragment.collection

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mrr.animalshelter.R
import com.mrr.animalshelter.ui.adapter.AnimalDetailAdapter
import com.mrr.animalshelter.ui.base.BaseFragment
import com.mrr.animalshelter.ui.page.main.MainViewModel
import kotlinx.android.synthetic.main.fragment_collectiondetail.*

class CollectionDetailFragment : BaseFragment() {

    companion object {
        const val TAG = "TAG_FRAGMENT_COLLECTION_ANIMAL_DETAIL"
        private const val EXTRA_KEY_START_POSITION = "EXTRA_KEY_START_POSITION"

        fun newInstance(startPosition: Int): CollectionDetailFragment {
            return CollectionDetailFragment().apply {
                arguments = Bundle().apply { putInt(EXTRA_KEY_START_POSITION, startPosition) }
            }
        }
    }

    private val mViewModel: MainViewModel by activityViewModels()
    private var mAnimalDetailAdapter: AnimalDetailAdapter? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_collectiondetail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        observe()
    }

    private fun initView() {
        initToolbar()

        mAnimalDetailAdapter = AnimalDetailAdapter().apply {
            onCollectListener = { animal -> mViewModel.collectAnimal(animal) }
            onUnCollectListener = { animalId -> mViewModel.unCollectAnimal(animalId) }
            onBindViewHolderListener =  { position -> mViewModel.scrollCollection(position) }
        }
        recyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext()).apply {
                orientation = RecyclerView.VERTICAL
                val startPosition = arguments?.getInt(EXTRA_KEY_START_POSITION, 0) ?: 0
                scrollToPosition(startPosition)
            }
            adapter = mAnimalDetailAdapter
        }
    }
    
    private fun initToolbar() {
        toolbar.title = getString(R.string.toolbar_title_collectiondetail)
        toolbar.setNavigationIcon(R.drawable.ic_back)
        toolbar.setNavigationOnClickListener { parentFragmentManager.popBackStack() }
    }

    private fun observe() {
        mViewModel.collectionAnimals.observe(viewLifecycleOwner, Observer { collectionAnimals ->
            if (collectionAnimals.isEmpty()) {
                parentFragmentManager.popBackStack()
            } else {
                mAnimalDetailAdapter?.submitList(collectionAnimals)
            }
        })
        mViewModel.collectionAnimalIds.observe(viewLifecycleOwner, Observer { collectionAnimalIds ->
            mAnimalDetailAdapter?.onCollectedAnimalsChanged(collectionAnimalIds)
        })
    }
}