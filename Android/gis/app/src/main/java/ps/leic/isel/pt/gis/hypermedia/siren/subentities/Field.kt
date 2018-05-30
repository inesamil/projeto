package ps.leic.isel.pt.gis.hypermedia.siren.subentities

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
class Field(val name: String, val type: Type?, val value: Any?, var title: String?) {

    enum class Type constructor(private val type: String) {
        @JsonProperty("hidden")
        Hidden("hidden"),
        @JsonProperty("text")
        Text("text"),
        @JsonProperty("search")
        Search("search"),
        @JsonProperty("tel")
        Tel("tel"),
        @JsonProperty("url")
        Url("url"),
        @JsonProperty("email")
        Email("email"),
        @JsonProperty("password")
        Password("password"),
        @JsonProperty("datetime")
        Datetime("datetime"),
        @JsonProperty("date")
        Date("date"),
        @JsonProperty("month")
        Month("month"),
        @JsonProperty("week")
        Week("week"),
        @JsonProperty("time")
        Time("time"),
        @JsonProperty("datetimeLocal")
        DatetimeLocal("datetimeLocal"),
        @JsonProperty("number")
        Number("number"),
        @JsonProperty("range")
        Range("range"),
        @JsonProperty("color")
        Color("color"),
        @JsonProperty("checkbox")
        Checkbox("checkbox"),
        @JsonProperty("radio")
        Radio("radio"),
        @JsonProperty("file")
        File("file"),
        @JsonProperty("boolean")
        Bool("boolean");

        override fun toString(): String {
            return type
        }
    }
}
