package ps.leic.isel.pt.gis.hypermedia.problemDetails

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@JsonIgnoreProperties(ignoreUnknown = true)
class ProblemDetails (
        val type: String,
        val title: String,
        val status: Int,
        val detail: String,
        val instance: String
)