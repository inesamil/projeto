package ps.leic.isel.pt.gis.uis.fragments

import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v7.app.AlertDialog
import ps.leic.isel.pt.gis.R

class NewListDialogFragment : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(activity!!)
        // Get the layout inflater
        val inflater = activity!!.layoutInflater

        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        builder.setView(inflater.inflate(R.layout.layout_new_list_dialog, null))
                // Add action buttons
                .setPositiveButton(R.string.add, DialogInterface.OnClickListener { dialog, id ->
                    // TODO: add list
                })
                .setNegativeButton(R.string.cancel, DialogInterface.OnClickListener { dialog, id -> this@NewListDialogFragment.getDialog().cancel() })
        return builder.create()
    }

    /**
     * NewListDialogFragment Factory
     */
    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @return A new instance of fragment NewListDialogFragment.
         */
        @JvmStatic
        fun newInstance() = NewListDialogFragment()
    }
}