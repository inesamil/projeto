package ps.leic.isel.pt.gis.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import ps.leic.isel.pt.gis.R

class TableLayoutAdapter(private val myDataset: Array<Any>) :
        RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    // Layouts
    private val headerLayout: Int = R.layout.content_allergies_table_header
    private val rowLayout: Int = R.layout.content_allergies_table_row

    // View Holder Types
    private val HEADER: Int = 0
    private val ROW: Int = 1

    // Provide a reference to the views for each data item
    // Access to all the views for a data item in a view holder.
    // Each data item is a box composed of 2 strings (allergensHeader and allergicsHeader) in this case that is shown in a TextView.
    class MyViewHolderHeader(view: View) : RecyclerView.ViewHolder(view) {
        var allergensHeader: TextView = view.findViewById(R.id.allergensHeaderText)
        var allergicsHeader: TextView = view.findViewById(R.id.allergicsNumHeaderText)
    }

    // Provide a reference to the views for each data item
    // Access to all the views for a data item in a view holder.
    // Each data item is a box composed of 2 strings (allergensText and allergicsText) in this case that is shown in a TextView and a EditText respectively.
    class MyViewHolderRow(view: View) : RecyclerView.ViewHolder(view) {
        var allergensText: TextView = view.findViewById(R.id.allergensText)
        var allergicsText: EditText = view.findViewById(R.id.allergicsNumEditText)

        init {
            view.setOnClickListener {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                /*val intent = Intent(mContext, YourActivity::class.java)
                intent.putExtra(YourActivity.ARG_FILE, filnames.get(position))
                startActivity(intent)*/
            }
        }
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        when (viewType) {
            HEADER -> {
                // create a new view
                val view = LayoutInflater.from(parent.context)
                        .inflate(headerLayout, parent, false) as View
                return MyViewHolderHeader(view)
            }
            else -> {
                // create a new view
                val view = LayoutInflater.from(parent.context)
                        .inflate(rowLayout, parent, false) as View
                return MyViewHolderRow(view)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        val firstPosition = 0
        if (position == firstPosition)  // First position - Table Headers
            return HEADER
        return ROW
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder.itemViewType) {
            ROW -> {
                // - get element from your dataset at this position
                // - replace the contents of the view with that element
                val item = myDataset[position]
                holder as MyViewHolderRow
                // Fill ViewHolder
                holder.allergensText.text = item as String //TODO: Alter type
                holder.allergicsText.hint = 0.toString() //TODO: Alter value
            }
        }
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = myDataset.size
}