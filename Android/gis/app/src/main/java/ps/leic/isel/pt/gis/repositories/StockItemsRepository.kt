package ps.leic.isel.pt.gis.repositories

import android.arch.lifecycle.LiveData
import ps.leic.isel.pt.gis.model.dtos.AllergiesStockItemDto
import ps.leic.isel.pt.gis.model.dtos.StockItemDto
import ps.leic.isel.pt.gis.model.dtos.StockItemsDto

interface StockItemsRepository {
    fun getStockItems(): LiveData<Resource<StockItemsDto>>

    fun getStockItem(): LiveData<Resource<StockItemDto>>

    fun getStockItemAllergies(): LiveData<Resource<AllergiesStockItemDto>>

    fun postStockItem(): LiveData<Resource<StockItemDto>>
}