package ps.leic.isel.pt.gis.uis.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import ps.leic.isel.pt.gis.R
import ps.leic.isel.pt.gis.model.dtos.ExpirationDateDto

class StockItemDetailsExpirationDateAdapter
    : RecyclerView.Adapter<StockItemDetailsExpirationDateAdapter.ViewHolder>() {

    private var data: Array<ExpirationDateDto>? = null

    // Inflates the cell layout from xml when needed
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_stock_item_detail_expirationdate, parent, false) as View
        return ViewHolder(view)
    }

    // Binds the data to the textview in each cell
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        data?.let {
            val item: ExpirationDateDto = it[position]
            // Fill ViewHolder
            holder.expirationDateItemText.text = item.expirationDate
        }
    }

    fun setData(data: Array<ExpirationDateDto>?) {
        this.data = data
        notifyDataSetChanged()
    }

    // Total number of cells
    override fun getItemCount() = data?.size ?: 0

    // Stores and recycles views as they are scrolled off screen
    inner class ViewHolder internal constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {
        internal val expirationDateItemText: TextView = itemView.findViewById(R.id.expirationDateItemText)
    }
}