package ps.leic.isel.pt.gis.model.dtos

import ps.leic.isel.pt.gis.hypermedia.siren.Siren
import ps.leic.isel.pt.gis.hypermedia.siren.subentities.Action
import ps.leic.isel.pt.gis.hypermedia.siren.subentities.Link

class StoragesDto(siren: Siren) {
    val storages: Array<StorageDto> = siren.entities?.map {
        StorageDto(Siren(it.klass, it.properties, null, it.actions, it.links))
    }.orEmpty().toTypedArray()
    val actions: StoragesActions = StoragesActions(siren.actions)
    val links: StoragesLink = StoragesLink(siren.links)

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
        private const val addStorageLabel: String = "add-storage"
        private const val storagesClassLabel: String = "storages"
        private const val houseLabel: String = "house"
    }
}