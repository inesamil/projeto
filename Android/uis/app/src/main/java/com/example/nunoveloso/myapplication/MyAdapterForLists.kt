package com.example.nunoveloso.myapplication

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

class MyAdapterForLists(private val myDataset: Array<Any>) :
        RecyclerView.Adapter<MyAdapterForLists.MyViewHolder>() {

    private val listItemLayout: Int = R.layout.content_lists

    // Provide a reference to the views for each data item
    // Access to all the views for a data item in a view holder.
    // Each data item is a box composed of 2 strings (listNameText and totalItemsText) in this case that is shown in a TextView.
    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var listNameText: TextView = view.findViewById(R.id.listNameText)
        var itemsText: TextView = view.findViewById(R.id.totalItemsText)

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
        holder.itemsText.text = item as String //TODO: Alter type
        holder.listNameText.text = item
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = myDataset.size
}