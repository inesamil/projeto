package ps.leic.isel.pt.gis.activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import ps.leic.isel.pt.gis.R
import ps.leic.isel.pt.gis.adapters.ListsAdapter

class ListsActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager

    private lateinit var items: Array<Any>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lists)

        items = arrayOf("Groceries List", "Dark List")

        viewManager = LinearLayoutManager(this)
        viewAdapter = ListsAdapter(items)

        recyclerView = findViewById<RecyclerView>(R.id.my_recycler_view_lists).apply {
            // use this setting to improve performance if you know that changes
            // in content do not change the layout size of the RecyclerView
            setHasFixedSize(true)

            // use a linear layout manager
            layoutManager = viewManager

            // specify an viewAdapter (see also next example)
            adapter = viewAdapter

        }
    }
}