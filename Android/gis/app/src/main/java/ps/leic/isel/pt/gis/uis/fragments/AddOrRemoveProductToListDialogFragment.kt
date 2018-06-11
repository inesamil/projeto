package ps.leic.isel.pt.gis.uis.fragments

import android.app.Dialog
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v7.app.AlertDialog
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.widget.ProgressBar
import android.widget.ScrollView
import android.widget.Toast
import kotlinx.android.synthetic.main.layout_add_list_product_dialog.view.*
import ps.leic.isel.pt.gis.R
import ps.leic.isel.pt.gis.model.dtos.ListDto
import ps.leic.isel.pt.gis.repositories.Status
import ps.leic.isel.pt.gis.uis.adapters.AddOrRemoveProductToListAdapter
import ps.leic.isel.pt.gis.utils.State
import ps.leic.isel.pt.gis.viewModel.ListsViewModel

class AddOrRemoveProductToListDialogFragment : DialogFragment() {

    private lateinit var url: String
    private lateinit var listsViewModel: ListsViewModel
    private var toAdd: Boolean = true

    private val adapter: AddOrRemoveProductToListAdapter = AddOrRemoveProductToListAdapter()
    private var lists: Array<ListDto>? = null

    private var state: State = State.LOADING

    private lateinit var progressBar: ProgressBar
    private lateinit var content: ScrollView

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(activity!!)
        // Get the layout inflater
        val inflater = activity!!.layoutInflater

        arguments?.let {
            url = it.getString(URL_TAG)
            toAdd = it.getBoolean(ADD_ACTION_TAG)
        }

        listsViewModel = ViewModelProviders.of(this).get(ListsViewModel::class.java)
        listsViewModel.init(url)
        listsViewModel.getLists()?.observe(this, Observer {
            when {
                it?.status == Status.SUCCESS -> {
                    lists = it.data!!.lists
                    lists?.let {
                        adapter.setData(it)
                    }
                    state = State.SUCCESS
                    showProgressBarOrContent()
                }
                it?.status == Status.ERROR -> {
                    //TODO
                    state = State.ERROR
               }
                it?.status == Status.LOADING -> state = State.LOADING
            }
        })

        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        val view = inflater.inflate(if (toAdd) ADD_DIALOG_LAYOUT else REMOVE_DIALOG_LAYOUT, null)
        builder.setView(view)
                // Add action buttons
                .setPositiveButton(if (toAdd) R.string.add else R.string.remove, { _, _ ->
                    // TODO: add list
                    Toast.makeText(view?.context, getString(R.string.functionality_not_available), Toast.LENGTH_SHORT).show()
                })
                .setNegativeButton(R.string.cancel, { _, _ -> this@AddOrRemoveProductToListDialogFragment.getDialog().cancel() })

        // Set Adapter to Recycler View
        view.listsRecyclerView.layoutManager = LinearLayoutManager(view.context)
        view.listsRecyclerView.setHasFixedSize(true)
        view.listsRecyclerView.adapter = adapter

        progressBar = view.listProductDialogProgressBar
        content = view.listsScrollView

        showProgressBarOrContent()

        return builder.create()
    }

    private fun showProgressBarOrContent() {
        progressBar.visibility = if (state == State.LOADING) View.VISIBLE else View.GONE
        content.visibility = if (state == State.SUCCESS) View.VISIBLE else View.INVISIBLE

    }

    /**
     * AddOrRemoveProductToListDialogFragment Factory
     */
    companion object {
        const val TAG: String = "AddOrRemoveProductToListDialogFragment"
        private const val URL_TAG: String = "URL"
        private const val ADD_ACTION_TAG: String = "ACTION"
        const val URL_ARG: String = "url"
        const val ADD_ACTION_ARG: String = "action"

        // Layouts
        private const val ADD_DIALOG_LAYOUT: Int = R.layout.layout_add_list_product_dialog
        private const val REMOVE_DIALOG_LAYOUT: Int = R.layout.layout_remove_list_product_dialog


        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @return A new instance of fragment NewListDialogFragment.
         */
        @JvmStatic
        fun newInstance(args: Map<String, Any>) = AddOrRemoveProductToListDialogFragment()
                .apply {
                    arguments = Bundle().apply {
                        putString(URL_TAG, args[URL_ARG] as String)
                        putBoolean(ADD_ACTION_TAG, args[ADD_ACTION_ARG] as Boolean)
                    }
                }
    }
}