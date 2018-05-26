package ps.leic.isel.pt.gis.repositories

import android.arch.lifecycle.LiveData
import ps.leic.isel.pt.gis.model.dtos.AllergiesDto
import ps.leic.isel.pt.gis.model.dtos.HouseAllergiesDto
import ps.leic.isel.pt.gis.model.dtos.HouseAllergyDto
import ps.leic.isel.pt.gis.model.dtos.StockItemsAllergenDto

interface AllergiesRepository {
    fun getAllergies(): LiveData<Resource<AllergiesDto>>

    fun getHouseAllergies(): LiveData<Resource<HouseAllergiesDto>>

    fun getStockItemsAllergen(): LiveData<Resource<StockItemsAllergenDto>>

    fun putHouseAllergen(): LiveData<Resource<HouseAllergyDto>>

    fun deleteHouseAllergen(): LiveData<Resource<HouseAllergyDto>>

}