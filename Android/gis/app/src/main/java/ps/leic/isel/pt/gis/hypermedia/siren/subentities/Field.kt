package ps.leic.isel.pt.gis.hypermedia.siren.subentities

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@JsonIgnoreProperties(ignoreUnknown = true)
class Field(val name: String, val type: Type?, val value: Any?, var title: String?) {

    enum class Type constructor(private val type: String) {
        Hidden("Hidden"), Text("text"), Search("search"), Tel("tel"), Url("url"), Email("email"), Password("password"),
        Datetime("datetime"), Date("date"), Month("month"), Week("week"), Time("time"), DatetimeLocal("datetimeLocal"),
        Number("number"), Range("range"), Color("color"), Checkbox("checkbox"), Radio("radio"), File("file"),
        Bool("boolean");

        override fun toString(): String {
            return type
        }
    }
}
