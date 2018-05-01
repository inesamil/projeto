package ps.leic.isel.pt.gis.uis.adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import ps.leic.isel.pt.gis.R

class StockItemDetailsExpirationDateAdapter(context: Context)
    : RecyclerView.Adapter<StockItemDetailsExpirationDateAdapter.ViewHolder>() {

    private val mInflater: LayoutInflater = LayoutInflater.from(context)
    private val mExpirationDate = arrayOf("2 units expire at 23/03/2018", "1 units expire at 27/03/2018")

    // inflates the cell layout from xml when needed
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = mInflater.inflate(R.layout.content_stock_item_details_expiration_date, parent, false)
        return ViewHolder(view)
    }

    // binds the data to the textview in each cell
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.expirationDateItemText.text = mExpirationDate[position]
    }

    // total number of cells
    override fun getItemCount(): Int {
        return mExpirationDate.size
    }

    // stores and recycles views as they are scrolled off screen
    inner class ViewHolder internal constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {
        internal var expirationDateItemText: TextView = itemView.findViewById(R.id.expirationDateItemText)
    }
}