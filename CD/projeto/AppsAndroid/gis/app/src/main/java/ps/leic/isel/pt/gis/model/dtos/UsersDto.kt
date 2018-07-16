package ps.leic.isel.pt.gis.model.dtos

import ps.leic.isel.pt.gis.hypermedia.siren.Siren

class UsersDto(siren: Siren) {
    val users: Array<UserDto> = siren.entities?.map {
        UserDto(Siren(it.klass, it.properties, null, it.actions, it.links))
    }.orEmpty().toTypedArray()
}