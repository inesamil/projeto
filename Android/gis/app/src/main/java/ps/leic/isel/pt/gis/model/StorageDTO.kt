package ps.leic.isel.pt.gis.model

import android.os.Parcel
import android.os.Parcelable

data class StorageDTO(
        val houseId: Long,
        val storageId: Short,
        val name: String,
        val temperatureRange: Any
) : Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readLong(),
            parcel.readInt().toShort(),
            parcel.readString(),
            TODO("temperatureRange")) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeLong(houseId)
        parcel.writeInt(storageId.toInt())
        parcel.writeString(name)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<StorageDTO> {
        override fun createFromParcel(parcel: Parcel): StorageDTO {
            return StorageDTO(parcel)
        }

        override fun newArray(size: Int): Array<StorageDTO?> {
            return arrayOfNulls(size)
        }
    }
}