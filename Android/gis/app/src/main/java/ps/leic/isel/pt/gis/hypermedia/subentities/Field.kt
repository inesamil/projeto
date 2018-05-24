package ps.leic.isel.pt.gis.hypermedia.subentities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@JsonIgnoreProperties(ignoreUnknown = true)
class Field(val name: String, val type: Type?, val value: Any?, var title: String?) {

    enum class Type private constructor(private val type: String) {
        hidden("hidden"), text("text"), search("search"), tel("tel"), url("url"), email("email"), password("password"),
        datetime("datetime"), date("date"), month("month"), week("week"), time("time"), datetimeLocal("datetimeLocal"),
        number("number"), range("range"), color("color"), checkbox("checkbox"), radio("radio"), file("file"),
        bool("boolean");

        override fun toString(): String {
            return type
        }
    }
}
