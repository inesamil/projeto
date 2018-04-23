package ps.leic.isel.pt.gis

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

class ListRecyclerViewAdapter(context: Context, names: Array<String>, numbers: Array<String>)
    : RecyclerView.Adapter<ListRecyclerViewAdapter.ViewHolder>() {

    private val mNames = names;
    private val mNumbers = numbers;
    private val mInflater: LayoutInflater = LayoutInflater.from(context)

    // inflates the cell layout from xml when needed
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = mInflater.inflate(R.layout.content_list_item, parent, false)
        return ViewHolder(view)
    }

    // binds the data to the textview in each cell
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.nameItemText.text = mNames[position]
        holder.numberItemText.text = mNumbers[position]
    }

    // total number of cells
    override fun getItemCount(): Int {
        return mNames.size
    }

    // stores and recycles views as they are scrolled off screen
    inner class ViewHolder internal constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {
        internal var nameItemText: TextView = itemView.findViewById<TextView>(R.id.nameText) as TextView
        internal var numberItemText: TextView = itemView.findViewById<TextView>(R.id.numberText) as TextView
    }
}
