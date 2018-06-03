package ps.leic.isel.pt.gis.uis.fragments

import android.app.Dialog
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.DialogInterface
import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v4.content.ContextCompat
import android.support.v4.content.res.ResourcesCompat
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatDelegate
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import kotlinx.android.synthetic.main.layout_filters_dialog.*
import kotlinx.android.synthetic.main.layout_filters_dialog.view.*
import ps.leic.isel.pt.gis.R
import ps.leic.isel.pt.gis.model.dtos.HousesDto
import ps.leic.isel.pt.gis.repositories.Status
import ps.leic.isel.pt.gis.uis.adapters.FiltersHousesAdapter
import ps.leic.isel.pt.gis.utils.ExtraUtils
import ps.leic.isel.pt.gis.viewModel.HousesViewModel

class ListsFiltersDialogFragment : DialogFragment() {

    private lateinit var url: String
    private lateinit var housesViewModel: HousesViewModel

    private val adapter: FiltersHousesAdapter = FiltersHousesAdapter()

    private val listsFiltersState: ListsFilters = ListsFilters()

    private lateinit var systemListsTextView: TextView

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
        val builder = AlertDialog.Builder(activity!!)
        // Get the layout inflater
        val inflater = activity!!.layoutInflater

        arguments?.let {
            url = it.getString(ExtraUtils.URL)
        }

        housesViewModel = ViewModelProviders.of(this).get(HousesViewModel::class.java)
        housesViewModel.init(url)
        housesViewModel.getHouses()?.observe(this, Observer {
            if (it?.status == Status.SUCCESS)
                onSuccess(it.data!!)
            else if (it?.status == Status.ERROR)
                onError(it.message)
        })

        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        val view: View = inflater.inflate(R.layout.layout_filters_dialog, null)
        builder.setView(view)
                // Add action buttons
                .setPositiveButton(R.string.apply, DialogInterface.OnClickListener { dialog, id ->
                    // TODO: apply filters
                })
                .setNegativeButton(R.string.cancel, DialogInterface.OnClickListener { dialog, id ->
                    this@ListsFiltersDialogFragment.dialog.cancel() })

        // Set RecyclerView
        view.housesFiltersRecyclerView.layoutManager = LinearLayoutManager(view.context)
        view.housesFiltersRecyclerView.setHasFixedSize(true)
        view.housesFiltersRecyclerView.adapter = adapter

        // Set listeners
        view.systemListsIcon.setOnClickListener(::onSystemsListClick)
        systemListsTextView = view.findViewById(R.id.systemListsText)
        systemListsTextView.setOnClickListener(::onSystemsListClick)

        return builder.create()
    }

    private fun onSuccess(housesDto: HousesDto) {
        adapter.setData(housesDto.houses)
    }

    private fun onError(message: String?) {
        //TODO. smthg
    }

    inner class ListsFilters {
        var systemListsSelected: Boolean = false
        var sharedListsSelected: Boolean = false
        var userListsSelected: Boolean = false
    }



    private fun onSystemsListClick(view: View?) {
        view?.let {
            if (listsFiltersState.systemListsSelected) {
                it.systemListsIcon.setImageResource(R.drawable.ic_computer_grey_24dp)
                it.context?.let {
                    systemListsTextView.setTextColor(ContextCompat.getColor(it, R.color.empress))
                }
            } else {
                it.systemListsIcon.setImageResource(R.drawable.ic_computer_black_24dp)
                it.context?.let {
                    systemListsTextView.setTextColor(ContextCompat.getColor(it, R.color.primaryTextColor))
                }
            }
        }
        listsFiltersState.systemListsSelected = !listsFiltersState.systemListsSelected
    }

    /**
     * ListsFiltersDialogFragment Factory
     */
    companion object {
        const val TAG: String = "ListsFiltersDialogFragment"

        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @return A new instance of fragment ListsFiltersDialogFragment.
         */
        @JvmStatic
        fun newInstance(url: String) =
                ListsFiltersDialogFragment().apply {
                    arguments = Bundle().apply {
                        putString(ExtraUtils.URL, url)
                    }
        }
    }
}