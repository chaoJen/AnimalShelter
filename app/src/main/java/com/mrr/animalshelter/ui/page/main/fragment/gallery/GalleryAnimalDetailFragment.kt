package com.mrr.animalshelter.ui.page.main.fragment.gallery

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
import kotlinx.android.synthetic.main.fragment_animal_detail.*
import kotlinx.android.synthetic.main.toolbar_shelter.*

class GalleryAnimalDetailFragment : BaseFragment() {

    companion object {
        const val TAG = "TAG_FRAGMENT_GALLERY_ANIMAL_DETAIL"
        private const val EXTRA_KEY_START_POSITION = "EXTRA_KEY_START_POSITION"

        fun newInstance(startPosition: Int): GalleryAnimalDetailFragment {
            return GalleryAnimalDetailFragment().apply {
                arguments = Bundle().apply { putInt(EXTRA_KEY_START_POSITION, startPosition) }
            }
        }
    }

    private val mViewModel: MainViewModel by activityViewModels()
    private var mAnimalDetailAdapter: AnimalDetailAdapter? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_animal_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        observe()
    }

    private fun initView() {
        toolbar.title = getString(R.string.toolbar_title_gallery)
        toolbar.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.item_filter -> {
                    // TODO launch filter
                }
            }
            true
        }

        mAnimalDetailAdapter = AnimalDetailAdapter().apply {
            onCollectListener = { animal -> mViewModel.collectAnimal(animal) }
            onUnCollectListener = { animalId -> mViewModel.unCollectAnimal(animalId) }
            onBindViewHolderListener =  { position ->
                if (position >= itemCount - 5) {
                    mViewModel.pullAnimals()
                }
                mViewModel.scrollGallery(position)
            }
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

    private fun observe() {
        mViewModel.animals.observe(viewLifecycleOwner, Observer { animals ->
            mAnimalDetailAdapter?.submitList(animals)
        })
        mViewModel.collectionAnimalIds.observe(viewLifecycleOwner, Observer { collectionAnimalIds ->
            mAnimalDetailAdapter?.onCollectedAnimalsChanged(collectionAnimalIds)
        })
    }
}