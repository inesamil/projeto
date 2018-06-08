package ps.leic.isel.pt.gis.uis.adapters

import android.support.design.widget.FloatingActionButton
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import ps.leic.isel.pt.gis.R
import ps.leic.isel.pt.gis.model.dtos.ListDto
import ps.leic.isel.pt.gis.utils.TextViewUtils

class AddOrRemoveProductToListAdapter : RecyclerView.Adapter<AddOrRemoveProductToListAdapter.ViewHolder>() {

    private var data: Array<ListDto>? = null

    // Inflates the cell layout from xml when needed
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_add_remove_list_product_popup, parent, false) as View
        return ViewHolder(view)
    }

    // Binds the data to the textview in each cell
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        data?.let {
            val item = it[position]
            // Fill ViewHolder
            holder.listText.text = item.listName
        }
    }

    // Total number of cells
    override fun getItemCount() = data?.size ?: 0

    fun setData(data: Array<ListDto>) {
        this.data = data
        notifyDataSetChanged()
    }

    // Stores and recycles views as they are scrolled off screen
    inner class ViewHolder internal constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {
        internal val listText: TextView = itemView.findViewById(R.id.listText)
        internal val plusButton: Button = itemView.findViewById(R.id.plusButton)
        internal val minusButton: Button = itemView.findViewById(R.id.minusButton)
        internal val quantityText: TextView = itemView.findViewById(R.id.quantityText)

        init {
            plusButton.setOnClickListener {
                TextViewUtils.incNumberText(quantityText, 0, Int.MAX_VALUE)
            }

            minusButton.setOnClickListener {
                TextViewUtils.decNumberText(quantityText, 0)
            }
        }
    }
}