package com.felipersn.idwallproject.presentation.ui.mainlist.adapter.feed

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.felipersn.idwallproject.R
import com.felipersn.idwallproject.common.extension.loadImageWithCrossFade
import com.felipersn.idwallproject.presentation.ui.mainlist.adapter.category.FeedItemViewHolder


class MainLisFeedAdapter(
    private val mainListFeedAdapterListener: MainListFeedAdapterListener
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var feedList: List<Any?> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflate = LayoutInflater.from(parent.context).inflate(R.layout.list_item_main_list_feed, parent, false)

        val viewHolder = FeedItemViewHolder(inflate)

        viewHolder.itemView.setOnClickListener {
            mainListFeedAdapterListener.onItemClick(
                feedList[viewHolder.adapterPosition] as String,
                viewHolder.feedImage
            )
        }

        return viewHolder
    }

    override fun getItemCount(): Int {
        return feedList.size
    }

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, position: Int) {
        val categoryEntity = feedList[position] as String
        bindMessageItemViewHolder(viewHolder, categoryEntity)
    }


    fun setMessageList(setListNotification: List<Any?>) {
        feedList = emptyList()
        feedList = setListNotification
        notifyDataSetChanged()
    }

    private fun bindMessageItemViewHolder(viewHolder: RecyclerView.ViewHolder, url: String) {
        val categoryItemViewHolder = viewHolder as FeedItemViewHolder

        categoryItemViewHolder.feedImage.loadImageWithCrossFade(url)
        categoryItemViewHolder.feedImage.transitionName = url

    }
}