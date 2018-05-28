package ps.leic.isel.pt.gis.uis.fragments

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_list.view.*
import ps.leic.isel.pt.gis.R
import ps.leic.isel.pt.gis.model.ListDTO
import ps.leic.isel.pt.gis.model.ListProductDTO
import ps.leic.isel.pt.gis.model.dtos.ListDto
import ps.leic.isel.pt.gis.model.dtos.ProductListDto
import ps.leic.isel.pt.gis.model.dtos.ProductsListDto
import ps.leic.isel.pt.gis.repositories.Status
import ps.leic.isel.pt.gis.uis.adapters.ListDetailAdapter
import ps.leic.isel.pt.gis.utils.ExtraUtils
import ps.leic.isel.pt.gis.viewModel.ListDetailViewModel

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [ListDetailFragment.OnListDetailFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [ListDetailFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class ListDetailFragment : Fragment(), ListDetailAdapter.OnItemClickListener {

    private lateinit var list: ListDto
    private lateinit var listProducts: ProductsListDto

    private var listener: OnListDetailFragmentInteractionListener? = null
    private lateinit var listDetailViewModel: ListDetailViewModel
    private lateinit var url: String
    private val adapter = ListDetailAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            url = it.getString(ExtraUtils.URL)
        }
        listDetailViewModel = ViewModelProviders.of(this).get(ListDetailViewModel::class.java)
        listDetailViewModel.init(url)
        listDetailViewModel.getListDetail()?.observe(this, Observer {
            if (it?.status == Status.SUCCESS)
                onSuccess(it.data!!)
            else if (it?.status == Status.ERROR)
                onError(it.message)
        })
    }

    private fun onSuccess(productsList: ProductsListDto) {
        adapter.setData(productsList.productsList)
    }

    private fun onError(error: String?) {
        Log.v("APP_GIS", error)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_list, container, false)
        view.listRecyclerView.layoutManager = LinearLayoutManager(view.context)
        view.listRecyclerView.setHasFixedSize(true)
        view.listRecyclerView.adapter = adapter
        adapter.setOnItemClickListener(this)
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        savedInstanceState?.let {
            url = it.getString(ExtraUtils.URL)
        }
    }

    override fun onStart() {
        super.onStart()
        activity?.title = list.listName
    }

    override fun onStop() {
        super.onStop()
        listDetailViewModel.cancel()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnListDetailFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnListDetailFragmentInteractionListener")
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(ExtraUtils.URL, url)
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    /***
     * Listeners
     ***/

    // NfcListener for list item clicks (from adapter)
    override fun onItemClick(view: View, position: Int) {
        val listProduct: ProductListDto = listProducts.productsList[position]
        listener?.onListProductInteraction(listProduct)
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     */
    interface OnListDetailFragmentInteractionListener {
        fun onListProductInteraction(listProduct: ProductListDto)
    }

    /**
     * ListDetailFragment Factory
     */
    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param url url
         * @return A new instance of fragment ListDetailFragment.
         */
        @JvmStatic
        fun newInstance(url: String) =
                ListDetailFragment().apply {
                    arguments = Bundle().apply {
                        putString(ExtraUtils.URL, url)
                    }
                }
    }
}
