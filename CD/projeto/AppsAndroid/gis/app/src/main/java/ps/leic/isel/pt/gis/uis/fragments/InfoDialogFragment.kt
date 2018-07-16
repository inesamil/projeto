package ps.leic.isel.pt.gis.uis.fragments

import android.app.Dialog
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v7.app.AlertDialog
import kotlinx.android.synthetic.main.layout_info_dialog.view.*
import ps.leic.isel.pt.gis.R

class InfoDialogFragment : DialogFragment() {

    private lateinit var title: String
    private lateinit var info: String

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(activity!!)
        // Get the layout inflater
        val inflater = activity!!.layoutInflater

        arguments?.let {
            title = it.getString(TITLE_KEY)
            info = it.getString(INFO_KEY)
        }

        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        val view = inflater.inflate(R.layout.layout_info_dialog, null)
        builder.setView(view)

        // Set content
        view.titleText.text = title
        view.infoText.text = info

        return builder.create()
    }

    /**
     * InfoDialogFragment Factory
     */
    companion object {
        const val TAG: String = "InfoDialogFragment"
        private const val TITLE_KEY: String = "TITLE"
        private const val INFO_KEY: String = "INFO"

        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @return A new instance of fragment InfoDialogFragment.
         */
        @JvmStatic
        fun newInstance(title: String, info: String) = InfoDialogFragment().apply {
            arguments = Bundle().apply {
                putString(TITLE_KEY, title)
                putString(INFO_KEY, info)
            }
        }
    }
}