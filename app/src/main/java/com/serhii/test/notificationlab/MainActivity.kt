package com.serhii.test.notificationlab

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), SectionFragment.OnSectionInteractionListener {

    private val sectionsPagerAdapter by lazy { SectionsPagerAdapter(supportFragmentManager) }

    private val scrollTime by lazy { resources.getInteger(android.R.integer.config_shortAnimTime) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        container.adapter = sectionsPagerAdapter
    }

    override fun addSection() {
        sectionsPagerAdapter.addSection()
        sectionsPagerAdapter.notifyDataSetChanged()

        val count = sectionsPagerAdapter.count
        container.setCurrentItem(count - 1, true)
    }

    override fun removeSection(number: Int) {
        // scroll to previous section
        val count = sectionsPagerAdapter.count
        if (number in 2..count + 1) {
            container.setCurrentItem(number - 2, true)
        }
        // remove section after scrolling
        container.postDelayed({
            sectionsPagerAdapter.removeSectionsAfterPosition(number - 1)
            sectionsPagerAdapter.notifyDataSetChanged()
        }, scrollTime.toLong())
    }
}
