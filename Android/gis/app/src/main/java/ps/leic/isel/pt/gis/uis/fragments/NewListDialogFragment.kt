package ps.leic.isel.pt.gis.uis.fragments

import android.app.Dialog
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v7.app.AlertDialog
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import kotlinx.android.synthetic.main.layout_new_list_dialog.view.*
import ps.leic.isel.pt.gis.R
import ps.leic.isel.pt.gis.model.body.ListBody
import ps.leic.isel.pt.gis.model.dtos.HouseDto
import ps.leic.isel.pt.gis.model.dtos.HousesDto
import ps.leic.isel.pt.gis.repositories.Status
import ps.leic.isel.pt.gis.utils.ExtraUtils
import ps.leic.isel.pt.gis.viewModel.HousesViewModel
import ps.leic.isel.pt.gis.viewModel.ListsViewModel

class NewListDialogFragment : DialogFragment() {

    private lateinit var url: String

    private lateinit var housesViewModel: HousesViewModel
    private var listsViewModel: ListsViewModel? = null

    private var houses: Array<HouseDto>? = null

    private lateinit var housesSpinner: Spinner

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(activity!!)
        // Get the layout inflater
        val inflater = activity!!.layoutInflater

        arguments?.let {
            url = it.getString(ExtraUtils.URL)
        }

        getHouses()

        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        val view = inflater.inflate(R.layout.layout_new_list_dialog, null)
        builder.setView(view)
                // Add action buttons
                .setPositiveButton(R.string.add, { _, _ ->
                    addList(view)
                })
                .setNegativeButton(R.string.cancel, { _, _ -> this@NewListDialogFragment.getDialog().cancel() })

        housesSpinner = view.newListHousesSpinner

        return builder.create()
    }

    private fun addList(view: View) {
        houses?.get(housesSpinner.selectedItemPosition)?.let {
            val houseId: Long = it.houseId
            val listName: String = view.listNameEditText.text.toString()
            val shareable: Boolean = !view.shareableListSwitch.isChecked

            val list = ListBody(houseId, listName, shareable)

            listsViewModel = ViewModelProviders.of(this).get(ListsViewModel::class.java)
            listsViewModel?.addList(list)?.observe(this, Observer {
                when {
                    it?.status == Status.SUCCESS -> {
                        Toast.makeText(context, getString(R.string.Authores_Names), Toast.LENGTH_SHORT).show()
                    }
                    it?.status == Status.ERROR -> {
                        Toast.makeText(context, getString(R.string.please_fill_in_all_required_fields), Toast.LENGTH_SHORT).show()
                    }
                }
            })
        }
    }

    private fun getHouses() {
        housesViewModel = ViewModelProviders.of(this).get(HousesViewModel::class.java)
        housesViewModel.init(url)
        housesViewModel.getHouses()?.observe(this, Observer {
            when {
                it?.status == Status.SUCCESS -> onSuccess(it.data!!)
                it?.status == Status.ERROR -> onError(it.message)
            }
        })
    }

    private fun onSuccess(housesDto: HousesDto) {
        houses = housesDto.houses

        houses?.let {
            val spinnerAdapter = ArrayAdapter<String>(housesSpinner.context, android.R.layout.simple_spinner_item, it.map { house -> house.name })
            spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            housesSpinner.adapter = spinnerAdapter
            housesSpinner.setSelection(0)
        }
    }


    private fun onError(error: String?) {
        //TODO
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
        fun newInstance(url: String) = NewListDialogFragment().apply {
            arguments = Bundle().apply {
                putString(ExtraUtils.URL, url)
            }
        }
    }
}