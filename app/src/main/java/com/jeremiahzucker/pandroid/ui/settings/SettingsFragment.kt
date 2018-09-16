package com.jeremiahzucker.pandroid.ui.settings

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jeremiahzucker.pandroid.R
import com.jeremiahzucker.pandroid.persist.Preferences
import com.jeremiahzucker.pandroid.ui.main.MainActivity
import kotlinx.android.synthetic.main.fragment_settings.*

class SettingsFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_settings, container, false)
    }

    override fun onStart() {
        super.onStart()

        settings_text.setOnClickListener {
            (activity as MainActivity).showAuth()
        }
    }

}
