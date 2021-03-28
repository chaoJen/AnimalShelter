package com.mrr.animalshelter.ui.page.main.fragment.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mrr.animalshelter.R
import com.mrr.animalshelter.data.AnimalFilter
import com.mrr.animalshelter.ui.adapter.AnimalShelterDetailAdapter
import com.mrr.animalshelter.ui.base.BaseFragment
import com.mrr.animalshelter.ui.page.main.MainViewModel
import kotlinx.android.synthetic.main.fragment_search_detail.*

class AnimalShelterSearchDetailFragment : BaseFragment() {

    companion object {
        const val TAG = "TAG_FRAGMENT_ANIMAL_SHELTER_SEARCH_DETAIL"
        private const val EXTRA_KEY_START_POSITION = "EXTRA_KEY_START_POSITION"

        fun newInstance(startPosition: Int): AnimalShelterSearchDetailFragment {
            return AnimalShelterSearchDetailFragment().apply {
                arguments = Bundle().apply { putInt(EXTRA_KEY_START_POSITION, startPosition) }
            }
        }
    }

    private val mViewModel: MainViewModel by activityViewModels()
    private var mDetailAdapter: AnimalShelterDetailAdapter? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_search_detail, container, false)
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
            onBindViewHolderListener =  { position ->
                if (position >= itemCount - 5) {
                    mViewModel.pullAnimals()
                }
                mViewModel.scrollAnimalShelterSearchMainTo(position)
            }
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
        toolbar.title = getString(R.string.toolbar_title_searchdetail)
        toolbar.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.item_filter -> mViewModel.launchSearchFilter()
            }
            true
        }
        toolbar.setNavigationIcon(R.drawable.ic_back)
        toolbar.setNavigationOnClickListener { parentFragmentManager.popBackStack() }
    }

    private fun observe() {
        mViewModel.animals.observe(viewLifecycleOwner, Observer { animals ->
            mDetailAdapter?.submitList(animals)
        })
        mViewModel.collectedAnimalIds.observe(viewLifecycleOwner, Observer { collectionAnimalIds ->
            mDetailAdapter?.onCollectedAnimalsChanged(collectionAnimalIds)
        })
        mViewModel.animalFilter.observe(viewLifecycleOwner, Observer { filter ->
            toolbar.menu.findItem(R.id.item_filter).apply {
                context?.run {
                    setIcon(icon.apply { setTint(getColor(if (filter != AnimalFilter()) R.color.colorAccent else android.R.color.black)) })
                }
            }
        })
    }
}