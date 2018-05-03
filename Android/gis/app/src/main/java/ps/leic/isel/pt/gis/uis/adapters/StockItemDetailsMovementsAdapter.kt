package ps.leic.isel.pt.gis.uis.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import ps.leic.isel.pt.gis.R
import ps.leic.isel.pt.gis.model.MovementDTO

class StockItemDetailsMovementsAdapter(private val data: Array<MovementDTO>)
    : RecyclerView.Adapter<StockItemDetailsMovementsAdapter.ViewHolder>() {

    private lateinit var mOnItemClickListener: OnItemClickListener

    // Inflates the cell layout from xml when needed
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_stockitemdetailsmovements, parent, false) as View
        return ViewHolder(view)
    }

    // Binds the data to the textview in each cell
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item: MovementDTO = data[position]
        // Fill View Holder
        holder.movementsItemText.text = item.type
        //TODO
    }

    // Total number of cells
    override fun getItemCount() = data.size

    // Stores and recycles views as they are scrolled off screen
    inner class ViewHolder internal constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {
        internal val movementsItemText: TextView = itemView.findViewById(R.id.movementsItemText)
        //TODO
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