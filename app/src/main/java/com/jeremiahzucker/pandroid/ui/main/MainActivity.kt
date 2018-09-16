package com.jeremiahzucker.pandroid.ui.main

import android.content.Intent
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.os.Bundle
import android.support.v4.view.ViewPager
import com.jeremiahzucker.pandroid.persist.Preferences
import com.jeremiahzucker.pandroid.R
import com.jeremiahzucker.pandroid.ui.play.PlayFragment
import com.jeremiahzucker.pandroid.request.json.v5.model.ExpandedStationModel
import com.jeremiahzucker.pandroid.ui.auth.AuthActivity
import com.jeremiahzucker.pandroid.ui.settings.SettingsFragment
import com.jeremiahzucker.pandroid.ui.base.BaseActivity
import com.jeremiahzucker.pandroid.ui.play.PlayPresenter
import com.jeremiahzucker.pandroid.ui.station.StationListFragment

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.toolbar_main.*

class MainActivity : BaseActivity(), MainContract.View, StationListFragment.OnListFragmentInteractionListener {

    override fun onListFragmentInteraction(item: ExpandedStationModel) {
        showPlayer(item)
    }

    private val TAG: String = MainActivity::class.java.simpleName
    private var adapter: MainPagerAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        // Setup fragments
        val fragments = listOf(
                StationListFragment(),
                PlayFragment(),
                SettingsFragment()
        )
        val titles = listOf(
                "Station List",
                "Play",
                "Settings"
        )
        val buttons = listOf(
                radio_button_station_list,
                radio_button_play,
                radio_button_settings
        )

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        adapter = MainPagerAdapter(supportFragmentManager, fragments, titles)

        // Set up the ViewPager with the sections adapter.
        container.adapter = adapter
        container.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {}
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {}
            override fun onPageSelected(position: Int) {
                buttons[position].isChecked = true
            }
        })

        buttons.forEach {
            it.setOnCheckedChangeListener {
                buttonView, isChecked -> if (isChecked) onItemChecked(buttons.indexOf(buttonView))
            }
        }

        radio_button_station_list.isChecked = true
    }

    private fun onItemChecked(position: Int) {
        container.currentItem = position
        toolbar.title = adapter?.getPageTitle(position) ?: toolbar.title
    }

    override fun showStationList() {
        radio_button_station_list.isChecked = true
    }

    override fun showPlayer(station: ExpandedStationModel?) {
        radio_button_play.isChecked = true

        if (station != null) {
            (adapter?.getItem(1) as PlayFragment).setStation(station)
        }
    }

    override fun showSettings() {
        radio_button_settings.isChecked = true
    }

    override fun showAuth() {
        Preferences.reset()

        PlayPresenter.unbindPlayerService()

        val intent = Intent(this, AuthActivity::class.java)
        startActivity(intent) // should probs use no history
        finish()
    }

    inner class MainPagerAdapter(
            fm: FragmentManager,
            private val fragments: List<Fragment>,
            private val titles: List<String>
    ) : FragmentPagerAdapter(fm) {
        override fun getItem(position: Int) = fragments[position]
        override fun getPageTitle(position: Int) = titles[position]
        override fun getCount() = titles.size
    }

}
