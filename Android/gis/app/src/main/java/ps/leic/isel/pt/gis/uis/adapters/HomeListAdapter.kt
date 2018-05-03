package ps.leic.isel.pt.gis.uis.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import ps.leic.isel.pt.gis.R
import ps.leic.isel.pt.gis.model.*

class HomeListAdapter(private val data: Array<ListDTO>) :
        RecyclerView.Adapter<HomeListAdapter.ViewHolder>() {

    private lateinit var mOnItemClickListener: OnItemClickListener

    // Inflates the cell layout from xml when needed
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_home, parent, false) as View
        return ViewHolder(view)
    }

    // Binds the data to the components in each cell
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item: ListDTO = data[position]

        val firstProduct: ListProductDTO = item.items[0]
        val secondProduct: ListProductDTO = item.items[1]
        val thirdProduct: ListProductDTO = item.items[2]

        // Fill ViewHolder
        holder.listNameText.text = item.listName
        holder.item1Text.text = firstProduct.productName
        holder.item1ValueText.text = firstProduct.quantity.toString()
        holder.item2Text.text = secondProduct.productName
        holder.item2ValueText.text = secondProduct.quantity.toString()
        holder.item3Text.text = thirdProduct.productName
        holder.item3ValueText.text = thirdProduct.quantity.toString()
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = data.size

    // Stores and recycles views as they are scrolled off screen
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        // List Name
        internal val listNameText: TextView = itemView.findViewById(R.id.listoverview_listnameText)

        // Item 1
        internal val item1Text: TextView = itemView.findViewById(R.id.listoverview_item1Text)
        internal val item1ValueText: TextView = itemView.findViewById(R.id.listoverview_cell1)
        // Item 2
        internal val item2Text: TextView = itemView.findViewById(R.id.listoverview_item2Text)
        internal val item2ValueText: TextView = itemView.findViewById(R.id.listoverview_cell2)
        // Item 3
        internal val item3Text: TextView = itemView.findViewById(R.id.listoverview_item3Text)
        internal val item3ValueText: TextView = itemView.findViewById(R.id.listoverview_cell3)

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