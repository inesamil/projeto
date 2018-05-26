package ps.leic.isel.pt.gis.model.dtos

import ps.leic.isel.pt.gis.hypermedia.subentities.Action
import ps.leic.isel.pt.gis.hypermedia.subentities.Link
import ps.leic.isel.pt.gis.hypermedia.subentities.Siren

class StoragesDto(siren: Siren) {
    val storages: Array<StorageDto>
    val actions: StoragesActions
    val links: StoragesLink

    init {
        storages = siren.entities?.map {
            StorageDto(Siren(it.klass, it.properties, null, it.actions, it.links))
        }.orEmpty().toTypedArray()
        actions = StoragesActions(siren.actions)
        links = StoragesLink(siren.links)
    }

    class StoragesActions(actions: Array<Action>?) {
        val addStorage: Action? = actions?.find {
            it.name == addStorageLabel
        }
    }

    class StoragesLink(links: Array<Link>?) {
        val selfLink: String? = links?.find {
            it.klass?.contains(storagesClassLabel) ?: false
        }?.href
        val houseLink: String? = links?.find {
            it.klass?.contains(houseLabel) ?: false
        }?.href
    }

    companion object {
        const val addStorageLabel: String = "add-storage"
        const val storagesClassLabel: String = "storages"
        const val houseLabel: String = "house"
    }
}