package ps.leic.isel.pt.gis.uis.adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import ps.leic.isel.pt.gis.R
import ps.leic.isel.pt.gis.model.dtos.StorageDto

class StockItemInnerListAdapter : RecyclerView.Adapter<StockItemInnerListAdapter.ViewHolder>() {

    private var data: Array<String>? = null

    // inflates the cell layout from xml when needed
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val context: Context? = parent.context
        val itemView: View = LayoutInflater.from(context).inflate(R.layout.item_stock_item_innerlist, parent, false)
        return ViewHolder(itemView)
    }

    // binds the data to the textview in each cell
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        data?.let {
            val item = it[position]
            holder.localItemText.text = item
            holder.unitsItemText.text = "0" //TODO: get Stock Item quantity
        }
    }

    // total number of cells
    override fun getItemCount(): Int {
        return data?.size ?: 0
    }

    fun setData(data: Array<String>) {
        this.data = data
    }

    // stores and recycles views as they are scrolled off screen
    inner class ViewHolder internal constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {
        internal var localItemText: TextView = itemView.findViewById(R.id.localText) as TextView
        internal var unitsItemText: TextView = itemView.findViewById(R.id.unitsText) as TextView
    }
}