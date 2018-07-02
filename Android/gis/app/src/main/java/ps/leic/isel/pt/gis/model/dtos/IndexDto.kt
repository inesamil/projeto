package ps.leic.isel.pt.gis.model.dtos

import com.damnhandy.uri.template.UriTemplate
import ps.leic.isel.pt.gis.hypermedia.jsonHome.JsonHome
import ps.leic.isel.pt.gis.hypermedia.jsonHome.subentities.ResourceObject
import ps.leic.isel.pt.gis.model.ActionDto

class IndexDto(jsonHome: JsonHome) {
    val title: String? = jsonHome.api?.title
    val author: String? = jsonHome.api?.links?.author
    val describedBy: String? = jsonHome.api?.links?.describedBy
    val license: String? = jsonHome.api?.links?.license
    val resources: Resources = Resources(jsonHome.resources)

    class Resources(resources: HashMap<String, ResourceObject>) {
        val getHouses: ResourceObject? = resources[relHousesLabel]
        val postHouse: ResourceObject? = resources[relHouseLabel]
        val getUsers: ResourceObject? = resources[relUsersLabel]
        val getUser: ResourceObject? = resources[relUserLabel]
        val postUser: ResourceObject? = resources[relUsersLabel]
        val getCategories: ResourceObject? = resources[relCategoriesLabel]
        val getAllergies: ResourceObject? = resources[relAllergiesLabel]
        val getUserLists: ResourceObject? = resources[relUserListsLabel]
        val getInvitations: ResourceObject? = resources[relInvitationsLabel]
    }

    fun getHousesUrl(username: String): String? {
        val houses = resources.getHouses
        houses?.hrefVars?.containsKey(USERNAME_LABEL)?.let {
            if (!it) return@let
            return UriTemplate.expand(houses.hrefTemplate, mapOf(Pair(USERNAME_LABEL, username)))
        }
        return null
    }

    fun getUsersUrl(query: String) : String? {
        val users = resources.getUsers
        users?.hrefVars?.containsKey(USERNAME_LABEL)?.let {
            if (!it) return@let
            return UriTemplate.expand(users.hrefTemplate, mapOf(Pair(USERNAME_LABEL, query)))
        }
        return null
    }

    fun getUsersAction() : ActionDto? {
        val users = resources.postUser
        users?.let {
            return it.href?.let { url -> it.hints?.acceptPost?.let { types -> ActionDto("add-user", url, types[0]) }}
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

    fun getUserListUrl(username: String): String? {
        val userLists = resources.getUserLists
        userLists?.hrefVars?.containsKey(USERNAME_LABEL)?.let {
            if (!it) return@let
            return UriTemplate.expand(userLists.hrefTemplate, mapOf(Pair(USERNAME_LABEL, username)))
        }
        return null
    }

    fun getInvitationsUrl(username: String) : String? {
        val invitations = resources.getInvitations
        invitations?.hrefVars?.containsKey(USERNAME_LABEL)?.let {
            if (!it) return@let
            return UriTemplate.expand(invitations.hrefTemplate, mapOf(Pair(USERNAME_LABEL, username)))
        }
        return null
    }

    fun getCategoriesUrl() : String? {
        val categories = resources.getCategories
        return categories?.href
    }

    companion object {
        private const val USERNAME_LABEL: String = "username"
        private const val QUERY_LABEL: String = "username"
        private const val relHouseLabel: String = "/rel/house"
        private const val relHousesLabel: String = "/rel/houses"
        private const val relUserLabel: String = "/rel/user"
        private const val relUsersLabel: String = "/rel/users"
        private const val relUsersSearchLabel: String = "/rel/users/search"
        private const val relCategoriesLabel: String = "/rel/categories"
        private const val relAllergiesLabel: String = "/rel/allergies"
        private const val relUserListsLabel: String = "/rel/lists"
        private const val relInvitationsLabel: String = "/rel/invitations"
    }
}