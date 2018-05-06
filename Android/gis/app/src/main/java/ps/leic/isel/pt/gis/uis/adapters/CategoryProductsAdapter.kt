package ps.leic.isel.pt.gis.uis.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import ps.leic.isel.pt.gis.R
import ps.leic.isel.pt.gis.model.ProductDTO

class CategoryProductsAdapter(private val data: Array<ProductDTO>)
    : RecyclerView.Adapter<CategoryProductsAdapter.ViewHolder>() {

    private var mOnItemClickListener: OnItemClickListener? = null

    // Inflates the cell layout from xml when needed
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_category_products, parent, false) as View
        return ViewHolder(view)
    }

    // Binds the data to the textview in each cell
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item: ProductDTO = data[position]
        // Fill ViewHolder
        holder.productDiaryItemText.text = item.name
    }

    // Total number of cells
    override fun getItemCount() = data.size

    // Stores and recycles views as they are scrolled off screen
    inner class ViewHolder internal constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {
        internal val productDiaryItemText: TextView = itemView.findViewById(R.id.productDiaryItemText)
        internal val productDiaryItemPlusBtn: Button = itemView.findViewById(R.id.productDiaryItemPlusBtn)
        internal val productDiaryItemMinusBtn: Button = itemView.findViewById(R.id.productDiaryItemMinusBtn)

        init {
            productDiaryItemText.setOnClickListener{
                mOnItemClickListener?.onTextClick(it, adapterPosition)
            }

            productDiaryItemPlusBtn.setOnClickListener {
                mOnItemClickListener?.onPlusClick(it, adapterPosition)
            }

            productDiaryItemMinusBtn.setOnClickListener {
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