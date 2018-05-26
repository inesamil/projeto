package ps.leic.isel.pt.gis.repositories

import android.arch.lifecycle.LiveData
import ps.leic.isel.pt.gis.model.dtos.*

interface ListsRepository {
    fun getLists(): LiveData<Resource<ListsDto>>

    fun getList(): LiveData<Resource<ListDto>>

    fun getProductsList(): LiveData<Resource<ProductsListDto>>

    fun postList(): LiveData<Resource<ListDto>>

    fun putList(): LiveData<Resource<ListDto>>

    fun putProductList(): LiveData<Resource<ProductListDto>>

    fun deleteList(): LiveData<Resource<ListDto>>

    fun deleteProductList(): LiveData<Resource<ProductListDto>>
}