package com.lol.wanandroidwithkotlin.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.lol.wanandroidwithkotlin.R
import kotlinx.android.synthetic.main.item_search_history.view.*

/**
 * Create by Jingui on 2020/6/5
 * Describe:
 * email: m15008497107@163.com
 */
class SearchHistoryAdapter(
    private val context: Context,
    private val layoutResId: Int = R.layout.item_search_history
) : ListAdapter<String, SearchHistoryHolder>(HistoryDiffCallback()) {

    var onItemClickListener: ((position: Int) -> Unit)? = null
    var onDeleteClickListener: ((position: Int) -> Unit)? = null
    var data: MutableList<String> = mutableListOf()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchHistoryHolder {
        return SearchHistoryHolder(
            LayoutInflater.from(context).inflate(layoutResId, parent, false)
        )

    }

    override fun onBindViewHolder(holder: SearchHistoryHolder, position: Int) {
        holder.itemView.run {
            tvLabel.text = getItem(position)
            setOnClickListener {
                onItemClickListener?.invoke(holder.adapterPosition)
            }
            ivDelete.setOnClickListener {
                onDeleteClickListener?.invoke(holder.adapterPosition)
            }
        }
    }
    override fun onBindViewHolder(
        holder: SearchHistoryHolder,
        position: Int,
        payloads: MutableList<Any>
    ) {
        onBindViewHolder(holder, position)
    }

    override fun submitList(list: MutableList<String>?) {
        data = if (list.isNullOrEmpty()) {
            mutableListOf()
        } else {
            ArrayList(list)
        }
        super.submitList(data)
    }
}

class SearchHistoryHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

class HistoryDiffCallback : DiffUtil.ItemCallback<String>() {
    override fun areItemsTheSame(oldItem: String, newItem: String) = oldItem == newItem
    override fun areContentsTheSame(oldItem: String, newItem: String) = oldItem == newItem
}