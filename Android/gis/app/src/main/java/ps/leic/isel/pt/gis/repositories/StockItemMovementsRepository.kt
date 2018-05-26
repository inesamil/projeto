package ps.leic.isel.pt.gis.repositories

import android.arch.lifecycle.LiveData
import ps.leic.isel.pt.gis.model.dtos.MovementDto
import ps.leic.isel.pt.gis.model.dtos.MovementsDto

interface StockItemMovementsRepository {
    fun getStockItemMovements(): LiveData<Resource<MovementsDto>>

    fun postStockItemMovement(): LiveData<Resource<MovementDto>>
}