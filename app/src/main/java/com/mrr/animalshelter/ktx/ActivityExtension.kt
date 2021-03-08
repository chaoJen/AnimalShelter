package com.mrr.animalshelter.ktx

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment

fun AppCompatActivity.switchFragment(
    containerViewId: Int,
    tag: String,
    onNewInstance: () -> Fragment,
    onFragmentAlreadyVisible: ((fragment: Fragment) -> Unit)? = null
) {
    val existedFragment = supportFragmentManager.findFragmentByTag(tag)
    if (existedFragment?.isVisible == true) {
        onFragmentAlreadyVisible?.invoke(existedFragment)
        return
    }

    supportFragmentManager.fragments.forEach {
        supportFragmentManager.beginTransaction().hide(it).commit()
    }
    val fragment = existedFragment ?: run {
        val fragment = onNewInstance()
        supportFragmentManager.beginTransaction().add(containerViewId, fragment, tag).commit()
        fragment
    }
    supportFragmentManager.beginTransaction().show(fragment).commit()
}