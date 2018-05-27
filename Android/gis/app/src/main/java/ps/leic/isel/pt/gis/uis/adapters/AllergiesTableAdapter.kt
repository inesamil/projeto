package ps.leic.isel.pt.gis.uis.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import ps.leic.isel.pt.gis.R
import ps.leic.isel.pt.gis.model.dtos.HouseAllergyDto

class AllergiesTableAdapter() :
        RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var data: Array<HouseAllergyDto>? = null

    // View Holder Types
    private val HEADER: Int = 0
    private val ROW: Int = 1

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        when (viewType) {
            HEADER -> {
                // create a new view
                val view = LayoutInflater.from(parent.context)
                        .inflate(R.layout.item_allergiestable_header, parent, false) as View
                return HeaderViewHolder(view)
            }
            else -> {
                // create a new view
                val view = LayoutInflater.from(parent.context)
                        .inflate(R.layout.item_allergiestable_row, parent, false) as View
                return RowViewHolder(view)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        val firstPosition = 0
        if (position == firstPosition)  // First position - Table Headers
            return HEADER
        return ROW
    }

    // Binds the data to the components in each cell
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder.itemViewType) {
            ROW -> {
                // - get element from your dataset at this position
                // - replace the contents of the view with that element
                data?.let {
                    val item: HouseAllergyDto = it.get(position - 1)
                    holder as RowViewHolder
                    // Fill ViewHolder
                    holder.allergensText.text = item.allergen
                    holder.allergicsText.setText(item.houseAllergiesNum.toString())
                }
            }
        }
    }

    fun setData(data: Array<HouseAllergyDto>) {
        this.data = data
        notifyDataSetChanged()
    }

    // Total number of cells
    override fun getItemCount() = data?.size?.plus(1) ?: 0

    // Stores and recycles views as they are scrolled off screen (HEADER TYPE)
    class HeaderViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var allergensHeader: TextView = view.findViewById(R.id.allergensHeaderText)
        var allergicsHeader: TextView = view.findViewById(R.id.allergicsNumHeaderText)
    }

    // Stores and recycles views as they are scrolled off screen (ROW TYPE)
    class RowViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var allergensText: TextView = view.findViewById(R.id.allergensText)
        var allergicsText: EditText = view.findViewById(R.id.allergicsNumEditText)
    }
}