package ps.leic.isel.pt.gis

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

class StockItemListInsideRecyclerViewAdapter()
    : RecyclerView.Adapter<StockItemListInsideRecyclerViewAdapter.ViewHolder>() {

    private val examplesLocal = arrayOf("Fridge", "Cabinet1")
    private val examplesUnit = arrayOf("2 units", "4 units")

    fun setLocalItems(localItems: Array<Any>) {
        //mLocalItems = localItems
        //notifyDataSetChanged()
    }

    // inflates the cell layout from xml when needed
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val context: Context? = parent.context
        val itemView: View = LayoutInflater.from(context).inflate(R.layout.content_stock_item_list_item_inside, parent, false)
        return ViewHolder(itemView)
    }

    // binds the data to the textview in each cell
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.localItemText.text = examplesLocal[position]
        holder.unitsItemText.text = examplesUnit[position]
    }

    // total number of cells
    override fun getItemCount(): Int {
        return examplesLocal.size
    }

    // stores and recycles views as they are scrolled off screen
    inner class ViewHolder internal constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {
        internal var localItemText: TextView = itemView.findViewById<TextView>(R.id.localText) as TextView
        internal var unitsItemText: TextView = itemView.findViewById<TextView>(R.id.unitsText) as TextView
    }
}