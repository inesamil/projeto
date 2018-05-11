package ps.leic.isel.pt.gis.uis.fragments

import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v7.app.AlertDialog
import kotlinx.android.synthetic.main.layout_new_house_dialog.view.*
import ps.leic.isel.pt.gis.R
import ps.leic.isel.pt.gis.utils.EditTextUtils
import ps.leic.isel.pt.gis.utils.RestrictionsUtils

class NewHouseDialogFragment : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(activity!!)
        // Get the layout inflater
        val inflater = activity!!.layoutInflater

        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        val view = inflater.inflate(R.layout.layout_new_house_dialog, null)
        builder.setView(view)
                // Add action buttons
                .setPositiveButton(R.string.add, DialogInterface.OnClickListener { dialog, id ->
                    // TODO: add house
                })
                .setNegativeButton(R.string.cancel, DialogInterface.OnClickListener { dialog, id -> this@NewHouseDialogFragment.getDialog().cancel() })

        // Set Plus buttons listeners
        view.babiesPlusBtn.setOnClickListener{
            EditTextUtils.incNumberText(
                    view.babiesNumEditText,
                    RestrictionsUtils.characteristicsMinValue,
                    RestrictionsUtils.characteristicsMaxValue)
        }

        view.childrenPlusBtn.setOnClickListener {
            EditTextUtils.incNumberText(
                    view.childrenNumEditText,
                    RestrictionsUtils.characteristicsMinValue,
                    RestrictionsUtils.characteristicsMaxValue)
        }

        view.adultsPlusBtn.setOnClickListener{
            EditTextUtils.incNumberText(
                    view.adultsNumEditText,
                    RestrictionsUtils.characteristicsMinValue,
                    RestrictionsUtils.characteristicsMaxValue)
        }

        view.seniorsPlusBtn.setOnClickListener{
            EditTextUtils.incNumberText(
                    view.seniorNumEditText,
                    RestrictionsUtils.characteristicsMinValue,
                    RestrictionsUtils.characteristicsMaxValue)
        }

        // Set Minus buttons listeners
        view.babiesMinusBtn.setOnClickListener{
            EditTextUtils.decNumberText(
                    view.babiesNumEditText,
                    RestrictionsUtils.characteristicsMinValue,
                    RestrictionsUtils.characteristicsMaxValue)
        }
        view.childrenMinusBtn.setOnClickListener{
            EditTextUtils.decNumberText(
                    view.childrenNumEditText,
                    RestrictionsUtils.characteristicsMinValue,
                    RestrictionsUtils.characteristicsMaxValue)
        }

        view.adultsMinusBtn.setOnClickListener {
            EditTextUtils.decNumberText(
                    view.adultsNumEditText,
                    RestrictionsUtils.characteristicsMinValue,
                    RestrictionsUtils.characteristicsMaxValue)
        }

        view.seniorsMinusBtn.setOnClickListener {
            EditTextUtils.decNumberText(
                    view.seniorNumEditText,
                    RestrictionsUtils.characteristicsMinValue,
                    RestrictionsUtils.characteristicsMaxValue)
        }
        return builder.create()
    }



    /**
     * NewHouseDialogFragment Factory
     */
    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @return A new instance of fragment NewListDialogFragment.
         */
        @JvmStatic
        fun newInstance() = NewHouseDialogFragment()
    }
}