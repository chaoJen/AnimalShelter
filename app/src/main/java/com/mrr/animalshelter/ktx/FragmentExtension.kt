package com.mrr.animalshelter.ktx

import androidx.fragment.app.Fragment
import androidx.fragment.app.commit

fun Fragment.switchFragment(
    containerViewId: Int,
    tag: String,
    onNewInstance: () -> Fragment,
    onFragmentAlreadyVisible: ((fragment: Fragment) -> Unit)? = null
) {
    val existedFragment = childFragmentManager.findFragmentByTag(tag)
    if (existedFragment?.isVisible == true) {
        onFragmentAlreadyVisible?.invoke(existedFragment)
        return
    }

    val fragment = existedFragment ?: run {
        val fragment = onNewInstance()
        childFragmentManager.commit {
            addToBackStack(null)
            add(containerViewId, fragment, tag)
        }
        fragment
    }
    childFragmentManager.beginTransaction().show(fragment).commit()
}