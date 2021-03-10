package com.mrr.animalshelter.ktx

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit

fun AppCompatActivity.switchFragment(
    containerViewId: Int,
    tag: String,
    onNewInstance: () -> Fragment,
    onFragmentAlreadyVisible: ((fragment: Fragment) -> Unit)? = null
): Fragment {
    val existedFragment = supportFragmentManager.findFragmentByTag(tag)
    if (existedFragment?.isVisible == true) {
        onFragmentAlreadyVisible?.invoke(existedFragment)
        return existedFragment
    }
    val fragment = existedFragment ?: onNewInstance()
    supportFragmentManager.commit {
        replace(containerViewId, fragment, tag)
    }
    return fragment
}