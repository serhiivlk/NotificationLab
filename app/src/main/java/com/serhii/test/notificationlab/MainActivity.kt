package com.serhii.test.notificationlab

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

private const val EXTRA_SECTION_NUMBER = "EXTRA_SECTION_NUMBER"

fun Context.mainIntentFromSection(sectionNumber: Int) =
        Intent(this, MainActivity::class.java).apply {
            putExtra(EXTRA_SECTION_NUMBER, sectionNumber)
        }

class MainActivity : AppCompatActivity(), SectionFragment.OnSectionInteractionListener {

    private val sectionsPagerAdapter by lazy { SectionsPagerAdapter(supportFragmentManager) }

    private val scrollTime by lazy { resources.getInteger(android.R.integer.config_shortAnimTime) }

    private val sectionNotifications by lazy { SectionNotifications(applicationContext) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        container.adapter = sectionsPagerAdapter

        processingIntent(intent)
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        processingIntent(intent)
    }

    override fun onDestroy() {
        sectionNotifications.cancelAllNotifications()
        super.onDestroy()
    }

    override fun addSection() {
        sectionsPagerAdapter.addSection()
        sectionsPagerAdapter.notifyDataSetChanged()

        val count = sectionsPagerAdapter.count
        container.setCurrentItem(count - 1, true)
    }

    private fun processingIntent(intent: Intent?) {
        val section = intent?.takeIf { it.extras?.containsKey(EXTRA_SECTION_NUMBER) ?: false }
                ?.getIntExtra(EXTRA_SECTION_NUMBER, -1) ?: return
        val position = section - 1
        if (position in 0..sectionsPagerAdapter.count) {
            container.setCurrentItem(position, true)
        }
    }

    override fun removeSection(number: Int) {
        // scroll to previous section
        val count = sectionsPagerAdapter.count
        if (number in 2..count + 1) {
            container.setCurrentItem(number - 2, true)
        }

        sectionNotifications.cancelNotificationFromSection(number)

        // remove section after scrolling
        container.postDelayed({
            sectionsPagerAdapter.removeSectionsAfterPosition(number - 1)
            sectionsPagerAdapter.notifyDataSetChanged()
        }, scrollTime.toLong())
    }

    override fun createNotification(number: Int) {
        sectionNotifications.createNotificationFromSection(number)
    }
}
