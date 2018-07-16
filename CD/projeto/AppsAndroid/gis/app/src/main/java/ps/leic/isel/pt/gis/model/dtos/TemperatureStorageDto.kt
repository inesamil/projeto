package ps.leic.isel.pt.gis.model.dtos

data class TemperatureStorageDto(val minimum: Float, val maximum: Float) {

    override fun toString(): String {
        return "[$minimum, $maximum]"
    }
}