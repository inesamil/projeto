package ps.leic.isel.pt.gis.uis.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import ps.leic.isel.pt.gis.R
import ps.leic.isel.pt.gis.model.ListProduct
import ps.leic.isel.pt.gis.model.dtos.ListDto
import ps.leic.isel.pt.gis.model.dtos.ProductDto
import ps.leic.isel.pt.gis.utils.TextViewUtils

class AddProductToListAdapter() : RecyclerView.Adapter<AddProductToListAdapter.ViewHolder>() {

    private lateinit var mOnItemClickListener: OnItemClickListener
    private var data: Array<ListProduct>? = null

    // Inflates the cell layout from xml when needed
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_add_list_product, parent, false) as View
        return ViewHolder(view)
    }

    // Binds the data to the textview in each cell
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        data?.let {
            val item = it[position]
            // Fill ViewHolder
            holder.listText.text = item.productName

            holder.plusButton.setOnClickListener {
                item.quantity = TextViewUtils.incNumberText(holder.quantityText, 0, Short.MAX_VALUE.toInt()).toShort()
            }

            holder.minusButton.setOnClickListener {
                item.quantity = TextViewUtils.decNumberText(holder.quantityText, 0).toShort()
            }

            holder.actionButton.setOnClickListener {
                mOnItemClickListener.onItemActionClick(item)
            }
        }
    }

    // Total number of cells
    override fun getItemCount() = data?.size ?: 0

    fun setData(data: Array<ProductDto>) {
        this.data = data.map { product ->
            ListProduct(product.productId, product.productName, null, 0)
        }.toTypedArray()
        notifyDataSetChanged()
    }

    // Stores and recycles views as they are scrolled off screen
    inner class ViewHolder internal constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {
        internal val listText: TextView = itemView.findViewById(R.id.productText)
        internal val plusButton: Button = itemView.findViewById(R.id.plusButton)
        internal val minusButton: Button = itemView.findViewById(R.id.minusButton)
        internal val quantityText: TextView = itemView.findViewById(R.id.quantityText)
        internal val actionButton: Button = itemView.findViewById(R.id.actionButton)
    }

    // Sets listener for items click
    fun setOnItemClickListener(onItemClickListener: OnItemClickListener) {
        mOnItemClickListener = onItemClickListener
    }

    // Parent activity will implement this method to respond to click events
    interface OnItemClickListener {
        fun onItemActionClick(listProduct: ListProduct)
    }
}