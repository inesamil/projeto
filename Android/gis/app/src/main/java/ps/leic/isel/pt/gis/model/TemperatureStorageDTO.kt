package ps.leic.isel.pt.gis.model

import android.os.Parcel
import android.os.Parcelable

data class TemperatureStorageDTO(val minimum: Float, val maximum: Float) : Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readFloat(),
            parcel.readFloat()) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeFloat(minimum)
        parcel.writeFloat(maximum)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<TemperatureStorageDTO> {
        override fun createFromParcel(parcel: Parcel): TemperatureStorageDTO {
            return TemperatureStorageDTO(parcel)
        }

        override fun newArray(size: Int): Array<TemperatureStorageDTO?> {
            return arrayOfNulls(size)
        }
    }
}