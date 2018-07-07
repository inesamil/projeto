package ps.leic.isel.pt.gis.uis.fragments

import android.app.Dialog
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v4.app.FragmentManager
import android.support.v7.app.AlertDialog
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import kotlinx.android.synthetic.main.layout_new_list_dialog.view.*
import ps.leic.isel.pt.gis.R
import ps.leic.isel.pt.gis.model.List
import ps.leic.isel.pt.gis.model.dtos.ErrorDto
import ps.leic.isel.pt.gis.model.dtos.HouseDto
import ps.leic.isel.pt.gis.model.dtos.HousesDto
import ps.leic.isel.pt.gis.repositories.Status
import ps.leic.isel.pt.gis.uis.activities.HomeActivity
import ps.leic.isel.pt.gis.viewModel.HousesViewModel

class NewListDialogFragment : DialogFragment() {

    private lateinit var url: String

    private lateinit var housesViewModel: HousesViewModel

    private var houses: Array<HouseDto>? = null

    private lateinit var housesSpinner: Spinner

    private var listener: OnNewListDialogFragmentInteractionListener? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnNewListDialogFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnNewListDialogFragmentInteractionListener")
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(activity!!)
        // Get the layout inflater
        val inflater = activity!!.layoutInflater

        arguments?.let {
            url = it.getString(URL_KEY)
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
                .setNegativeButton(R.string.cancel, { _, _ -> this@NewListDialogFragment.dialog.cancel() })

        housesSpinner = view.newListHousesSpinner

        return builder.create()
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    /***
     * Auxiliary Methods
     ***/
    private fun addList(view: View) {
        houses?.get(housesSpinner.selectedItemPosition)?.let {
            val houseId: Long = it.houseId
            val listName: String = view.listNameEditText.text.toString()
            val shareable: Boolean = !view.shareableListSwitch.isChecked

            listener?.onAddList(List(houseId, listName, shareable))
        }
    }

    private fun getHouses() {
        housesViewModel = ViewModelProviders.of(this).get(HousesViewModel::class.java)
        housesViewModel.init(url)
        housesViewModel.getHouses()?.observe(this, Observer {
            when (it?.status) {
                Status.SUCCESS -> onSuccess(it.data)
                Status.UNSUCCESS -> onUnsuccess(it.apiError)
                Status.ERROR -> onError(it.message)
            }
        })
    }

    private fun onSuccess(houses: HousesDto?) {
        houses?.let {
            this.houses = it.houses

            this.houses?.let {
                val spinnerAdapter = ArrayAdapter<String>(housesSpinner.context, android.R.layout.simple_spinner_item, it.map { house -> house.name })
                spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                housesSpinner.adapter = spinnerAdapter
                housesSpinner.setSelection(0)
            }
        }
    }

    private fun onUnsuccess(error: ErrorDto?) {
        error?.let {
            Log.e(TAG, it.developerErrorMessage)
            onError(it.message)
        }
    }

    private fun onError(message: String?) {
        message?.let {
            Log.e(TAG, it)
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
            fragmentManager?.popBackStack(TAG, FragmentManager.POP_BACK_STACK_INCLUSIVE)
        }
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     */
    interface OnNewListDialogFragmentInteractionListener {
        fun onAddList(list: List)
    }

    /**
     * NewListDialogFragment Factory
     */
    companion object {
        const val TAG: String = "NewListDialogFragment"
        private const val URL_KEY: String = "URL"
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @return A new instance of fragment NewListDialogFragment.
         */
        @JvmStatic
        fun newInstance(url: String) = NewListDialogFragment().apply {
            arguments = Bundle().apply {
                putString(URL_KEY, url)
            }
        }
    }
}