package com.mrr.animalshelter.ui.page.main

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.google.android.material.snackbar.Snackbar
import com.mrr.animalshelter.R
import com.mrr.animalshelter.core.AnimalRepository
import com.mrr.animalshelter.core.api.ApiManager
import com.mrr.animalshelter.ktx.switchFragment
import com.mrr.animalshelter.ui.base.AnyViewModelFactory
import com.mrr.animalshelter.ui.base.BaseActivity
import com.mrr.animalshelter.ui.page.main.fragment.GalleryFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {

    private var mViewModel: MainViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initViewModel()
        initView()
        observe()
    }

    private fun initViewModel() {
        val factory = AnyViewModelFactory {
            val service = ApiManager.getShelterService()
            val repository = AnimalRepository(service)
            MainViewModel(repository)
        }
        mViewModel = ViewModelProviders.of(this, factory).get(MainViewModel::class.java)
    }

    private fun initView() {
        setSupportActionBar(toolbar)

        layBottomNavigation.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.itemAnimalsGallery -> {
                    switchFragment(R.id.layContainer, "Gallery", GalleryFragment.newInstance())
                    true
                }
                R.id.itemAnimalsCollection -> {
                    // TODO launch collection page
                    true
                }
                else -> false
            }
        }

        layBottomNavigation.selectedItemId = R.id.itemAnimalsGallery
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.item_filter -> {
                // TODO launch filter setting
            }
        }
        return true
    }

    private fun observe() {
        mViewModel?.error?.observe(this, Observer { error ->
            // TODO show error message snackbar
        })
        mViewModel?.isNoMoreData?.observe(this, Observer { isNoMore ->
            if (isNoMore) {
                Snackbar.make(layBottomNavigation, R.string.main_snackbar_message_nomoredata, Snackbar.LENGTH_INDEFINITE)
            }
        })
    }
}
