package ps.leic.isel.pt.gis.model.dtos

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import ps.leic.isel.pt.gis.hypermedia.siren.Siren
import ps.leic.isel.pt.gis.hypermedia.siren.subentities.Action
import ps.leic.isel.pt.gis.hypermedia.siren.subentities.Link

class StorageDto(siren: Siren) {
    val houseId: Long
    val storageId: Short
    val name: String
    val temperature: TemperatureStorageDto
    val actions: StorageActions
    val links: StorageLinks

    init {
        val properties = siren.properties
        houseId = (properties?.get(houseIdLabel) as Int).toLong()
        storageId = (properties[storageIdLabel] as Int).toShort()
        name = properties[nameLabel] as String
        temperature = mapper.convertValue<TemperatureStorageDto>(properties[temperatureLabel], TemperatureStorageDto::class.java)
        actions = StorageActions(siren.actions)
        links = StorageLinks(siren.links)
    }

    class StorageActions(actions: Array<Action>?) {
        val updateStorage: Action? = actions?.find {
            it.name == updateStorageLabel
        }
        val deleteStorage: Action? = actions?.find {
            it.name == deleteStorageLabel
        }
    }

    class StorageLinks(links: Array<Link>?) {
        val selfLink: String? = links?.find {
            it.klass?.contains(storageClassLabel) ?: false
        }?.href
        val storagesLink: String? = links?.find {
            it.klass?.contains(storagesLabel) ?: false
        }?.href
    }

    companion object {
        private const val houseIdLabel: String = "house-id"
        private const val storageIdLabel: String = "storage-id"
        private const val nameLabel = "storage-name"
        private const val temperatureLabel = "storage-temperature"
        private const val storageClassLabel: String = "storage"
        private const val storagesLabel: String = "storages"
        private const val updateStorageLabel: String = "update-storage"
        private const val deleteStorageLabel: String = "delete-storage"
        private val mapper: ObjectMapper = jacksonObjectMapper()
    }
}