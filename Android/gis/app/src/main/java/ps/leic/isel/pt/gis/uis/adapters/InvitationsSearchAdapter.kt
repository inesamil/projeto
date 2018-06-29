package ps.leic.isel.pt.gis.uis.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import ps.leic.isel.pt.gis.R
import ps.leic.isel.pt.gis.model.dtos.HouseDto

class InvitationsSearchAdapter : RecyclerView.Adapter<InvitationsSearchAdapter.ViewHolder>() {

    private var data: Array<HouseDto>? = null
    private lateinit var mOnItemClickListener: OnItemClickListener

    // Inflates the cell layout from xml when needed
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_invitations_search, parent, false) as View
        return ViewHolder(view)
    }

    // Binds the data to the textview in each cell
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        data?.let {
            val item = it[position]
            // Fill ViewHolder
            holder.houseNameText.text = item.name
            holder.adminNameText.text = item.members?.find { member -> member.administrator == true }?.username
            holder.sendIcon.setOnClickListener {
                mOnItemClickListener.onSendInvitation(item.houseId)
            }
        }
    }

    // Total number of cells
    override fun getItemCount() = data?.size ?: 0

    fun setData(data: Array<HouseDto>) {
        this.data = data
        notifyDataSetChanged()
    }

    // Stores and recycles views as they are scrolled off screen
    inner class ViewHolder internal constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {
        internal val houseNameText: TextView = itemView.findViewById(R.id.houseNameText)
        internal val adminNameText: TextView = itemView.findViewById(R.id.adminNameText)
        internal val sendIcon: ImageView = itemView.findViewById(R.id.sendIcon)
    }

    // Sets listener for items click
    fun setOnItemClickListener(onItemClickListener: OnItemClickListener) {
        mOnItemClickListener = onItemClickListener
    }

    // Parent activity will implement this method to respond to click events
    interface OnItemClickListener {
        fun onSendInvitation(houseId: Long)
    }
}