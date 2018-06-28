package ps.leic.isel.pt.gis.repositories

enum class Status {
    SUCCESS,
    UNSUCCESS,
    ERROR,
    LOADING
}

// Generic class that describes a data with a status
class Resource<out T, E> private constructor(val status: Status, val data: T?, val apiError: E?, val message: String?) {

    companion object {
        fun <T, E> success(data: T): Resource<T, E> {
            return Resource(Status.SUCCESS, data, null, null)
        }

        fun <T, E> apiError(error: E): Resource<T, E> {
            return Resource(Status.UNSUCCESS, null, error, null)
        }

        fun <T, E> error(msg: String?): Resource<T, E> {
            return Resource(Status.ERROR, null, null, msg)
        }

        fun <T, E> loading(): Resource<T, E> {
            return Resource(Status.LOADING, null, null, null)
        }
    }
}
