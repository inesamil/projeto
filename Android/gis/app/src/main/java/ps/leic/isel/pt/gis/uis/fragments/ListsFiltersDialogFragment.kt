package ps.leic.isel.pt.gis.uis.fragments

import android.app.Dialog
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v4.app.FragmentManager
import android.support.v4.content.ContextCompat
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatDelegate
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import kotlinx.android.synthetic.main.layout_filters_dialog.view.*
import ps.leic.isel.pt.gis.R
import ps.leic.isel.pt.gis.model.dtos.ErrorDto
import ps.leic.isel.pt.gis.model.dtos.HouseDto
import ps.leic.isel.pt.gis.model.dtos.HousesDto
import ps.leic.isel.pt.gis.repositories.Status
import ps.leic.isel.pt.gis.uis.activities.HomeActivity
import ps.leic.isel.pt.gis.uis.adapters.FiltersHousesAdapter
import ps.leic.isel.pt.gis.utils.State
import ps.leic.isel.pt.gis.viewModel.HousesViewModel

class ListsFiltersDialogFragment : DialogFragment() {

    private lateinit var url: String
    private lateinit var housesViewModel: HousesViewModel
    private var listener: OnListsFiltersDialogFragmentInteractionListener? = null

    private val filtersHousesAdapter: FiltersHousesAdapter = FiltersHousesAdapter()

    private val listsFiltersState: ListsFilters = ListsFilters()

    private lateinit var systemListsTextView: TextView
    private lateinit var systemListImageView: ImageView

    private lateinit var userListsTextView: TextView
    private lateinit var userListImageView: ImageView

    private lateinit var sharedListsTextView: TextView
    private lateinit var sharedListImageView: ImageView

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnListsFiltersDialogFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnListsFiltersDialogFragmentInteractionListener")
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
        val builder = AlertDialog.Builder(activity!!)
        // Get the layout inflater
        val inflater = activity!!.layoutInflater

        arguments?.let {
            url = it.getString(URL_KEY)
        }

        housesViewModel = ViewModelProviders.of(this).get(HousesViewModel::class.java)
        housesViewModel.init(url)
        housesViewModel.getHouses()?.observe(this, Observer {
            when (it?.status) {
                Status.SUCCESS -> onSuccess(it.data)
                Status.UNSUCCESS -> onUnsuccess(it.apiError)
                Status.ERROR -> onError(it.message)
            }
        })

        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        val view: View = inflater.inflate(R.layout.layout_filters_dialog, null)
        builder.setView(view)
                // Add action buttons
                .setPositiveButton(R.string.apply, { _, _ ->
                    listener?.onFiltersApply(
                            listsFiltersState.systemListsSelected,
                            listsFiltersState.userListsSelected,
                            listsFiltersState.sharedListsSelected,
                            filtersHousesAdapter.getSelectedItems()
                    )
                })
                .setNegativeButton(R.string.cancel, { _, _ ->
                    this@ListsFiltersDialogFragment.dialog.cancel()
                })

        // Set RecyclerView
        view.housesFiltersRecyclerView.layoutManager = LinearLayoutManager(view.context)
        view.housesFiltersRecyclerView.setHasFixedSize(true)
        view.housesFiltersRecyclerView.adapter = filtersHousesAdapter

        // Set listeners - System Lists
        systemListImageView = view.systemListsIcon
        systemListImageView.setOnClickListener(::onSystemsListClick)
        systemListsTextView = view.systemListsText
        systemListsTextView.setOnClickListener(::onSystemsListClick)
        // Set listeners - User Lists
        userListImageView = view.userListsIcon
        userListImageView.setOnClickListener(::onUserListClick)
        userListsTextView = view.userListsText
        userListsTextView.setOnClickListener(::onUserListClick)
        // Set listeners - Shared Lists
        sharedListImageView = view.sharedListsIcon
        sharedListImageView.setOnClickListener(::onSharedListClick)
        sharedListsTextView = view.sharedListsText
        sharedListsTextView.setOnClickListener(::onSharedListClick)

        return builder.create()
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    private fun onSuccess(houses: HousesDto?) {
        houses?.let {
            filtersHousesAdapter.setData(it.houses)
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

    private inner class ListsFilters {
        var systemListsSelected: Boolean = false
        var sharedListsSelected: Boolean = false
        var userListsSelected: Boolean = false
    }

    private fun onSystemsListClick(view: View?) {
        view?.let {
            if (listsFiltersState.systemListsSelected) {
                systemListImageView.setImageResource(R.drawable.ic_computer_grey_24dp)
                it.context?.let {
                    systemListsTextView.setTextColor(ContextCompat.getColor(it, R.color.empress))
                }
            } else {
                systemListImageView.setImageResource(R.drawable.ic_computer_black_24dp)
                it.context?.let {
                    systemListsTextView.setTextColor(ContextCompat.getColor(it, R.color.primaryTextColor))
                }
            }
        }
        listsFiltersState.systemListsSelected = !listsFiltersState.systemListsSelected
    }

    private fun onUserListClick(view: View?) {
        view?.let {
            if (listsFiltersState.userListsSelected) {
                userListImageView.setImageResource(R.drawable.ic_person_outline_grey_24dp)
                it.context?.let {
                    userListsTextView.setTextColor(ContextCompat.getColor(it, R.color.empress))
                }
            } else {
                userListImageView.setImageResource(R.drawable.ic_person_outline_black_24dp)
                it.context?.let {
                    userListsTextView.setTextColor(ContextCompat.getColor(it, R.color.primaryTextColor))
                }
            }
        }
        listsFiltersState.userListsSelected = !listsFiltersState.userListsSelected
    }

    private fun onSharedListClick(view: View?) {
        view?.let {
            if (listsFiltersState.sharedListsSelected) {
                sharedListImageView.setImageResource(R.drawable.ic_people_outline_grey_24dp)
                it.context?.let {
                    sharedListsTextView.setTextColor(ContextCompat.getColor(it, R.color.empress))
                }
            } else {
                sharedListImageView.setImageResource(R.drawable.ic_people_outline_black_24dp)
                it.context?.let {
                    sharedListsTextView.setTextColor(ContextCompat.getColor(it, R.color.primaryTextColor))
                }
            }
        }
        listsFiltersState.sharedListsSelected = !listsFiltersState.sharedListsSelected
    }

    /**
     * Listener
     */
    interface OnListsFiltersDialogFragmentInteractionListener {
        fun onFiltersApply(systemLists: Boolean, userLists: Boolean, sharedLists: Boolean, houses: Array<HouseDto>?)
    }

    /**
     * ListsFiltersDialogFragment Factory
     */
    companion object {
        const val TAG: String = "ListsFiltersDialogFrag"
        private const val URL_KEY: String = "URL"
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
                        putString(URL_KEY, url)
                    }
                }
    }
}