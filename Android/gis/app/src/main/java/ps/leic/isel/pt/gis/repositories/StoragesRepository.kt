package ps.leic.isel.pt.gis.repositories

import android.arch.lifecycle.LiveData
import ps.leic.isel.pt.gis.model.dtos.StorageDto
import ps.leic.isel.pt.gis.model.dtos.StoragesDto

interface StoragesRepository {
    fun getStorages(): LiveData<Resource<StoragesDto>>

    fun getstorage(): LiveData<Resource<StorageDto>>

    fun postStorage(): LiveData<Resource<StorageDto>>

    fun putStorage(): LiveData<Resource<StorageDto>>

    fun deleteStorage(): LiveData<Resource<StorageDto>>
}