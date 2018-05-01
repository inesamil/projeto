package ps.leic.isel.pt.gis.uis.adapters

import android.content.Context
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import ps.leic.isel.pt.gis.R

class StockItemListAdapter(private val context: Context, private val name: Array<String>)
    : RecyclerView.Adapter<StockItemListAdapter.ViewHolder>() {

    private var stockItemListInside: RecyclerView? = null

    // inflates the cell layout from xml when needed
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.content_stock_item_list_item, parent, false)
        return ViewHolder(view)
    }

    // binds the data to the textview in each cell
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.stockItemText.text = "Leite UHT Magro" // Just for demonstration
        holder.brandText.text = name[position]
        holder.qntItemText.text = "2 units" // Just for demonstration
        // If the inner adapter needs data pass here the data
        // holder?.innerAdapter.setData(data)
    }

    // total number of cells
    override fun getItemCount(): Int {
        return name.size
    }

    // stores and recycles views as they are scrolled off screen
    inner class ViewHolder internal constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {
        internal var stockItemText: TextView
        internal var brandText: TextView
        internal var qntItemText: TextView
        internal var innerAdapter: StockItemListInsideAdapter

        init {
            val context: Context = itemView.context
            stockItemText = itemView.findViewById(R.id.stockItemText)
            brandText = itemView.findViewById(R.id.brandText)
            qntItemText = itemView.findViewById(R.id.qntText)
            stockItemListInside = itemView.findViewById(R.id.stockItemListInsideRecyclerView)
            stockItemListInside?.layoutManager = LinearLayoutManager(context)
            innerAdapter = StockItemListInsideAdapter()
            stockItemListInside?.adapter = innerAdapter
        }
    }
}