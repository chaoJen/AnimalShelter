package com.mrr.animalshelter.ktx

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment

fun AppCompatActivity.switchFragment(
    containerViewId: Int,
    tag: String,
    fragment: Fragment
) {
    supportFragmentManager.fragments.forEach {
        supportFragmentManager.beginTransaction().hide(it).commit()
    }

    supportFragmentManager.findFragmentByTag(tag)?.let {
        supportFragmentManager.beginTransaction().show(fragment).commit()
    } ?: run {
        supportFragmentManager.beginTransaction().add(containerViewId, fragment, tag).commit()
    }
}