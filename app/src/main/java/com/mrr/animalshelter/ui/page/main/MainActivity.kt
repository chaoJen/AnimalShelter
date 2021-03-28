package com.mrr.animalshelter.ui.page.main

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.google.android.material.snackbar.Snackbar
import com.mrr.animalshelter.R
import com.mrr.animalshelter.core.AnimalRepository
import com.mrr.animalshelter.core.api.ApiManager
import com.mrr.animalshelter.core.const.PreferencesConst
import com.mrr.animalshelter.core.db.AppDatabase
import com.mrr.animalshelter.data.AnimalFilter
import com.mrr.animalshelter.ktx.getPreference
import com.mrr.animalshelter.ktx.putPreference
import com.mrr.animalshelter.ktx.switchFragment
import com.mrr.animalshelter.ui.base.AnyViewModelFactory
import com.mrr.animalshelter.ui.base.BaseActivity
import com.mrr.animalshelter.ui.page.main.fragment.AnimalShelterCollectedHostFragment
import com.mrr.animalshelter.ui.page.main.fragment.AnimalShelterSearchHostFragment
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : BaseActivity() {

    private var mViewModel: MainViewModel? = null
    private var mCurrentFragment: Fragment? = null
    private var mBackPressedTimeMillisecond: Long = 0

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
            val dao = AppDatabase.getInstance(this).getAnimalDao()
            val repository = AnimalRepository(service, dao)
            val filter = getPreference(PreferencesConst.NAME_ANIMAL, PreferencesConst.KEY_FILTER, AnimalFilter::class.java)
            MainViewModel(repository, filter ?: AnimalFilter())
        }
        mViewModel = ViewModelProviders.of(this, factory).get(MainViewModel::class.java)
    }

    private fun initView() {
        layBottomNavigation.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.itemAnimalShelterSearch -> {
                    onSwitchAnimalShelterSearchFragment()
                    true
                }
                R.id.itemAnimalShelterCollected -> {
                    onSwitchAnimalShelterCollectedFragment()
                    true
                }
                else -> false
            }
        }

        layBottomNavigation.selectedItemId = R.id.itemAnimalShelterSearch
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_search, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return true
    }

    private fun onSwitchAnimalShelterSearchFragment() {
        mCurrentFragment = switchFragment(
            R.id.layContainer,
            AnimalShelterSearchHostFragment.TAG,
            onNewInstance = { AnimalShelterSearchHostFragment.newInstance() },
            onFragmentAlreadyVisible = { fragment ->
                if (fragment.childFragmentManager.backStackEntryCount == 1) {
                    mViewModel?.scrollAnimalShelterSearchMainTo(0)
                } else {
                    fragment.childFragmentManager.popBackStack(null, 0)
                }
            }
        )
    }

    private fun onSwitchAnimalShelterCollectedFragment() {
        mCurrentFragment = switchFragment(
            R.id.layContainer,
            AnimalShelterCollectedHostFragment.TAG,
            onNewInstance = { AnimalShelterCollectedHostFragment.newInstance() },
            onFragmentAlreadyVisible = { fragment ->
                if (fragment.childFragmentManager.backStackEntryCount == 1) {
                    mViewModel?.scrollAnimalShelterCollectedMainTo(0)
                } else {
                    fragment.childFragmentManager.popBackStack(null, 0)
                }
            }
        )
    }

    private fun observe() {
        mViewModel?.exception?.observe(this, Observer { exception ->
            // TODO exception handling
        })
        mViewModel?.animalFilter?.observe(this, Observer { filter ->
            putPreference(PreferencesConst.NAME_ANIMAL, PreferencesConst.KEY_FILTER, filter)
        })
        mViewModel?.collectedAnimals?.observe(this, Observer { collectionAnimals ->
            layBottomNavigation.getOrCreateBadge(R.id.itemAnimalShelterCollected).apply {
                isVisible = collectionAnimals.isNotEmpty()
                number = collectionAnimals.size
            }
        })
    }

    override fun onBackPressed() {
        if (mCurrentFragment?.childFragmentManager?.backStackEntryCount ?: 1 > 1) {
            mCurrentFragment?.childFragmentManager?.popBackStack()
        } else {
            if (Date().time - mBackPressedTimeMillisecond > 3000) {
                Snackbar.make(layBottomNavigation, R.string.main_snackbar_message_backpressedagain, Snackbar.LENGTH_SHORT).show()
                mBackPressedTimeMillisecond = Date().time
            } else {
                super.onBackPressed()
            }
        }
    }
}
