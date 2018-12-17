package com.felipersn.idwallproject.presentation.ui.mainlist.adapter.category

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.felipersn.idwallproject.R

class CategoryItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val categoryTitle: TextView by lazy { itemView.findViewById<TextView>(R.id.textViewCategoryName) }
}
