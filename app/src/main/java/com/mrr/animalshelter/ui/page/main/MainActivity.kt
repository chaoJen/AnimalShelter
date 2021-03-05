package com.mrr.animalshelter.ui.page.main

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.lifecycle.ViewModelProviders
import com.mrr.animalshelter.R
import com.mrr.animalshelter.core.AnimalRepository
import com.mrr.animalshelter.core.api.ApiManager
import com.mrr.animalshelter.ui.base.AnyViewModelFactory
import com.mrr.animalshelter.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {

    private lateinit var mViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initViewModel()
        initView()
    }

    private fun initViewModel() {
        val factory = AnyViewModelFactory {
            val service = ApiManager.getShelterService()
            val repository = AnimalRepository(service)
            MainViewModel(repository)
        }
        mViewModel = ViewModelProviders
            .of(this, factory)
            .get(MainViewModel::class.java)
    }

    private fun initView() {
        setSupportActionBar(toolbar)

        layBottomNavigation.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.itemAnimalsGallery -> {
                    // TODO 首頁
                    true
                }
                R.id.itemAnimalsTracked -> {
                    // TODO 收藏頁
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
                // TODO 開啟篩選
            }
        }
        return true
    }
}
