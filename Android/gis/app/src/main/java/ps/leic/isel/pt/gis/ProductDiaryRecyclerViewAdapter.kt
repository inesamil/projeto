package ps.leic.isel.pt.gis

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView

class ProductDiaryRecyclerViewAdapter(context: Context, data: Array<String>) : RecyclerView.Adapter<ProductDiaryRecyclerViewAdapter.ViewHolder>() {

    private val mData = data;
    private val mInflater: LayoutInflater = LayoutInflater.from(context)
    private var mOnItemClickListener: OnItemClickListener? = null

    // inflates the cell layout from xml when needed
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = mInflater.inflate(R.layout.content_product_diary_item, parent, false)
        return ViewHolder(view)
    }

    // binds the data to the textview in each cell
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = mData[position]
        holder.productDiaryItemText.text = item
    }

    // total number of cells
    override fun getItemCount(): Int {
        return mData.size
    }

    // stores and recycles views as they are scrolled off screen
    inner class ViewHolder internal constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {
        internal var productDiaryItemText: TextView = itemView.findViewById(R.id.productDiaryItemText)
        internal var productDiaryItemPlusBtn: Button = itemView.findViewById(R.id.productDiaryItemPlusBtn)
        internal var productDiaryItemMinusBtn: Button = itemView.findViewById(R.id.productDiaryItemMinusBtn)

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

    fun setOnItemClickListener(onItemClickListener: OnItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    // parent activity will implement this method to respond to click events
    interface OnItemClickListener {
        fun onTextClick(view: View, position: Int)
        fun onPlusClick(view: View, position: Int)
        fun onMinusClick(view: View, position: Int)
    }
}