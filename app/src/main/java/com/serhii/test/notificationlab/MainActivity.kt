package com.serhii.test.notificationlab

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), SectionFragment.OnSectionInteractionListener {

    private val sectionsPagerAdapter by lazy { SectionsPagerAdapter(supportFragmentManager) }

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

    override fun removeSection() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
