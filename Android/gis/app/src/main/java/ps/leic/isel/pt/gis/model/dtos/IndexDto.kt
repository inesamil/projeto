package ps.leic.isel.pt.gis.model.dtos

import ps.leic.isel.pt.gis.hypermedia.jsonHome.subentities.JsonHome
import ps.leic.isel.pt.gis.hypermedia.jsonHome.subentities.ResourceObject

class IndexDto(jsonHome: JsonHome) {
    val title: String?
    val author: String?
    val describedBy: String?
    val license: String?
    val resources: Resources


    init {
        title = jsonHome.api.title
        author = jsonHome.api.links.author
        describedBy = jsonHome.api.links.describedBy
        license = jsonHome.api.links.license
        resources = Resources(jsonHome.resources)
    }

    class Resources(resources: HashMap<String, ResourceObject>) {
        val getHouses: ResourceObject? = resources[relHousesLabel]
        val postHouse: ResourceObject? = resources[relHouseLabel]
        val getUser: ResourceObject? = resources[relUserLabel]  //TODO
        val getCategories: ResourceObject? = resources[relCategoriesLabel]
        val getAllergies: ResourceObject? = resources[relAllergiesLabel]
    }

    companion object {
        val relHouseLabel: String = "rel/house"
        val relHousesLabel: String = "rel/houses"
        val relUserLabel: String = "rel/user"
        val relCategoriesLabel: String = "rel/categories"
        val relAllergiesLabel: String = "rel/allergies"
    }
}