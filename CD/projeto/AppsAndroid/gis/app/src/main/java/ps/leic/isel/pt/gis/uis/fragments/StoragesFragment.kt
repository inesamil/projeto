package ps.leic.isel.pt.gis.uis.fragments

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.constraint.ConstraintLayout
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import kotlinx.android.synthetic.main.fragment_storages.*
import kotlinx.android.synthetic.main.fragment_storages.view.*
import ps.leic.isel.pt.gis.R
import ps.leic.isel.pt.gis.model.dtos.ErrorDto
import ps.leic.isel.pt.gis.model.dtos.StoragesDto
import ps.leic.isel.pt.gis.repositories.Status
import ps.leic.isel.pt.gis.uis.adapters.StoragesAdapter
import ps.leic.isel.pt.gis.utils.State
import ps.leic.isel.pt.gis.viewModel.StoragesViewModel

/**
 * A simple [Fragment] subclass.
 * Use the [StoragesFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class StoragesFragment : Fragment() {

    private val adapter = StoragesAdapter()
    private lateinit var url: String
    private lateinit var storagesViewModel: StoragesViewModel

    private var state: State = State.LOADING
    private lateinit var progressBar: ProgressBar
    private lateinit var content: ConstraintLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            url = it.getString(URL_KEY)
        }
        storagesViewModel = ViewModelProviders.of(this).get(StoragesViewModel::class.java)
        storagesViewModel.init(url)
        storagesViewModel.getStorages()?.observe(this, Observer {
            when (it?.status) {
                Status.SUCCESS -> onSuccess(it.data)
                Status.UNSUCCESS -> onUnsuccess(it.apiError)
                Status.ERROR -> onError(it.message)
                Status.LOADING -> {
                    state = State.LOADING
                }
            }
        })
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_storages, container, false)
        view.storagesRecyclerView.layoutManager = LinearLayoutManager(view.context)
        view.storagesRecyclerView.setHasFixedSize(true)
        view.storagesRecyclerView.adapter = adapter

        progressBar = view.storagesProgressBar
        content = view.storagesLayout

        // Show progress bar or content
        showProgressBarOrContent()

        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        savedInstanceState?.let {
            url = it.getString(URL_KEY)
        }
    }

    override fun onStart() {
        super.onStart()
        activity?.title = getString(R.string.storages)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(URL_KEY, url)
    }

    override fun onStop() {
        super.onStop()
        storagesViewModel.cancel()
    }

    /***
     * Auxiliary Methods
     ***/

    private fun onSuccess(storages: StoragesDto?) {
        storages?.let {
            state = State.SUCCESS

            // Show progress bar or content
            showProgressBarOrContent()

            storagesLayout.visibility = View.VISIBLE

            adapter.setData(it.storages)
        }
    }

    private fun onUnsuccess(error: ErrorDto?) {
        state = State.ERROR
        error?.let {
            Log.e(TAG, it.developerErrorMessage)
            onError(it.message)
        }
    }

    private fun onError(message: String?) {
        state = State.ERROR
        message?.let {
            Log.e(TAG, it)
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
            fragmentManager?.popBackStack(TAG, FragmentManager.POP_BACK_STACK_INCLUSIVE)
        }
    }

    private fun showProgressBarOrContent() {
        progressBar.visibility = if (state == State.LOADING) View.VISIBLE else View.GONE
        content.visibility = if (state == State.SUCCESS) View.VISIBLE else View.INVISIBLE
    }

    /**
     * StoragesFragment Factory
     */
    companion object {
        const val TAG: String = "StoragesFragment"
        private const val URL_KEY: String = "URL"
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param url URL
         * @return A new instance of fragment StoragesFragment.
         */
        @JvmStatic
        fun newInstance(url: String) =
                StoragesFragment().apply {
                    arguments = Bundle().apply {
                        putString(URL_KEY, url)
                    }
                }
    }
}
