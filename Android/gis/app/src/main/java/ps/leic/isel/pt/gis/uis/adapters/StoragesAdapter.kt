package ps.leic.isel.pt.gis.uis.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import ps.leic.isel.pt.gis.R
import ps.leic.isel.pt.gis.model.dtos.StorageDto

class StoragesAdapter(private val data: Array<StorageDto>) : RecyclerView.Adapter<StoragesAdapter.ViewHolder>() {

    // Inflates the cell layout from xml when needed
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_storages, parent, false) as View
        return ViewHolder(view)
    }

    // Binds the data to the textview in each cell
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = data[position]
        // Fill ViewHolder
        holder.storageName.text = item.name
        holder.storageTemperature.text = item.temperature
    }

    override fun getItemCount() = data.size

    // Stores and recycles views as they are scrolled off screen
    inner class ViewHolder internal constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {
        internal val storageName: TextView = itemView.findViewById(R.id.storageNameText)
        internal val storageTemperature: TextView = itemView.findViewById(R.id.storageTemperatureText)
    }
}