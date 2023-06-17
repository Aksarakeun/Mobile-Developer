package com.fadil.aksarakeun.base

import androidx.recyclerview.widget.RecyclerView

abstract class BaseRecyclerViewAdapter<VH : RecyclerView.ViewHolder, T> :
    RecyclerView.Adapter<VH>() {

    protected var items: MutableList<T> = mutableListOf()

    var onItemClick: ((T) -> Unit)? = null

    abstract fun onBindViewHolder(holder: VH, item: T, position: Int)


    override fun getItemCount(): Int {
        return this.items.size
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        onBindViewHolder(holder, this.items[position], position)
    }

    override fun getItemViewType(position: Int): Int = position

    override fun getItemId(position: Int): Long = position.toLong()

    private fun clear() {
        this.items.clear()
    }

    fun setItem(item: MutableList<T>) {
        clear()
        this.items.addAll(item)
        this.notifyDataSetChanged()
    }
}