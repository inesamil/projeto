package com.example.nunoveloso.myapplication

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

class StockItemDetailsMovementsRecyclerViewAdapter(context: Context)
    : RecyclerView.Adapter<StockItemDetailsMovementsRecyclerViewAdapter.ViewHolder>() {

    private val mInflater: LayoutInflater = LayoutInflater.from(context)
    private val mMovements = arrayOf("enter 1 unit at 25/03/2018", "exit 2 units at 25/03/2018", "enter 1 unit at 26/03/2018")

    // inflates the cell layout from xml when needed
    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        val view = mInflater.inflate(R.layout.content_stock_item_details_movements, parent, false)
        return ViewHolder(view)
    }

    // binds the data to the textview in each cell
    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        holder?.movementsItemText?.text = mMovements[position]
    }

    // total number of cells
    override fun getItemCount(): Int {
        return mMovements.size
    }

    // stores and recycles views as they are scrolled off screen
    inner class ViewHolder internal constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {
        internal var movementsItemText: TextView = itemView.findViewById(R.id.movementsItemText)
    }
}