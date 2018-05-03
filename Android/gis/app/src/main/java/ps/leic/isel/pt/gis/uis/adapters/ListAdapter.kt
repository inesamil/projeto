package ps.leic.isel.pt.gis.uis.adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import ps.leic.isel.pt.gis.R
import ps.leic.isel.pt.gis.model.ListProductDTO

class ListAdapter(private val data: Array<ListProductDTO>)
    : RecyclerView.Adapter<ListAdapter.ViewHolder>() {

    private lateinit var mOnItemClickListener: OnItemClickListener

    // Inflates the cell layout from xml when needed
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_list, parent, false)
        return ViewHolder(view)
    }

    // Binds the data to the textview in each cell
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item: ListProductDTO = data[position]
        // Fill ViewHolder
        holder.nameItemText.text = item.productName
        holder.numberItemText.text = item.quantity.toString()
    }

    // Total number of cells
    override fun getItemCount() = data.size

    // stores and recycles views as they are scrolled off screen
    inner class ViewHolder internal constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {
        internal val nameItemText: TextView = itemView.findViewById<TextView>(R.id.nameText) as TextView
        internal val numberItemText: TextView = itemView.findViewById<TextView>(R.id.numberText) as TextView

        init {
            itemView.setOnClickListener {
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
