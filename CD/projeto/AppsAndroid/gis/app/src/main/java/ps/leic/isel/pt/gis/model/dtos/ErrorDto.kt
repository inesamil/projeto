package ps.leic.isel.pt.gis.model.dtos

import ps.leic.isel.pt.gis.hypermedia.problemDetails.ProblemDetails

data class ErrorDto(val statusText: String, val statusCode: Int, val developerErrorMessage: String, val message: String) {
    constructor(problemDetails: ProblemDetails)
            : this(problemDetails.title, problemDetails.status, problemDetails.detail, problemDetails.userFriendlyMessage)
}