package com.mrr.animalshelter.ui.page.main.fragment.collected

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mrr.animalshelter.R
import com.mrr.animalshelter.ui.adapter.AnimalShelterDetailAdapter
import com.mrr.animalshelter.ui.base.BaseFragment
import com.mrr.animalshelter.ui.page.main.MainViewModel
import kotlinx.android.synthetic.main.fragment_collected_detail.*

class AnimalShelterCollectedDetailFragment : BaseFragment() {

    companion object {
        const val TAG = "TAG_FRAGMENT_COLLECTION_ANIMAL_DETAIL"
        private const val EXTRA_KEY_START_POSITION = "EXTRA_KEY_START_POSITION"

        fun newInstance(startPosition: Int): AnimalShelterCollectedDetailFragment {
            return AnimalShelterCollectedDetailFragment().apply {
                arguments = Bundle().apply { putInt(EXTRA_KEY_START_POSITION, startPosition) }
            }
        }
    }

    private val mViewModel: MainViewModel by activityViewModels()
    private var mDetailAdapter: AnimalShelterDetailAdapter? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_collected_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        observe()
    }

    private fun initView() {
        initToolbar()

        mDetailAdapter = AnimalShelterDetailAdapter().apply {
            onCollectListener = { animal -> mViewModel.collectAnimal(animal) }
            onUnCollectListener = { animalId -> mViewModel.unCollectAnimal(animalId) }
            onBindViewHolderListener =  { position -> mViewModel.scrollAnimalShelterCollectedMainTo(position) }
        }
        recyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext()).apply {
                orientation = RecyclerView.VERTICAL
                val startPosition = arguments?.getInt(EXTRA_KEY_START_POSITION, 0) ?: 0
                scrollToPosition(startPosition)
            }
            adapter = mDetailAdapter
        }
    }
    
    private fun initToolbar() {
        toolbar.title = getString(R.string.toolbar_title_collecteddetail)
        toolbar.setNavigationIcon(R.drawable.ic_back)
        toolbar.setNavigationOnClickListener { parentFragmentManager.popBackStack() }
    }

    private fun observe() {
        mViewModel.collectedAnimals.observe(viewLifecycleOwner, Observer { collectionAnimals ->
            if (collectionAnimals.isEmpty()) {
                parentFragmentManager.popBackStack()
            } else {
                mDetailAdapter?.submitList(collectionAnimals)
            }
        })
        mViewModel.collectedAnimalIds.observe(viewLifecycleOwner, Observer { collectionAnimalIds ->
            mDetailAdapter?.onCollectedAnimalsChanged(collectionAnimalIds)
        })
    }
}