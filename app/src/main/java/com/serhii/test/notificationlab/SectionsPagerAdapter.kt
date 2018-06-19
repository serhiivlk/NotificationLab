package com.serhii.test.notificationlab

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter

class SectionsPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {
    private val fragments: MutableList<Fragment> =
            arrayListOf(SectionFragment.newInstance(1))

    override fun getItem(position: Int) = fragments[position]

    override fun getCount() = fragments.size

    fun addSection() {
        fragments.add(SectionFragment.newInstance(count + 1))
    }
}