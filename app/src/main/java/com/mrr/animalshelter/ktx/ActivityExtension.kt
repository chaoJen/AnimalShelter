package com.mrr.animalshelter.ktx

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment

fun AppCompatActivity.switchFragment(
    containerViewId: Int,
    tag: String,
    onNewInstance: () -> Fragment,
    onFragmentAlreadyVisible: ((fragment: Fragment) -> Unit)? = null
) {
    supportFragmentManager.fragments.forEach {
        supportFragmentManager.beginTransaction().hide(it).commit()
    }

    val fragment = supportFragmentManager.findFragmentByTag(tag)?.run {
        onFragmentAlreadyVisible?.invoke(this)
        this
    } ?: run {
        val fragment = onNewInstance()
        supportFragmentManager.beginTransaction().add(containerViewId, fragment, tag).commit()
        fragment
    }

    supportFragmentManager.beginTransaction().show(fragment).commit()
}