package ps.leic.isel.pt.gis.uis.adapters

import android.content.Context
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import ps.leic.isel.pt.gis.R
import ps.leic.isel.pt.gis.model.StockItemDTO
import ps.leic.isel.pt.gis.model.dtos.StockItemDto

class StockItemListAdapter(private var data: Array<StockItemDto>)
    : RecyclerView.Adapter<StockItemListAdapter.ViewHolder>() {

    private lateinit var mOnItemClickListener: OnItemClickListener

    // inflates the cell layout from xml when needed
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_stock_item_list, parent, false) as View
        return ViewHolder(view)
    }

    // Binds the data to the textview in each cell
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item: StockItemDto = data[position]
        //TODO: holder.stockItemText.text = item.productName
        holder.brandText.text = item.brand
        holder.qntItemText.text = item.quantity.toString()
        // If the inner adapter needs data pass here the data
        //holder.innerAdapter.setData(item.storages)
    }

    // Total number of cells
    override fun getItemCount() = data.size

    // Set adapter data
    fun setData(data: Array<StockItemDto>) {
        this.data = data
        notifyDataSetChanged()
    }


    // Stores and recycles views as they are scrolled off screen
    inner class ViewHolder internal constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {
        internal val stockItemText: TextView = itemView.findViewById(R.id.stockItemText)
        internal val brandText: TextView = itemView.findViewById(R.id.brandText)
        internal val qntItemText: TextView = itemView.findViewById(R.id.qntText)
        internal val innerAdapter: StockItemInnerListAdapter

        init {
            val context: Context = itemView.context
            // Set inner list Adapter
            val stockItemInnerListRecyclerView: RecyclerView = itemView.findViewById(R.id.stockItemInnerListRecyclerView)
            stockItemInnerListRecyclerView.layoutManager = LinearLayoutManager(context)
            innerAdapter = StockItemInnerListAdapter()
            stockItemInnerListRecyclerView.adapter = innerAdapter

            itemView.setOnClickListener{
                mOnItemClickListener.onItemClick(it, adapterPosition)
            }
        }
    }

    // Sets listener for items click
    fun setOnItemClickListener(onItemClickListener: OnItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    // Parent activity will implement this method to respond to click events
    interface OnItemClickListener {
        fun onItemClick(view: View, position: Int)
    }
}