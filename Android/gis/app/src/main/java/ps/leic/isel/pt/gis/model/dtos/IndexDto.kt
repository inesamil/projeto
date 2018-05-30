package ps.leic.isel.pt.gis.model.dtos

import com.damnhandy.uri.template.UriTemplate
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

    fun getHousesUrl(username: String): String? {
        val houses = resources.getHouses
        houses?.hrefVars?.containsKey(USERNAME_LABEL)?.let {
            if (!it) return@let
            return UriTemplate.expand(houses.hrefTemplate, mapOf(Pair(USERNAME_LABEL, username)))
        }
        return null
    }

    fun getUserUrl(username: String): String? {
        val user = resources.getUser
        user?.hrefVars?.containsKey(USERNAME_LABEL)?.let {
            if (!it) return@let
            return UriTemplate.expand(user.hrefTemplate, mapOf(Pair(USERNAME_LABEL, username)))
        }
        return null
    }

    companion object {
        private const val USERNAME_LABEL: String = "username"
        private const val relHouseLabel: String = "rel/house"
        private const val relHousesLabel: String = "rel/houses"
        private const val relUserLabel: String = "rel/user"
        private const val relCategoriesLabel: String = "rel/categories"
        private const val relAllergiesLabel: String = "rel/allergies"
    }
}