package com.jeremiahzucker.pandroid.ui.station

import android.content.Context
import android.support.v7.widget.AppCompatImageView
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.jeremiahzucker.pandroid.R
import com.jeremiahzucker.pandroid.request.model.ExpandedStationModel

import com.jeremiahzucker.pandroid.ui.station.StationListFragment.OnListFragmentInteractionListener
import com.squareup.picasso.Picasso

/**
 * [RecyclerView.Adapter] that can display a [DummyItem] and makes a call to the
 * specified [OnListFragmentInteractionListener].
 */
class StationListAdapter(private val context: Context, private var stations: List<ExpandedStationModel>, private val mListener: OnListFragmentInteractionListener?) : RecyclerView.Adapter<StationListAdapter.ViewHolder>() {

    private val TAG: String = StationListAdapter::class.java.simpleName
    private var footerSummary: TextView? = null

    init {
        registerAdapterDataObserver(object : RecyclerView.AdapterDataObserver() {
            override fun onChanged() {
                super.onChanged()
                updateFooter()
            }
        })
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(viewType, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (position == stations.size) {
            footerSummary = holder.footerSummary
            updateFooter()
        } else {
            holder.stationModel = stations[position]
            holder.stationName?.text = stations[position].stationName
            holder.stationDetailUrl?.text = stations[position].stationDetailUrl
            Picasso.with(context).load(stations[position].artUrl).fit().centerInside()
                    .into(holder.stationArt)

            holder.mView.setOnClickListener {
                mListener?.onListFragmentInteraction(holder.stationModel!!)
            }
        }
    }

    private fun updateFooter() {
        if (footerSummary != null) {
            val itemCount = itemCount - 1
            if (itemCount > 1) {
                footerSummary?.visibility = View.VISIBLE
                footerSummary?.text = context.getString(R.string.station_list_footer_end_summary_formatter, itemCount)
            } else {
                footerSummary?.visibility = View.GONE
            }
        }
    }

    override fun getItemViewType(position: Int) =
            if (position == stations.size)
                R.layout.item_station_list_footer
            else
                R.layout.item_station

    override fun getItemCount(): Int = stations.size + 1

    fun updateStationList(stations: List<ExpandedStationModel>) {
        this.stations = stations
        notifyDataSetChanged()
    }

    inner class ViewHolder(val mView: View) : RecyclerView.ViewHolder(mView) {
        val stationName: TextView? = mView.findViewById(R.id.text_view_name) as TextView?
        val stationDetailUrl: TextView? = mView.findViewById(R.id.text_view_info) as TextView?
        val stationArt: AppCompatImageView? = mView.findViewById(R.id.image_view_album) as AppCompatImageView?
        var stationModel: ExpandedStationModel? = null

        val footerSummary: TextView? = mView.findViewById(R.id.text_view_summary) as TextView?

        override fun toString(): String {
            return super.toString() + " '" + stationName?.text + "'"
        }
    }
}
