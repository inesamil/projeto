package ps.leic.isel.pt.gis.model.dtos

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import ps.leic.isel.pt.gis.hypermedia.siren.subentities.Action
import ps.leic.isel.pt.gis.hypermedia.siren.subentities.Link
import ps.leic.isel.pt.gis.hypermedia.siren.subentities.Siren
import ps.leic.isel.pt.gis.model.CharacteristicsDTO

class UserDto(siren: Siren) {
    val username: String?
    val name: String?
    val email: String?
    val age: Short
    val actions: UserActions
    val links: UserLinks

    init {
        val properties = siren.properties
        username = properties?.get(usernameLabel) as String
        name = properties[nameLabel] as String
        email = properties[emailLabel] as String
        age = (properties[ageLabel] as Int).toShort()
        actions = UserActions(siren.actions)
        links = UserLinks(siren.links)
    }

    class UserActions(actions: Array<Action>?) {
        val updateUser: Action? = actions?.find {
            it.name == updateUserLabel
        }
        val deleteUser: Action? = actions?.find {
            it.name == deleteUserLabel
        }
    }

    class UserLinks(links: Array<Link>?) {
        val selfLink: String? = links?.find {
            it.klass?.contains(userClassLabel) ?: false
        }?.href
        val indexLink: String? = links?.find {
            it.klass?.contains(indexLabel) ?: false
        }?.href
    }

    companion object {
        const val usernameLabel: String = "user-username"
        const val nameLabel: String = "user-name"
        const val emailLabel: String = "user-email"
        const val ageLabel: String = "user-age"
        const val updateUserLabel: String = "update-user"
        const val deleteUserLabel: String = "delete-user"
        const val indexLabel: String = "index"
        const val userClassLabel: String = "user"
    }
}