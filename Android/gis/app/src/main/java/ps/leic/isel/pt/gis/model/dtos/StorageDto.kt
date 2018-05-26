package ps.leic.isel.pt.gis.model.dtos

import ps.leic.isel.pt.gis.hypermedia.siren.subentities.Action
import ps.leic.isel.pt.gis.hypermedia.siren.subentities.Link
import ps.leic.isel.pt.gis.hypermedia.siren.subentities.Siren

class StorageDto(siren: Siren) {
    val houseId: Long
    val storageId: Short
    val name: String
    val temperature: String? //TODO: problem?
    val actions: StorageActions
    val links: StorageLinks

    init {
        val properties = siren.properties
        houseId = (properties?.get(houseIdLabel) as Int).toLong()
        storageId = (properties[storageIdLabel] as Int).toShort()
        name = properties[nameLabel] as String
        temperature = properties[temperatureLabel] as String    //TODO: problem?
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
        const val houseIdLabel: String = "house-id"
        const val storageIdLabel: String = "storage-id"
        const val nameLabel = "storage-name"
        const val temperatureLabel = "storage-temperature"
        const val storageClassLabel: String = "storage"
        const val storagesLabel: String = "storages"
        const val updateStorageLabel: String = "update-storage"
        const val deleteStorageLabel: String = "delete-storage"
    }
}