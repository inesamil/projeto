package ps.leic.isel.pt.gis.uis.adapters

import android.support.v7.widget.RecyclerView
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import ps.leic.isel.pt.gis.R
import ps.leic.isel.pt.gis.model.dtos.HouseAllergyDto
import android.text.Editable
import ps.leic.isel.pt.gis.model.HouseAllergy


class AllergiesTableAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var data: Array<HouseAllergy>? = null

    // View Holder Types
    private val HEADER: Int = 0
    private val ROW: Int = 1

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            HEADER -> {
                // create a new view
                val view = LayoutInflater.from(parent.context)
                        .inflate(R.layout.item_allergiestable_header, parent, false) as View
                HeaderViewHolder(view)
            }
            else -> {
                // create a new view
                val view = LayoutInflater.from(parent.context)
                        .inflate(R.layout.item_allergiestable_row, parent, false) as View
                RowViewHolder(view)
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
                    val item: HouseAllergy = it[position - 1]
                    holder as RowViewHolder
                    // Fill ViewHolder
                    holder.allergensText.text = item.allergy
                    holder.allergicsText.setText(item.allergics.toString())
                    holder.allergicsText.addTextChangedListener(MyTextWatcher(position - 1))
                }
            }
        }
    }

    // Total number of cells
    override fun getItemCount() = data?.size?.plus(1) ?: 0

    fun setData(data: Array<HouseAllergyDto>) {
        this.data = data.map { houseAllergyDto -> HouseAllergy(houseAllergyDto.allergen, houseAllergyDto.houseAllergiesNum) }.toTypedArray()
        notifyDataSetChanged()
    }

    // Return an array of the allergens
    fun getHouseAllergyItems() : Array<HouseAllergy>? {
        return data
    }

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

    /***
     * TextWatcher
     ***/
    inner class  MyTextWatcher(val idx: Int) : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            // Nothing to do here
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            // Nothing to do here
        }

        override fun afterTextChanged(s: Editable?) {
            data?.let {
                s?.let { str ->
                    if (str.isNotEmpty())
                        it[idx].allergics = str.toString().toShort()
                }
            }
        }
    }
}