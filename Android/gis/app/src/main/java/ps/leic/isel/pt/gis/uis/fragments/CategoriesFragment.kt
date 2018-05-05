package ps.leic.isel.pt.gis.uis.fragments

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_categories.view.*
import ps.leic.isel.pt.gis.R
import ps.leic.isel.pt.gis.model.CategoryDTO
import ps.leic.isel.pt.gis.uis.adapters.CategoriesAdapter

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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // TODO: Get Data
        categories = arrayOf(
                CategoryDTO(1, "Laticínios"),
                CategoryDTO(2, "Carne"),
                CategoryDTO(3, "Peixe"))
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view: View =  inflater.inflate(R.layout.fragment_categories, container, false)

        // Set Adapter
        val adapter = CategoriesAdapter(categories)
        view.categoryRecyclerView.setHasFixedSize(true)
        view.categoryRecyclerView.adapter = adapter
        adapter.setOnItemClickListener(this)

        return view
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
    
     // Listener for category item clicks (from adapter)
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
