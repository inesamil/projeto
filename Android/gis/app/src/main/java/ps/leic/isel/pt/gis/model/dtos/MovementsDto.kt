package ps.leic.isel.pt.gis.model.dtos

import ps.leic.isel.pt.gis.hypermedia.siren.subentities.Action
import ps.leic.isel.pt.gis.hypermedia.siren.subentities.Link
import ps.leic.isel.pt.gis.hypermedia.siren.subentities.Siren

class MovementsDto(siren: Siren) {
    val movements: Array<MovementDto> = siren.entities?.map {
        MovementDto(Siren(it.klass, it.properties, null, null, it.links))
    }.orEmpty().toTypedArray()
    val actions: MovementsActions = MovementsActions(siren.actions)
    val links: MovementsLink = MovementsLink(siren.links)

    class MovementsActions(actions: Array<Action>?) {
        val addMovement: Action? = actions?.find {
            it.name == addMovementLabel
        }
    }

    class MovementsLink(links: Array<Link>?) {
        val selfLink: String? = links?.find {
            it.klass?.contains(movementsClassLabel) ?: false
        }?.href
        val houseLink: String? = links?.find {
            it.klass?.contains(houseLabel) ?: false
        }?.href
    }

    companion object {
        const val movementsClassLabel: String = "movements"
        const val houseLabel: String = "house"
        const val addMovementLabel: String = "add-movement"
    }
}