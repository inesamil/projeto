package ps.leic.isel.pt.gis.model.dtos

import ps.leic.isel.pt.gis.hypermedia.siren.subentities.Action
import ps.leic.isel.pt.gis.hypermedia.siren.subentities.Link
import ps.leic.isel.pt.gis.hypermedia.siren.subentities.Siren

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
        private const val usernameLabel: String = "user-username"
        private const val nameLabel: String = "user-name"
        private const val emailLabel: String = "user-email"
        private const val ageLabel: String = "user-age"
        private const val updateUserLabel: String = "update-user"
        private const val deleteUserLabel: String = "delete-user"
        private const val indexLabel: String = "index"
        private const val userClassLabel: String = "user"
    }
}