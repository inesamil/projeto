package ps.leic.isel.pt.gis.model

import android.os.Parcel
import android.os.Parcelable

data class MovementDTO(
        val houseId: Long,
        val stockItemSku: String,
        val storageId: Short,
        val type: String,
        val dateTime: String,
        val quantity: Short
) : Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readLong(),
            parcel.readString(),
            parcel.readInt().toShort(),
            parcel.readString(),
            parcel.readString(),
            parcel.readInt().toShort()) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeLong(houseId)
        parcel.writeString(stockItemSku)
        parcel.writeInt(storageId.toInt())
        parcel.writeString(type)
        parcel.writeString(dateTime)
        parcel.writeInt(quantity.toInt())
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<MovementDTO> {
        override fun createFromParcel(parcel: Parcel): MovementDTO {
            return MovementDTO(parcel)
        }

        override fun newArray(size: Int): Array<MovementDTO?> {
            return arrayOfNulls(size)
        }
    }
}