package ps.leic.isel.pt.gis.uis.adapters

import android.content.Context
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import ps.leic.isel.pt.gis.R
import ps.leic.isel.pt.gis.model.dtos.HouseDto

class HousesAdapter(private val username: String, private val usernamePlaceholder: String) : RecyclerView.Adapter<HousesAdapter.ViewHolder>() {

    private lateinit var mOnItemClickListener: OnItemClickListener
    private var data: Array<HouseDto>? = null

    // Inflates the cell layout from xml when needed
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_houses, parent, false) as View
        return ViewHolder(view)
    }

    // Binds the data to the textview in each cell
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        data?.let {
            val item = it[position]
            // Fill ViewHolder
            holder.houseName.text = item.name
            holder.babiesNumber.text = item.characteristics?.babiesNumber.toString()
            holder.childrenNumber.text = item.characteristics?.childrenNumber.toString()
            holder.adultsNumber.text = item.characteristics?.adultsNumber.toString()
            holder.seniorsNumber.text = item.characteristics?.seniorsNumber.toString()
            //Pass data to the Adapter
            holder.membersAdapter.setData(item.members)
            // Show add member button if logged in user is admin
            if (item.members?.find { member -> member.username == username && member.administrator == true } != null) {
                holder.addMemberButton.visibility = View.VISIBLE
                holder.addMemberButton.setOnClickListener { mOnItemClickListener.onInvitationClick(item) }
            } else {
                holder.addMemberButton.visibility = View.GONE
            }
        }
    }

    fun setData(data: Array<HouseDto>) {
        this.data = data
        notifyDataSetChanged()
    }

    // Total number of cells
    override fun getItemCount() = data?.size ?: 0

    // Stores and recycles views as they are scrolled off screen
    inner class ViewHolder internal constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {
        internal val houseName: TextView = itemView.findViewById(R.id.houseNameText)
        internal val babiesNumber: TextView = itemView.findViewById(R.id.babiesNumberText)
        internal val childrenNumber: TextView = itemView.findViewById(R.id.childrenNumberText)
        internal val adultsNumber: TextView = itemView.findViewById(R.id.adultsNumberText)
        internal val seniorsNumber: TextView = itemView.findViewById(R.id.seniorsNumberText)
        internal val storages: Button = itemView.findViewById(R.id.storageBtn)
        internal val allergies: Button = itemView.findViewById(R.id.allergiesBtn)
        internal val addMemberButton: ImageButton = itemView.findViewById(R.id.addMemberButton)
        internal val membersAdapter: MembersAdapter

        init {
            val context: Context = itemView.context
            // Set inner member list Adapter
            val membersRecyclerView: RecyclerView = itemView.findViewById(R.id.membersRecyclerView)
            membersAdapter = MembersAdapter(usernamePlaceholder)
            membersRecyclerView.layoutManager = LinearLayoutManager(context)
            membersRecyclerView.setHasFixedSize(true)
            membersRecyclerView.adapter = membersAdapter
            storages.setOnClickListener {
                mOnItemClickListener.onStoragesClick(it, adapterPosition)
            }
            allergies.setOnClickListener {
                mOnItemClickListener.onAllergiesClick(it, adapterPosition)
            }
        }
    }

    // Sets listener for items click
    fun setOnItemClickListener(onItemClickListener: OnItemClickListener) {
        mOnItemClickListener = onItemClickListener
    }

    // Parent activity will implement this method to respond to click events
    interface OnItemClickListener {
        fun onStoragesClick(view: View, position: Int)
        fun onAllergiesClick(view: View, position: Int)
        fun onInvitationClick(house: HouseDto)
    }
}