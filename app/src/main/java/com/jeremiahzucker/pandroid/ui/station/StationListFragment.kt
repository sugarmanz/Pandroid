package com.jeremiahzucker.pandroid.ui.station

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.jeremiahzucker.pandroid.R
import com.jeremiahzucker.pandroid.request.model.ExpandedStationModel
import kotlinx.android.synthetic.main.fragment_station_list.*

/**
 * A fragment representing a list of Items.
 *
 *
 * Activities containing this fragment MUST implement the [OnListFragmentInteractionListener]
 * interface.
 */
/**
 * Mandatory empty constructor for the fragment manager to instantiate the
 * fragment (e.g. upon screen orientation changes).
 */
class StationListFragment : Fragment(), StationListContract.View {

    private val TAG: String = StationListFragment::class.java.simpleName
    private var presenter: StationListContract.Presenter = StationListPresenter() // TODO: Inject

    // TODO: Customize parameters
    private var mColumnCount = 1
    private var mListener: OnListFragmentInteractionListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (arguments != null) {
            mColumnCount = arguments.getInt(ARG_COLUMN_COUNT)
        }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater!!.inflate(R.layout.fragment_station_list, container, false)

//        presenter.getStationList()

        return view
    }


    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is OnListFragmentInteractionListener) {
            mListener = context
        } else {
            throw RuntimeException(context!!.toString() + " must implement OnListFragmentInteractionListener")
        }
    }

    override fun onResume() {
        super.onResume()
        presenter.attach(this)
        // TODO: Check station list checksum here!
    }

    override fun onDetach() {
        super.onDetach()
        mListener = null
    }

    /**
     * Shows the progress UI and hides the login form.
     */
    override fun showProgress(show: Boolean) {
        val shortAnimTime = resources.getInteger(android.R.integer.config_shortAnimTime).toLong()

        recycler_view_station_list.visibility = if (show) View.GONE else View.VISIBLE
        recycler_view_station_list.animate()
                .setDuration(shortAnimTime)
                .alpha((if (show) 0 else 1).toFloat())
                .setListener(object : AnimatorListenerAdapter() {
                    override fun onAnimationEnd(animation: Animator) {
                        recycler_view_station_list.visibility = if (show) View.GONE else View.VISIBLE
                    }
                })

        progress_bar_station_list.visibility = if (show) View.VISIBLE else View.GONE
        progress_bar_station_list.animate()
                .setDuration(shortAnimTime)
                .alpha((if (show) 1 else 0).toFloat())
                .setListener(object : AnimatorListenerAdapter() {
                    override fun onAnimationEnd(animation: Animator) {
                        progress_bar_station_list.visibility = if (show) View.VISIBLE else View.GONE
                    }
                })

        if (recycler_view_station_list.visibility == View.VISIBLE)
            setupRecyclerView() // TODO: Pretty dumb way to handle it (Should improve soon)
    }

    override fun updateStationList(stations: List<ExpandedStationModel>) {
        Log.i(TAG, stations.toString())
        (recycler_view_station_list.adapter as StationListAdapter).updateStationList(stations)
    }

    private fun setupRecyclerView() {
        val context = recycler_view_station_list.context
        if (mColumnCount <= 1) {
            recycler_view_station_list.layoutManager = LinearLayoutManager(context)
        } else {
            recycler_view_station_list.layoutManager = GridLayoutManager(context, mColumnCount)
        }
        // TODO: Setup local persistence and initially load from local storage
        recycler_view_station_list.adapter = StationListAdapter(context, listOf(), mListener)
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     *
     *
     * See the Android Training lesson [Communicating with Other Fragments](http://developer.android.com/training/basics/fragments/communicating.html) for more information.
     */
    interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
        fun onListFragmentInteraction(item: ExpandedStationModel)
    }

    companion object {

        // TODO: Customize parameter argument names
        private val ARG_COLUMN_COUNT = "column-count"

        // TODO: Customize parameter initialization
        fun newInstance(columnCount: Int): StationListFragment {
            val fragment = StationListFragment()
            val args = Bundle()
            args.putInt(ARG_COLUMN_COUNT, columnCount)
            fragment.arguments = args
            return fragment
        }
    }
}
