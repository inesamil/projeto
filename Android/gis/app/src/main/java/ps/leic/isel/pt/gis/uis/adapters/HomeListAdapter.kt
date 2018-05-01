package ps.leic.isel.pt.gis.uis.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import ps.leic.isel.pt.gis.R

class HomeListAdapter(private val myDataset: Array<Any>) :
        RecyclerView.Adapter<HomeListAdapter.MyViewHolder>() {

    private val listItemLayout: Int = R.layout.content_home

    // Provide a reference to the views for each data item
    // Access to all the views for a data item in a view holder.
    // Each data item is a box composed of 2 strings (listNameText and totalItemsText) in this case that is shown in a TextView.
    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        // List Name
        val listNameText: TextView = view.findViewById(R.id.listoverview_listnameText)

        // Column
        val columnTitleText: TextView = view.findViewById(R.id.listoverview_columnTitleText)

        // Items
        val item1Text: TextView = view.findViewById(R.id.listoverview_item1Text)
        val item1ValueText: TextView = view.findViewById(R.id.listoverview_cell1)

        val item2Text: TextView = view.findViewById(R.id.listoverview_item2Text)
        val item2ValueText: TextView = view.findViewById(R.id.listoverview_cell2)

        val item3Text: TextView = view.findViewById(R.id.listoverview_item3Text)
        val item3ValueText: TextView = view.findViewById(R.id.listoverview_cell3)
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        // create a new view
        val view = LayoutInflater.from(parent.context)
                .inflate(listItemLayout, parent, false) as View
        return MyViewHolder(view)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        val item = myDataset[position]

        // Fill ViewHolder
        //TODO:UPDATE
        holder.listNameText.text = "Groceries List"
        holder.columnTitleText.text = "Quantity"
        holder.item1Text.text = "Milk"
        holder.item1ValueText.text = "2"
        holder.item2Text.text = "Sugar"
        holder.item2ValueText.text = "3"
        holder.item3Text.text = "Eggs"
        holder.item3ValueText.text = "12"
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = myDataset.size
}