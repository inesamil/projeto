package ps.leic.isel.pt.gis.model.dtos

import ps.leic.isel.pt.gis.hypermedia.jsonHome.subentities.JsonHome
import ps.leic.isel.pt.gis.hypermedia.jsonHome.subentities.ResourceObject

class IndexDto(jsonHome: JsonHome) {
    val title: String? = jsonHome.api?.title
    val author: String? = jsonHome.api?.links?.author
    val describedBy: String? = jsonHome.api?.links?.describedBy
    val license: String? = jsonHome.api?.links?.license
    val resources: Resources = Resources(jsonHome.resources)

    class Resources(resources: HashMap<String, ResourceObject>) {
        val getHouses: ResourceObject? = resources[relHousesLabel]
        val postHouse: ResourceObject? = resources[relHouseLabel]
        val getUser: ResourceObject? = resources[relUserLabel]  //TODO
        val getCategories: ResourceObject? = resources[relCategoriesLabel]
        val getAllergies: ResourceObject? = resources[relAllergiesLabel]
    }

    companion object {
        const val relHouseLabel: String = "rel/house"
        const val relHousesLabel: String = "rel/houses"
        const val relUserLabel: String = "rel/user"
        const val relCategoriesLabel: String = "rel/categories"
        const val relAllergiesLabel: String = "rel/allergies"
    }
}