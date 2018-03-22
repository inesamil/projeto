package com.example.nunoveloso.myapplication

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

class StockItemDetailsStorageRecyclerViewAdapter(context: Context)
    : RecyclerView.Adapter<StockItemDetailsStorageRecyclerViewAdapter.ViewHolder>() {

    private val mInflater: LayoutInflater = LayoutInflater.from(context)
    private val mStorage = arrayOf("2 units in the fridge", "2 units in the cabinet1")

    // inflates the cell layout from xml when needed
    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        val view = mInflater.inflate(R.layout.content_stock_item_details_storage, parent, false)
        return ViewHolder(view)
    }

    // binds the data to the textview in each cell
    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        holder?.storageItemText?.text = mStorage[position]
    }

    // total number of cells
    override fun getItemCount(): Int {
        return mStorage.size
    }

    // stores and recycles views as they are scrolled off screen
    inner class ViewHolder internal constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {
        internal var storageItemText: TextView = itemView.findViewById(R.id.storageItemText)
    }
}