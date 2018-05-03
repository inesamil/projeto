package ps.leic.isel.pt.gis.uis.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import ps.leic.isel.pt.gis.R
import ps.leic.isel.pt.gis.model.CategoryDTO

class CategoriesAdapter(private val data: Array<CategoryDTO>)
    : RecyclerView.Adapter<CategoriesAdapter.ViewHolder>() {

    private lateinit var mOnItemClickListener: OnItemClickListener

    // Inflates the cell layout from xml when needed
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_category, parent, false) as View
        return ViewHolder(view)
    }

    // Binds the data to the components in each cell
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item: CategoryDTO = data[position]
        // Fill ViewHolder
        holder.categoryItemText.text = item.categoryName
        holder.categoryItemImage.setImageResource(R.drawable.ic_house_setup)
    }

    // Total number of cells
    override fun getItemCount() = data.size

    // Stores and recycles views as they are scrolled off screen
    inner class ViewHolder internal constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {
        internal val categoryItemText: TextView = itemView.findViewById(R.id.categoryItemText) as TextView
        internal val categoryItemImage: ImageView = itemView.findViewById(R.id.categoryItemImage) as ImageView

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