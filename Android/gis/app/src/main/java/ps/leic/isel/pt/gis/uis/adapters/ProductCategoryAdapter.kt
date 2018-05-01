package ps.leic.isel.pt.gis.uis.adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import ps.leic.isel.pt.gis.R

class ProductCategoryAdapter(context: Context, data: Array<String>)
    : RecyclerView.Adapter<ProductCategoryAdapter.ViewHolder>() {

    private val mData = data;
    private val mInflater: LayoutInflater = LayoutInflater.from(context)
    private var mOnItemClickListener: OnItemClickListener? = null

    // inflates the cell layout from xml when needed
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = mInflater.inflate(R.layout.content_product_category_item, parent, false)
        return ViewHolder(view)
    }

    // binds the data to the textview in each cell
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = mData[position]
        holder.productCategoryItemText.text = item
        holder.productCategoryItemImage.setImageResource(R.drawable.ic_house_setup)
    }

    // total number of cells
    override fun getItemCount(): Int {
        return mData.size;
    }

    // stores and recycles views as they are scrolled off screen
    inner class ViewHolder internal constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {
        internal var productCategoryItemText: TextView = itemView.findViewById<TextView>(R.id.productCategoryItemText) as TextView
        internal var productCategoryItemImage: ImageView = itemView.findViewById<ImageView>(R.id.productCategoryItemImage) as ImageView

        init {
            itemView.setOnClickListener {
                mOnItemClickListener?.onItemClick(it, adapterPosition)
            }
        }
    }

    fun setOnItemClickListener(onItemClickListener: OnItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    // parent activity will implement this method to respond to click events
    interface OnItemClickListener {
        fun onItemClick(view: View, position: Int)
    }
}