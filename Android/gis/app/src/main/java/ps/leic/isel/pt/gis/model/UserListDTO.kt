package ps.leic.isel.pt.gis.model

import android.os.Parcel
import android.os.Parcelable

data class UserListDTO(
        val houseId: Long,
        val listId: Short,
        val listName: String,
        val userUsername: String,
        val shareable: Boolean,
        val items: List<ListProductDTO>
) : Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readLong(),
            parcel.readInt().toShort(),
            parcel.readString(),
            parcel.readString(),
            parcel.readByte() != 0.toByte(),
            parcel.createTypedArrayList(ListProductDTO)) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeLong(houseId)
        parcel.writeInt(listId.toInt())
        parcel.writeString(listName)
        parcel.writeString(userUsername)
        parcel.writeByte(if (shareable) 1 else 0)
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