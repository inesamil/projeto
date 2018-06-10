package ps.leic.isel.pt.gis.model.actions

import ps.leic.isel.pt.gis.hypermedia.siren.subentities.Action
import ps.leic.isel.pt.gis.model.dtos.CharacteristicsDto

class AddHouseAction(action: Action) {

    val url: String = action.href

    fun getBody(houseName: String, characteristics: CharacteristicsDto): String {
        return ""   //TODO: construir o body. Contruir o body? n percebo. Para fazer o post tens de mandar um objeto no parametro body.
    }
}