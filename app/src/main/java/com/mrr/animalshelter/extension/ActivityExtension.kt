package com.mrr.animalshelter.extension

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment

fun AppCompatActivity.switchFragment(
    containerViewId: Int,
    tag: String,
    onPreTransaction: () -> Unit = { },
    onFragmentExisted: (existedFragment: Fragment) -> Fragment? = { existedFragment -> existedFragment },
    onNewInstance: () -> Fragment
) {
    onPreTransaction()

    supportFragmentManager.fragments.forEach {
        supportFragmentManager.beginTransaction().hide(it).commit()
    }

    val fragment = supportFragmentManager.findFragmentByTag(tag)?.run {
        onFragmentExisted(this)
    } ?: run {
        val fragment = onNewInstance()
        supportFragmentManager.beginTransaction().add(containerViewId, fragment, tag).commit()
        fragment
    }

    supportFragmentManager.beginTransaction().show(fragment).commit()
}