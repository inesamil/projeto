package ps.leic.isel.pt.gis.model.dtos

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import ps.leic.isel.pt.gis.hypermedia.siren.subentities.Action
import ps.leic.isel.pt.gis.hypermedia.siren.subentities.Link
import ps.leic.isel.pt.gis.hypermedia.siren.subentities.Siren
import ps.leic.isel.pt.gis.model.CharacteristicsDTO

class HouseDto(siren: Siren) {
    val houseId: Long
    val name: String?
    val characteristics: CharacteristicsDTO?
    val members: Array<MemberDto>
    val actions: HousesActions
    val links: HouseLinks

    init {
        val properties = siren.properties
        houseId = (properties?.get(houseIdLabel) as Int).toLong()
        name = properties[houseNameLabel] as String
        val characteristics = properties[houseCharacteristicsLabel]
        this.characteristics = mapper.convertValue<CharacteristicsDTO>(characteristics, CharacteristicsDTO::class.java)
        members = siren.entities?.map {
            MemberDto(Siren(it.klass, it.properties, null, null, null))
        }.orEmpty().toTypedArray()
        actions = HousesActions(siren.actions)
        links = HouseLinks(siren.links)
    }

    class HousesActions(actions: Array<Action>?) {
        val addHouse: Action? = actions?.find {
            it.name == addHouseLabel
        }
    }

    class HouseLinks(links: Array<Link>?) {
        val selfLink: String? = links?.find {
            it.klass?.contains(houseClassLabel) ?: false
        }?.href
        val indexLink: String? = links?.find {
            it.klass?.contains(indexLabel) ?: false
        }?.href
    }

    companion object {
        private val mapper: ObjectMapper = jacksonObjectMapper()
        private const val houseIdLabel: String = "house-id"
        private const val houseNameLabel: String = "house-name"
        private const val houseCharacteristicsLabel = "house-characteristics"
        private const val houseClassLabel: String = "house"
        private const val indexLabel: String = "index"
        private const val addHouseLabel: String = "add-house"
    }
}