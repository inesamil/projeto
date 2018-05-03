package ps.leic.isel.pt.gis.uis.activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import kotlinx.android.synthetic.main.activity_lists.*
import ps.leic.isel.pt.gis.R
import ps.leic.isel.pt.gis.model.ListDTO
import ps.leic.isel.pt.gis.uis.adapters.ListsAdapter

class ListsActivity : AppCompatActivity(), ListsAdapter.OnItemClickListener {

    private lateinit var items: Array<ListDTO>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lists)

        // Set Adapter
        val adapter = ListsAdapter(items)
        listsRecyclerView.setHasFixedSize(true)
        listsRecyclerView.adapter = adapter
        adapter.setOnItemClickListener(this)
    }

    override fun onItemClick(view: View, position: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}