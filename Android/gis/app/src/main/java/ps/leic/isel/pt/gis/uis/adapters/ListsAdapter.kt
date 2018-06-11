package ps.leic.isel.pt.gis.uis.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import ps.leic.isel.pt.gis.R
import ps.leic.isel.pt.gis.model.dtos.ListDto

class ListsAdapter : RecyclerView.Adapter<ListsAdapter.ViewHolder>() {

    private lateinit var mOnItemClickListener: OnItemClickListener
    private var data: Array<ListDto>? = null

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_lists, parent, false) as View
        return ViewHolder(view)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        data?.let {
            val item = it[position]
            // Fill ViewHolder
            holder.listNameText.text = item.listName
            holder.houseNameText.text = item.houseName
        }
    }

    fun setData(data: Array<ListDto>) {
        this.data = data
        notifyDataSetChanged()
    }

    // Total number of cells
    override fun getItemCount() = data?.size ?: 0

    // Stores and recycles views as they are scrolled off screen
    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        internal val listNameText: TextView = view.findViewById(R.id.listNameEditText)
        internal val houseNameText: TextView = view.findViewById(R.id.houseNameText)

        init {
            view.setOnClickListener {
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