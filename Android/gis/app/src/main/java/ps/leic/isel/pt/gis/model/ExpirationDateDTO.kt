package ps.leic.isel.pt.gis.model

import android.os.Parcel
import android.os.Parcelable

data class ExpirationDateDTO(
        val houseId: Long,
        val stockItemSku: String,
        val date: String,
        val quantity: Short
) : Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readLong(),
            parcel.readString(),
            parcel.readString(),
            parcel.readInt().toShort()) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeLong(houseId)
        parcel.writeString(stockItemSku)
        parcel.writeString(date)
        parcel.writeInt(quantity.toInt())
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ExpirationDateDTO> {
        override fun createFromParcel(parcel: Parcel): ExpirationDateDTO {
            return ExpirationDateDTO(parcel)
        }

        override fun newArray(size: Int): Array<ExpirationDateDTO?> {
            return arrayOfNulls(size)
        }
    }
}