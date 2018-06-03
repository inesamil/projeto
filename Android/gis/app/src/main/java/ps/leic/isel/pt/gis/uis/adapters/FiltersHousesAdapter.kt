package ps.leic.isel.pt.gis.uis.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import ps.leic.isel.pt.gis.R
import ps.leic.isel.pt.gis.model.dtos.HouseDto

class FiltersHousesAdapter : RecyclerView.Adapter<FiltersHousesAdapter.ViewHolder>() {

    private var data: Array<HouseDto>? = null

    // Inflates the cell layout from xml when needed
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_houses_filter, parent, false) as View
        return ViewHolder(view)
    }

    // Binds the data to the textview in each cell
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        data?.let {
            val item = it[position]
            // Fill ViewHolder
            holder.houseCheckBox.text = item.name
        }
    }

    // Total number of cells
    override fun getItemCount(): Int = data?.size ?: 0

    fun setData(data: Array<HouseDto>) {
        this.data = data
        notifyDataSetChanged()
    }

    // Stores and recycles views as they are scrolled off screen
    inner class ViewHolder internal constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {
        internal val houseCheckBox: CheckBox = itemView.findViewById(R.id.houseCheckbox)
    }

}