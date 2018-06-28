package ps.leic.isel.pt.gis.viewModel

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import ps.leic.isel.pt.gis.ServiceLocator
import ps.leic.isel.pt.gis.model.body.ListProductBody
import ps.leic.isel.pt.gis.model.dtos.ErrorDto
import ps.leic.isel.pt.gis.model.dtos.ListDto
import ps.leic.isel.pt.gis.repositories.Resource

class ListDetailViewModel(private val app: Application) : AndroidViewModel(app) {

    private var listDetail: LiveData<Resource<ListDto, ErrorDto>>? = null

    fun init(url: String) {
        if (listDetail != null) return
        listDetail = ServiceLocator.getRepository(app.applicationContext)
                .get(ListDto::class.java, ErrorDto::class.java, url, TAG)
    }

    fun getListDetail(): LiveData<Resource<ListDto, ErrorDto>>? {
        return listDetail
    }

    fun addProductToList(listProduct: ListProductBody): LiveData<Resource<ListDto, ErrorDto>>? {
        listDetail?.value?.data?.actions?.addListProduct?.let {
            return ServiceLocator.getRepository(app.applicationContext)
                    .create(ListDto::class.java, ErrorDto::class.java, it.url, it.contentType, listProduct, TAG)
        }
        return null
    }

    fun updateListProduct(listProduct: ListProductBody) : LiveData<Resource<ListDto, ErrorDto>>? {
        listDetail?.value?.data?.listProducts?.find { elem -> elem.productId == listProduct.productId }?.actions?.updateListProduct?.let {
            return ServiceLocator.getRepository(app.applicationContext)
                    .update(ListDto::class.java, ErrorDto::class.java, it.url, it.contentType, listProduct, TAG)
        }
        return null
    }

    fun removeListProduct(listProduct: ListProductBody) : LiveData<Resource<ListDto, ErrorDto>>? {
        listDetail?.value?.data?.listProducts?.find { elem -> elem.productId == listProduct.productId }?.actions?.removeListProduct?.let {
            return ServiceLocator.getRepository(app.applicationContext)
                    .delete(ListDto::class.java, ErrorDto::class.java, it.url, TAG)
        }
        return null
    }

    fun cancel() {
        ServiceLocator.getRepository(app.applicationContext).cancelAllPendingRequests(TAG)
        listDetail = null
    }

    companion object {
        private const val TAG = "ListDetailViewModel"
    }
}