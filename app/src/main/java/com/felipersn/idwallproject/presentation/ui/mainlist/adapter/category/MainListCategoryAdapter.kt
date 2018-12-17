package com.felipersn.idwallproject.presentation.ui.mainlist.adapter.category

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.felipersn.idwallproject.R


class MainListCategoryAdapter(
    private val mainListCategoryAdapterListener: MainListCategoryAdapterListener
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var categoryList: List<Any> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflate = LayoutInflater.from(parent.context).inflate(R.layout.list_item_main_list_category, parent, false)

        val viewHolder = CategoryItemViewHolder(inflate)

        viewHolder.itemView.setOnClickListener { mainListCategoryAdapterListener.onCategoryClick(categoryList[viewHolder.adapterPosition] as String) }

        return viewHolder
    }

    override fun getItemCount(): Int {
        return categoryList.size
    }

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, position: Int) {
        val categoryEntity = categoryList[position] as String?
        bindMessageItemViewHolder(viewHolder, categoryEntity)
    }


    fun setMessageList(setListNotification: List<Any>) {
        categoryList = setListNotification
        notifyDataSetChanged()
    }

    private fun bindMessageItemViewHolder(viewHolder: RecyclerView.ViewHolder, categoryName: String?) {
        val categoryItemViewHolder = viewHolder as CategoryItemViewHolder
        categoryItemViewHolder.categoryTitle.text = categoryName
    }
}