package ps.leic.isel.pt.gis.uis.fragments

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_categories.view.*
import ps.leic.isel.pt.gis.R
import ps.leic.isel.pt.gis.model.CategoryDTO
import ps.leic.isel.pt.gis.model.dtos.CategoriesDto
import ps.leic.isel.pt.gis.repositories.Status
import ps.leic.isel.pt.gis.uis.adapters.CategoriesAdapter
import ps.leic.isel.pt.gis.viewModel.CategoriesViewModel

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [CategoriesFragment.OnCategoriesFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [CategoriesFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class CategoriesFragment : Fragment(), CategoriesAdapter.OnItemClickListener {

    private lateinit var categories: Array<CategoryDTO>

    private var listener: OnCategoriesFragmentInteractionListener? = null
    private var categoriesViewModel: CategoriesViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        categoriesViewModel = ViewModelProviders.of(this).get(CategoriesViewModel::class.java)
        val url = ""
        categoriesViewModel?.init(url)
        categoriesViewModel?.getCategories()?.observe(this, Observer {
            if (it?.status == Status.SUCCESS)
                onSuccess(it.data!!)
            else if (it?.status == Status.ERROR)
                onError(it.message)
        })
        // TODO: Get Data
        categories = arrayOf(
                CategoryDTO(1, "Laticínios"),
                CategoryDTO(2, "Carne"),
                CategoryDTO(3, "Peixe"))
    }

    private fun onSuccess(categories: CategoriesDto) {
        // Set Adapter
        val adapter = CategoriesAdapter(categories.categories)
        view?.let {
            it.categoryRecyclerView.setHasFixedSize(true)
            it.categoryRecyclerView.adapter = adapter
        }
        adapter.setOnItemClickListener(this)
    }

    private fun onError(error: String?) {
        Log.v("APP_GIS", error)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_categories, container, false)
    }

    override fun onStart() {
        super.onStart()
        // Set title
        activity?.title = getString(R.string.categories)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnCategoriesFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnCategoriesFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    /***
     * Listeners
     ***/

    // NfcListener for category item clicks (from adapter)
    override fun onItemClick(view: View, position: Int) {
        val category: CategoryDTO = categories[position]
        listener?.onCategoryInteraction(category)
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     */
    interface OnCategoriesFragmentInteractionListener {
        fun onCategoryInteraction(category: CategoryDTO)
    }

    /**
     * CategoriesFragment Factory
     */
    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @return A new instance of fragment CategoriesFragment.
         */
        @JvmStatic
        fun newInstance() = CategoriesFragment()
    }
}
