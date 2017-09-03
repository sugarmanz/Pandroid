package com.jeremiahzucker.pandroid.station

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.jeremiahzucker.pandroid.R
import com.jeremiahzucker.pandroid.request.model.ExpandedStationModel

import com.jeremiahzucker.pandroid.station.StationListFragment.OnListFragmentInteractionListener

/**
 * [RecyclerView.Adapter] that can display a [DummyItem] and makes a call to the
 * specified [OnListFragmentInteractionListener].
 * TODO: Replace the implementation with code for your data type.
 */
class StationRecyclerViewAdapter(private var stations: List<ExpandedStationModel>, private val mListener: OnListFragmentInteractionListener?) : RecyclerView.Adapter<StationRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.fragment_station, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.mItem = stations[position]
        holder.mIdView.text = stations[position].stationId
        holder.mContentView.text = stations[position].stationName

        holder.mView.setOnClickListener {
            mListener?.onListFragmentInteraction(holder.mItem!!)
        }
    }

    override fun getItemCount(): Int {
        return stations.size
    }

    fun updateStationList(stations: List<ExpandedStationModel>) {
        this.stations = stations
        notifyDataSetChanged()
    }

    inner class ViewHolder(val mView: View) : RecyclerView.ViewHolder(mView) {
        val mIdView: TextView
        val mContentView: TextView
        var mItem: ExpandedStationModel? = null

        init {
            mIdView = mView.findViewById(R.id.id) as TextView
            mContentView = mView.findViewById(R.id.content) as TextView
        }

        override fun toString(): String {
            return super.toString() + " '" + mContentView.text + "'"
        }
    }
}
