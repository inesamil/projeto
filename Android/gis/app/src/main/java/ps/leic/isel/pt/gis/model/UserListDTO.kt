package ps.leic.isel.pt.gis.model

import android.os.Parcel
import android.os.Parcelable

data class UserListDTO(
        override val houseId: Long,
        override val listId: Short,
        override val listName: String,
        override val listType: String,
        val userUsername: String,
        val shareable: Boolean,
        override val items: List<ListProductDTO>
) : ListDTO, Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readLong(),
            parcel.readInt().toShort(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readByte() != 0.toByte(),
            parcel.createTypedArrayList(ListProductDTO)) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeLong(houseId)
        parcel.writeInt(listId.toInt())
        parcel.writeString(listName)
        parcel.writeString(listType)
        parcel.writeString(userUsername)
        parcel.writeByte(if (shareable) 1 else 0)
        parcel.writeTypedList(items)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<UserListDTO> {
        override fun createFromParcel(parcel: Parcel): UserListDTO {
            return UserListDTO(parcel)
        }

        override fun newArray(size: Int): Array<UserListDTO?> {
            return arrayOfNulls(size)
        }
    }
}