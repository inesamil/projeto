package ps.leic.isel.pt.gis.uis.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import ps.leic.isel.pt.gis.R
import ps.leic.isel.pt.gis.model.dtos.ListProductDto

class ListDetailAdapter : RecyclerView.Adapter<ListDetailAdapter.ViewHolder>() {

    private lateinit var mOnItemClickListener: OnItemClickListener
    private var data: Array<ListProductDto>? = null

    // Inflates the cell layout from xml when needed
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_list, parent, false)
        return ViewHolder(view)
    }

    // Binds the data to the textview in each cell
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        data?.let {
            val item = it[position]
            // Fill ViewHolder
            holder.nameItemText.text = item.productName
            holder.numberItemText.text = item.productsListQuantity.toString()
        }
    }

    fun setData(data: Array<ListProductDto>) {
        this.data = data
        notifyDataSetChanged()
    }

    // Total number of cells
    override fun getItemCount() = data?.size ?: 0

    // stores and recycles views as they are scrolled off screen
    inner class ViewHolder internal constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {
        internal val nameItemText: TextView = itemView.findViewById(R.id.nameText) as TextView
        internal val numberItemText: TextView = itemView.findViewById(R.id.numberText) as TextView

        init {
            itemView.setOnClickListener {
                mOnItemClickListener.onItemClick(it, adapterPosition)
            }
        }
    }

    // Sets listener for items click
    fun setOnItemClickListener(onItemClickListener: OnItemClickListener) {
        mOnItemClickListener = onItemClickListener
    }

    // Parent activity will implement this method to respond to click events
    interface OnItemClickListener {
        fun onItemClick(view: View, position: Int)
    }
}
