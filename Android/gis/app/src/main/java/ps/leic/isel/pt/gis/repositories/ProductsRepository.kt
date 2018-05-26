package ps.leic.isel.pt.gis.repositories

import android.arch.lifecycle.LiveData
import ps.leic.isel.pt.gis.model.dtos.ProductDto
import ps.leic.isel.pt.gis.model.dtos.ProductsDto

interface ProductsRepository {
    fun getProducts(): LiveData<Resource<ProductsDto>>

    fun getProduct(): LiveData<Resource<ProductDto>>
}