package ps.leic.isel.pt.gis.uis.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import ps.leic.isel.pt.gis.R
import ps.leic.isel.pt.gis.model.dtos.MemberDto

class MembersAdapter() : RecyclerView.Adapter<MembersAdapter.ViewHolder>() {

    private lateinit var data: Array<MemberDto>

    // Inflates the cell layout from xml when needed
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_member, parent, false) as View

        return ViewHolder(view)
    }

    // Binds the data to the textview in each cell
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = data[position]
        // Fill ViewHolder
        holder.member.text = item.username
        holder.admin.visibility = if (item.administrator) View.VISIBLE else View.INVISIBLE
    }

    override fun getItemCount() = data.size

    fun setData(data: Array<MemberDto>) {
        this.data = data
        notifyDataSetChanged()
    }

    // Stores and recycles views as they are scrolled off screen
    inner class ViewHolder internal constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {
        internal val member: TextView = itemView.findViewById(R.id.memberUsernameText)
        internal val admin: TextView = itemView.findViewById(R.id.adminText)
    }
}