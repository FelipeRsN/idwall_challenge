package com.felipersn.idwallproject.presentation.ui.mainlist.adapter.feed

import android.widget.ImageView

interface MainListFeedAdapterListener {
    fun onItemClick(url: String, imageView: ImageView)
}