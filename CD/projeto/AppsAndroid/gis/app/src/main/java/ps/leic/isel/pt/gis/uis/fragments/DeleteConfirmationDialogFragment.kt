package ps.leic.isel.pt.gis.uis.fragments

import android.app.Dialog
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v7.app.AlertDialog
import ps.leic.isel.pt.gis.R

class DeleteConfirmationDialogFragment : DialogFragment() {

    private var listener: OnDeleteConfirmationDialogFragmentListener? = null

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(activity!!)
        // Get the layout inflater
        val inflater = activity!!.layoutInflater

        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        val view = inflater.inflate(R.layout.layout_delete_confirmation_dialog, null)
        builder.setView(view)
                // Add action buttons
                .setPositiveButton(R.string.yes, { _, _ -> listener?.onPositiveConfirmation() })
                .setNegativeButton(R.string.no, { _, _ -> this@DeleteConfirmationDialogFragment.dialog.cancel() })

        return builder.create()
    }

    fun setOnDeleteConfirmationDialogListener(listener: OnDeleteConfirmationDialogFragmentListener) {
        this.listener = listener
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     */
    interface OnDeleteConfirmationDialogFragmentListener {
        fun onPositiveConfirmation()
    }

    /**
     * DeleteConfirmationDialogFragment Factory
     */
    companion object {
        const val TAG: String = "DeleteConfirmationDialogFragment"

        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @return A new instance of fragment DeleteConfirmationDialogFragment.
         */
        @JvmStatic
        fun newInstance() = DeleteConfirmationDialogFragment()
    }
}