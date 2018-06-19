package com.serhii.test.notificationlab

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.view.PagerAdapter

class SectionsPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {
    private val fragments: MutableList<Fragment> =
            arrayListOf(SectionFragment.newInstance(1))

    override fun getItem(position: Int) = fragments[position]

    override fun getCount() = fragments.size

    override fun getItemPosition(`object`: Any) = PagerAdapter.POSITION_NONE

    fun addSection() {
        fragments.add(SectionFragment.newInstance(count + 1))
    }

    fun removeSectionsAfterPosition(position: Int) {
        while (fragments.size > position) {
            fragments.removeAt(fragments.size - 1)
        }
    }
}