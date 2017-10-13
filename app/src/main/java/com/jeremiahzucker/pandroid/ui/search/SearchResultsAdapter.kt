package com.jeremiahzucker.pandroid.ui.search

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.jeremiahzucker.pandroid.R
import com.jeremiahzucker.pandroid.request.method.exp.music.Search

/**
 * [RecyclerView.Adapter] that can display a [DummyItem] and makes a call to the
 * specified [OnListFragmentInteractionListener].
 */
class SearchResultsAdapter(private val context: Context, private var results: List<Search.SearchResult>, private val mListener: (String) -> Unit) : RecyclerView.Adapter<SearchResultsAdapter.ViewHolder>() {

    private val TAG: String = SearchResultsAdapter::class.java.simpleName
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
        if (position == results.size) {
            footerSummary = holder.footerSummary
            updateFooter()
        } else {
            holder.searchResult = results[position]
            holder.name?.text = results[position].name

            holder.mView.setOnClickListener {
                mListener(holder.searchResult!!.musicToken)
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
            if (position == results.size)
                R.layout.item_station_list_footer
            else
                R.layout.item_station

    override fun getItemCount(): Int = results.size + 1

    fun updateSearchResults(results: List<Search.SearchResult>) {
        this.results = results
        notifyDataSetChanged()
    }

    inner class ViewHolder(val mView: View) : RecyclerView.ViewHolder(mView) {
        val name: TextView? = mView.findViewById(R.id.text_view_name) as TextView?
        var searchResult: Search.SearchResult? = null

        val footerSummary: TextView? = mView.findViewById(R.id.text_view_summary) as TextView?

        override fun toString(): String {
            return super.toString() + " '" + name?.text + "'"
        }
    }
}
