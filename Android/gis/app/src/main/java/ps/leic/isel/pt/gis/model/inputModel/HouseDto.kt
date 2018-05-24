package ps.leic.isel.pt.gis.model.inputModel

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import ps.leic.isel.pt.gis.hypermedia.subentities.Action
import ps.leic.isel.pt.gis.hypermedia.subentities.Link
import ps.leic.isel.pt.gis.hypermedia.subentities.Siren
import ps.leic.isel.pt.gis.model.CharacteristicsDTO

class HouseDto(siren: Siren) {

    companion object {
        val mapper: ObjectMapper = jacksonObjectMapper()
    }

    val houseId: Long
    val name: String?
    val characteristics: CharacteristicsDTO?
    val actions: Array<Action>?
    val links: Array<String>

    init {
        val properties = siren.properties
        houseId = (properties?.get("house-id") as Int).toLong()
        name = properties["house-name"] as String
        val chars = properties["house-characteristics"]
        characteristics = mapper.convertValue<CharacteristicsDTO>(chars, CharacteristicsDTO::class.java)
        this.actions = siren.actions
        siren.links?.forEach {
            val house = it.klass?.find { it.equals("house") }
            links.put(house, it.href)
        }
    }
}