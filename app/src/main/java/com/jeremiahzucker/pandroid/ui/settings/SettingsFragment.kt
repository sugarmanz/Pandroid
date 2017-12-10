package com.jeremiahzucker.pandroid.ui.settings

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jeremiahzucker.pandroid.R
import com.jeremiahzucker.pandroid.player.Player
import com.jeremiahzucker.pandroid.ui.main.MainActivity
import kotlinx.android.synthetic.main.fragment_settings.*

class SettingsFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater!!.inflate(R.layout.fragment_settings, container, false)

        view.findViewById(R.id.settings_text).setOnClickListener {
            Player
            (activity as MainActivity).showAuth()
        }

        return view
    }

}
