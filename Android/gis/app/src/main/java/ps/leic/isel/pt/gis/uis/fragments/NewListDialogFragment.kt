package ps.leic.isel.pt.gis.uis.fragments

import android.app.Dialog
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v7.app.AlertDialog
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import kotlinx.android.synthetic.main.fragment_write_nfc_tag.view.*
import ps.leic.isel.pt.gis.R
import ps.leic.isel.pt.gis.model.dtos.HouseDto
import ps.leic.isel.pt.gis.model.dtos.HousesDto
import ps.leic.isel.pt.gis.repositories.Status
import ps.leic.isel.pt.gis.utils.ExtraUtils
import ps.leic.isel.pt.gis.utils.State
import ps.leic.isel.pt.gis.viewModel.HousesViewModel

class NewListDialogFragment : DialogFragment() {

    private lateinit var url: String
    private lateinit var housesViewModel: HousesViewModel

    private var houses: Array<HouseDto>? = null

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(activity!!)
        // Get the layout inflater
        val inflater = activity!!.layoutInflater

        arguments?.let {
            url = it.getString(ExtraUtils.URL)
        }

        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        val view = inflater.inflate(R.layout.layout_new_list_dialog, null)
        builder.setView(view)
                // Add action buttons
                .setPositiveButton(R.string.add, { _, _ ->
                    // TODO: add list
                    Toast.makeText(view?.context, "Functionality Not Yet Available", Toast.LENGTH_SHORT).show()
                })
                .setNegativeButton(R.string.cancel, { _, _ -> this@NewListDialogFragment.getDialog().cancel() })

        housesViewModel = ViewModelProviders.of(this).get(HousesViewModel::class.java)
        housesViewModel.init(url)
        housesViewModel.getHouses()?.observe(this, Observer {
            when {
                it?.status == Status.SUCCESS -> onSuccess(it.data!!)
                it?.status == Status.ERROR -> onError(it.message)
            }
        })

        return builder.create()
    }

    private fun onError(error: String?) {

    }

    private fun onSuccess(housesDto: HousesDto) {
        houses = housesDto.houses

        houses?.let {
            val spinnerAdapter = ArrayAdapter<String>(view?.context, android.R.layout.simple_spinner_item, it.map { house -> house.name })
            spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            view?.productSpinner?.adapter = spinnerAdapter
            view?.productSpinner?.setSelection(0)
        }
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