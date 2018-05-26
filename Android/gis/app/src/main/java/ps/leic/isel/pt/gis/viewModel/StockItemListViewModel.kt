package ps.leic.isel.pt.gis.viewModel

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import ps.leic.isel.pt.gis.model.dtos.StockItemsDto
import ps.leic.isel.pt.gis.repositories.Resource
import ps.leic.isel.pt.gis.repositories.StockItemsRepository

class StockItemListViewModel(application: Application) : AndroidViewModel(application) {

    private val stockItemsRepo: StockItemsRepository = StockItemsRepositoryImpl(application)

    private lateinit var stockItems: LiveData<Resource<StockItemsDto>>

    fun init(url: String) {
        if (::stockItems.isInitialized) return
        stockItems = stockItemsRepo.getStockItems(url)
    }

    fun getStockItems(): LiveData<Resource<StockItemsDto>> {
        return stockItems
    }
}