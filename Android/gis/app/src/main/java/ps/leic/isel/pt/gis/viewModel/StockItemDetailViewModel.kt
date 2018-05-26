package ps.leic.isel.pt.gis.viewModel

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import ps.leic.isel.pt.gis.model.dtos.StockItemDto
import ps.leic.isel.pt.gis.repositories.Resource
import ps.leic.isel.pt.gis.repositories.StockItemsRepository

class StockItemDetailViewModel(application: Application) : AndroidViewModel(application) {

    private val stockItemsRepo: StockItemsRepository = StockItemsRepositoryImpl(application)

    private lateinit var stockItem: LiveData<Resource<StockItemDto>>

    fun init(url: String) {
        if (::stockItem.isInitialized) return
        stockItem = stockItemsRepo.getStockItem(url)
    }

    fun getStockItem(): LiveData<Resource<StockItemDto>> {
        return stockItem
    }
}