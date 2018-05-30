package ps.leic.isel.pt.gis.repositories

enum class Status {
    SUCCESS,
    ERROR,
    LOADING
}

//a generic class that describes a data with a status
class Resource<out T> private constructor(val status: Status, val data: T?, val message: String?) {

    companion object {
        fun <T> success(data: T): Resource<T> {
            return Resource(Status.SUCCESS, data, null)
        }

        fun <T> error(msg: String?): Resource<T> {
            return Resource(Status.ERROR, null, msg)
        }

        fun <T> loading(): Resource<T> {
            return Resource(Status.LOADING, null, null)
        }
    }
}
