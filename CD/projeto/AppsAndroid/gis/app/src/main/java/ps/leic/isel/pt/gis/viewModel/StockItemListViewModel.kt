package ps.leic.isel.pt.gis.viewModel

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import ps.leic.isel.pt.gis.ServiceLocator
import ps.leic.isel.pt.gis.model.dtos.ErrorDto
import ps.leic.isel.pt.gis.model.dtos.StockItemsDto
import ps.leic.isel.pt.gis.repositories.Resource

class StockItemListViewModel(private val app: Application) : AndroidViewModel(app) {

    private var stockItems: LiveData<Resource<StockItemsDto, ErrorDto>>? = null
    private lateinit var url: String

    fun init(url: String) {
        if (stockItems != null && this.url == url) return
        if (stockItems != null) cancel()
        this.url = url
        stockItems = ServiceLocator.getRepository(app.applicationContext)
                .get(StockItemsDto::class.java, ErrorDto::class.java, url, TAG)
    }

    fun getStockItems(): LiveData<Resource<StockItemsDto, ErrorDto>>? {
        return stockItems
    }

    fun cancel() {
        ServiceLocator.getRepository(app.applicationContext).cancelAllPendingRequests(TAG)
        stockItems = null
    }

    companion object {
        private const val TAG: String = "StockItemListViewModel"
    }
}