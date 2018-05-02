package ps.leic.isel.pt.gis.model

import android.os.Parcel
import android.os.Parcelable

data class SystemListDTO(
        override val houseId: Long,
        override val listId: Short,
        override val listName: String,
        override val listType: String,
        val items: List<ListProductDTO>
) : ListDTO, Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readLong(),
            parcel.readInt().toShort(),
            parcel.readString(),
            parcel.readString(),
            parcel.createTypedArrayList(ListProductDTO)) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeLong(houseId)
        parcel.writeInt(listId.toInt())
        parcel.writeString(listName)
        parcel.writeString(listType)
        parcel.writeTypedList(items)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<SystemListDTO> {
        override fun createFromParcel(parcel: Parcel): SystemListDTO {
            return SystemListDTO(parcel)
        }

        override fun newArray(size: Int): Array<SystemListDTO?> {
            return arrayOfNulls(size)
        }
    }
}