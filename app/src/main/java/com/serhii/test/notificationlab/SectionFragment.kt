package com.serhii.test.notificationlab

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_section.view.*

class SectionFragment : Fragment() {

    private val sectionNumber by lazy { arguments?.getInt(ARG_SECTION_NUMBER) ?: -1 }

    private lateinit var listener: OnSectionInteractionListener

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        listener = context as? OnSectionInteractionListener
                ?: throw RuntimeException("$context must implement OnSectionInteractionListener")
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment_section, container, false)

        rootView.section_label.text = "$sectionNumber"

        // hide remove button for first section
        if (sectionNumber == 1) {
            rootView.remove_button.hide()
        }

        rootView.add_button.setOnClickListener { listener.addSection() }
        rootView.remove_button.setOnClickListener { listener.removeSection() }

        return rootView
    }

    interface OnSectionInteractionListener {
        fun addSection()
        fun removeSection()
    }

    companion object {
        private const val ARG_SECTION_NUMBER = "section_number"

        fun newInstance(sectionNumber: Int) = SectionFragment().apply {
            arguments = Bundle().apply {
                putInt(ARG_SECTION_NUMBER, sectionNumber)
            }
        }
    }
}