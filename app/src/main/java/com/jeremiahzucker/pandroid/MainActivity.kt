package com.jeremiahzucker.pandroid

import android.net.Uri
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.os.Bundle
import android.support.v4.view.ViewPager
import com.jeremiahzucker.pandroid.play.PlayFragment
import com.jeremiahzucker.pandroid.request.model.ExpandedStationModel
import com.jeremiahzucker.pandroid.settings.SettingsFragment
import com.jeremiahzucker.pandroid.station.StationListFragment

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.toolbar_main.*

class MainActivity : AppCompatActivity(), StationListFragment.OnListFragmentInteractionListener, PlayFragment.OnFragmentInteractionListener {
    override fun onFragmentInteraction(uri: Uri) {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onListFragmentInteraction(item: ExpandedStationModel) {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
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

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }

        radio_button_station_list.isChecked = true
    }

    private fun onItemChecked(position: Int) {
        container.currentItem = position
        toolbar.title = adapter?.getPageTitle(position) ?: toolbar.title
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
