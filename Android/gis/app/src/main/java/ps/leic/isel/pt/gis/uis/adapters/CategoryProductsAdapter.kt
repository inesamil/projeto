package ps.leic.isel.pt.gis.uis.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import ps.leic.isel.pt.gis.R
import ps.leic.isel.pt.gis.model.dtos.ProductDto

class CategoryProductsAdapter
    : RecyclerView.Adapter<CategoryProductsAdapter.ViewHolder>() {

    private var mOnItemClickListener: OnItemClickListener? = null
    private var data: Array<ProductDto>? = null

    // Inflates the cell layout from xml when needed
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_category_products, parent, false) as View
        return ViewHolder(view)
    }

    // Binds the data to the textview in each cell
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        data?.let {
            val item = it[position]
            // Fill ViewHolder
            holder.productText.text = item.productName
        }
    }

    fun setData(data: Array<ProductDto>) {
        this.data = data
        notifyDataSetChanged()
    }

    // Total number of cells
    override fun getItemCount() = data?.size ?: 0

    // Stores and recycles views as they are scrolled off screen
    inner class ViewHolder internal constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {
        internal val productText: TextView = itemView.findViewById(R.id.productDiaryItemText)
        internal val productPlusBtn: Button = itemView.findViewById(R.id.productDiaryItemPlusBtn)
        internal val productMinusBtn: Button = itemView.findViewById(R.id.productDiaryItemMinusBtn)

        init {
            productText.setOnClickListener {
                mOnItemClickListener?.onTextClick(it, adapterPosition)
            }

            productPlusBtn.setOnClickListener {
                mOnItemClickListener?.onPlusClick(it, adapterPosition)
            }

            productMinusBtn.setOnClickListener {
                mOnItemClickListener?.onMinusClick(it, adapterPosition)
            }
        }
    }

    // Sets listener for items click
    fun setOnItemClickListener(onItemClickListener: OnItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    // Parent activity will implement this method to respond to click events
    interface OnItemClickListener {
        fun onTextClick(view: View, position: Int)
        fun onPlusClick(view: View, position: Int)
        fun onMinusClick(view: View, position: Int)
    }
}