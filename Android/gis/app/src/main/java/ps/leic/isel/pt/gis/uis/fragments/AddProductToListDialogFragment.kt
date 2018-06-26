package ps.leic.isel.pt.gis.uis.fragments

import android.app.Dialog
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v7.app.AlertDialog
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.widget.ProgressBar
import android.widget.ScrollView
import kotlinx.android.synthetic.main.layout_add_list_product_dialog.view.*
import ps.leic.isel.pt.gis.R
import ps.leic.isel.pt.gis.model.ListProduct
import ps.leic.isel.pt.gis.model.dtos.ListDto
import ps.leic.isel.pt.gis.uis.adapters.AddProductToListAdapter
import ps.leic.isel.pt.gis.utils.State
import ps.leic.isel.pt.gis.viewModel.ListsViewModel

class AddProductToListDialogFragment : DialogFragment(), AddProductToListAdapter.OnItemClickListener {

    private lateinit var url: String
    private lateinit var listsViewModel: ListsViewModel
    private var toAdd: Boolean = true
    private var productId: Int? = null

    private val adapter: AddProductToListAdapter = AddProductToListAdapter()
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

        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        val view = inflater.inflate(R.layout.layout_add_list_product_dialog, null)
        builder.setView(view)

        // Set Adapter to Recycler View
        view.productsRecyclerView.layoutManager = LinearLayoutManager(view.context)
        view.productsRecyclerView.setHasFixedSize(true)
        view.productsRecyclerView.adapter = adapter
        adapter.setOnItemClickListener(this)

        progressBar = view.listProductDialogProgressBar
        content = view.productsScrollView

        showProgressBarOrContent()

        return builder.create()
    }

    private fun showProgressBarOrContent() {
        progressBar.visibility = if (state == State.LOADING) View.VISIBLE else View.GONE
        content.visibility = if (state == State.SUCCESS) View.VISIBLE else View.INVISIBLE

    }

    override fun onItemActionClick(listProduct: ListProduct) {
        if (listProduct.quantity > 0) {
            //TODO
        }
    }

    /**
     * AddProductToListDialogFragment Factory
     */
    companion object {
        const val TAG: String = "AddProductToListDialogFragment"
        private const val URL_TAG: String = "URL"
        private const val ADD_ACTION_TAG: String = "ACTION"
        const val URL_ARG: String = "url"
        const val ADD_ACTION_ARG: String = "action"



        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @return A new instance of fragment NewListDialogFragment.
         */
        @JvmStatic
        fun newInstance(args: Map<String, Any>) = AddProductToListDialogFragment()
                .apply {
                    arguments = Bundle().apply {
                        putString(URL_TAG, args[URL_ARG] as String)
                        putBoolean(ADD_ACTION_TAG, args[ADD_ACTION_ARG] as Boolean)
                    }
                }
    }
}