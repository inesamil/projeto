package ps.leic.isel.pt.gis.uis.adapters

import android.content.Intent
import android.content.ServiceConnection
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import ps.leic.isel.pt.gis.R
import ps.leic.isel.pt.gis.model.ListDTO
import ps.leic.isel.pt.gis.uis.activities.ListActivity
import ps.leic.isel.pt.gis.utils.ServiceLocator

class ListsAdapter(private val lists: Array<ListDTO>) :
        RecyclerView.Adapter<ListsAdapter.MyViewHolder>() {

    private val listItemLayout: Int = R.layout.item_content_lists

    // Provide a reference to the views for each data item
    // Access to all the views for a data item in a view holder.
    // Each data item is a box composed of 2 strings (listNameText and totalItemsText) in this case that is shown in a TextView.
    class MyViewHolder(view: View, val list: ListDTO) : RecyclerView.ViewHolder(view) {
        var listNameText: TextView = view.findViewById(R.id.listNameText)
        var itemsText: TextView = view.findViewById(R.id.totalItemsText)

        init {
            view.setOnClickListener {
                val intent: Intent = Intent(ServiceLocator.getContext(), ListActivity::class.java)

            }
        }
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        // create a new view
        val view = LayoutInflater.from(parent.context)
                .inflate(listItemLayout, parent, false) as View
        return MyViewHolder(view, 0)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        val item = lists[position]

        // Fill ViewHolder
        //holder.itemsText.text =
        holder.listNameText.text = item.listName
    }

    // Return the size of lists (invoked by the layout manager)
    override fun getItemCount() = lists.size
}