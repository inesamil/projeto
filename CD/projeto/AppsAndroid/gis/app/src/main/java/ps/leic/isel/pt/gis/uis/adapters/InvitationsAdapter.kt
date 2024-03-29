package ps.leic.isel.pt.gis.uis.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import ps.leic.isel.pt.gis.R
import ps.leic.isel.pt.gis.model.dtos.InvitationDto

class InvitationsAdapter(private val usernamePlaceholder: String, private val questionPlaceholder: String)
    : RecyclerView.Adapter<InvitationsAdapter.ViewHolder>() {

    private var data: Array<InvitationDto>? = null

    private lateinit var mOnItemClickListener: OnItemClickListener

    // Inflates the cell layout from xml when needed
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_invitations, parent, false) as View
        return ViewHolder(view)
    }

    // Binds the data to the textview in each cell
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        data?.let {
            val item = it[position]
            // Fill ViewHolder
            holder.invitationQuestion.text = String.format(questionPlaceholder, item.houseName)

            // Set on accept invitation listener
            holder.acceptInvitation.setOnClickListener {
                mOnItemClickListener.onAcceptInvitation(item)
            }

            // Set on decline invitation listener
            holder.declineInvitation.setOnClickListener {
                mOnItemClickListener.onDeclineInvitation(item)
            }
        }
    }

    // Total number of cells
    override fun getItemCount() = data?.size ?: 0

    fun setData(data: Array<InvitationDto>) {
        this.data = data
        notifyDataSetChanged()
    }

    // Stores and recycles views as they are scrolled off screen
    inner class ViewHolder internal constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {
        internal val invitationQuestion: TextView = itemView.findViewById(R.id.invitationQuestionText)
        internal val acceptInvitation: Button = itemView.findViewById(R.id.acceptInvitationBtn)
        internal val declineInvitation: Button = itemView.findViewById(R.id.declineInvitationBtn)
    }

    // Sets listener for items click
    fun setOnItemClickListener(onItemClickListener: OnItemClickListener) {
        mOnItemClickListener = onItemClickListener
    }

    // Parent activity will implement this method to respond to click events
    interface OnItemClickListener {
        fun onAcceptInvitation(invitation: InvitationDto)
        fun onDeclineInvitation(invitation: InvitationDto)
    }
}