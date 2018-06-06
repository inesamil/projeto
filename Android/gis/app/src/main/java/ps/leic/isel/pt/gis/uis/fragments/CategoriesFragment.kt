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
import ps.leic.isel.pt.gis.model.dtos.CategoriesDto
import ps.leic.isel.pt.gis.model.dtos.CategoryDto
import ps.leic.isel.pt.gis.repositories.Status
import ps.leic.isel.pt.gis.uis.adapters.CategoriesAdapter
import ps.leic.isel.pt.gis.utils.ExtraUtils
import ps.leic.isel.pt.gis.utils.State
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

    private var categories: Array<CategoryDto>? = null

    private var listener: OnCategoriesFragmentInteractionListener? = null
    private lateinit var categoriesViewModel: CategoriesViewModel
    private val adapter = CategoriesAdapter()
    private lateinit var url: String

    private var state: State = State.LOADING;

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnCategoriesFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnCategoriesFragmentInteractionListener")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            url = it.getString(ExtraUtils.URL)
        }
        categoriesViewModel = ViewModelProviders.of(this).get(CategoriesViewModel::class.java)
        categoriesViewModel.init(url)
        categoriesViewModel.getCategories()?.observe(this, Observer {
            when {
                it?.status == Status.SUCCESS -> onSuccess(it.data!!)
                it?.status == Status.ERROR -> onError(it.message)
                it?.status == Status.LOADING -> {
                    state = State.LOADING
                }
            }
        })
    }

    private fun onSuccess(categories: CategoriesDto) {
        state = State.SUCCESS

        // Show progress bar or content
        showProgressBarOrContent()

        // Set Adapter
        adapter.setData(categories.categories)
        this.categories = categories.categories
    }

    private fun onError(error: String?) {
        state = State.ERROR
        error?.let{
            Log.v("APP_GIS", error)
        }
    }

    private fun showProgressBarOrContent() {
        view?.let {
            it.categoriesProgressBar.visibility = if (state == State.LOADING) View.VISIBLE else View.GONE
            it.categoriesLayout.visibility = if (state == State.SUCCESS) View.VISIBLE else View.INVISIBLE
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_categories, container, false)
        view.categoryRecyclerView.setHasFixedSize(true)
        view.categoryRecyclerView.adapter = adapter
        adapter.setOnItemClickListener(this)

        // Show progress bar or content
        showProgressBarOrContent()

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
        // Set title
        activity?.title = getString(R.string.categories)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(ExtraUtils.URL, url)
    }

    override fun onStop() {
        super.onStop()
        categoriesViewModel.cancel()
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
        categories?.let {
            val category = it[position]
            category.links.productsCategoryLink?.let {
                listener?.onCategoryInteraction(it, category.categoryName)
            }
        }
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     */
    interface OnCategoriesFragmentInteractionListener {
        fun onCategoryInteraction(url: String, categoryName: String)
    }

    /**
     * CategoriesFragment Factory
     */
    companion object {

        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param url url
         * @return A new instance of fragment CategoriesFragment.
         */
        @JvmStatic
        fun newInstance(url: String) = CategoriesFragment().apply {
            arguments = Bundle().apply {
                putString(ExtraUtils.URL, url)
            }
        }
    }
}
