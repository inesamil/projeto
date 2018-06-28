package ps.leic.isel.pt.gis.model.dtos

import ps.leic.isel.pt.gis.hypermedia.problemDetails.ProblemDetails

class ErrorDto(problemDetails: ProblemDetails) {
    val statusText: String = problemDetails.title
    val statusCode: Int = problemDetails.status
    val developerErrorMessage: String = problemDetails.detail
    val message: String = problemDetails.userFriendlyMessage ?: ""  //TODO
}