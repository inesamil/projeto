package ps.leic.isel.pt.gis.model

import android.os.Parcel
import android.os.Parcelable

open class ListDTO(
        open val houseId: Long,
        open val listId: Short,
        open val listName: String,
        open val listType: String
) : Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readLong(),
            parcel.readInt().toShort(),
            parcel.readString(),
            parcel.readString()) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeLong(houseId)
        parcel.writeInt(listId.toInt())
        parcel.writeString(listName)
        parcel.writeString(listType)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ListDTO> {
        override fun createFromParcel(parcel: Parcel): ListDTO {
            return ListDTO(parcel)
        }

        override fun newArray(size: Int): Array<ListDTO?> {
            return arrayOfNulls(size)
        }
    }
}