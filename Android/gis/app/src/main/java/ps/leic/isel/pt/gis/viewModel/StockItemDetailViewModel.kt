package ps.leic.isel.pt.gis.viewModel

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import ps.leic.isel.pt.gis.ServiceLocator
import ps.leic.isel.pt.gis.model.dtos.ErrorDto
import ps.leic.isel.pt.gis.model.dtos.StockItemDto
import ps.leic.isel.pt.gis.repositories.Resource

class StockItemDetailViewModel(private val app: Application) : AndroidViewModel(app) {

    private var stockItem: LiveData<Resource<StockItemDto, ErrorDto>>? = null

    fun init(url: String) {
        if (stockItem != null) return
        stockItem = ServiceLocator.getRepository(app.applicationContext)
                .get(StockItemDto::class.java, ErrorDto::class.java, url, TAG)
    }

    fun getStockItem(): LiveData<Resource<StockItemDto, ErrorDto>>? {
        return stockItem
    }

    fun cancel() {
        ServiceLocator.getRepository(app.applicationContext).cancelAllPendingRequests(TAG)
        stockItem = null
    }

    companion object {
        private const val TAG: String = "StockItemDetailViewModel"
    }
}