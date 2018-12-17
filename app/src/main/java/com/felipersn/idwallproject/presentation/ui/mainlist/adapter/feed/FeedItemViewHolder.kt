package com.felipersn.idwallproject.presentation.ui.mainlist.adapter.category

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.felipersn.idwallproject.R

class FeedItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val feedImage: ImageView by lazy { itemView.findViewById<ImageView>(R.id.imageViewFeedImage) }
}
