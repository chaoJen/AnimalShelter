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
import com.mrr.animalshelter.data.AnimalFilter
import com.mrr.animalshelter.ui.adapter.AnimalDetailAdapter
import com.mrr.animalshelter.ui.base.BaseFragment
import com.mrr.animalshelter.ui.page.main.MainViewModel
import kotlinx.android.synthetic.main.fragment_animal_detail.*

class AnimalDetailFragment : BaseFragment() {

    companion object {
        val TAG = AnimalDetailFragment::class.java.simpleName

        fun newInstance(): AnimalDetailFragment {
            return AnimalDetailFragment()
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
        mAnimalDetailAdapter = AnimalDetailAdapter().apply {
            onBindViewHolderListener =  { position ->
                if (position >= itemCount - 5) {
                    mViewModel.pullAnimals(AnimalFilter())
                }
                mViewModel.scrollGallery(position)
            }
        }
        recyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext()).apply {
                orientation = RecyclerView.VERTICAL
            }
            adapter = mAnimalDetailAdapter
        }
    }

    private fun observe() {
        mViewModel.animals.observe(viewLifecycleOwner, Observer {
            mAnimalDetailAdapter?.submitList(it)
        })
    }
}