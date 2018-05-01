package ps.leic.isel.pt.gis.model

import android.os.Parcel
import android.os.Parcelable

data class MemberDTO(
        val houseId: Long,
        val userUsername: String,
        val administrator: Boolean
) : Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readLong(),
            parcel.readString(),
            parcel.readByte() != 0.toByte()) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeLong(houseId)
        parcel.writeString(userUsername)
        parcel.writeByte(if (administrator) 1 else 0)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<MemberDTO> {
        override fun createFromParcel(parcel: Parcel): MemberDTO {
            return MemberDTO(parcel)
        }

        override fun newArray(size: Int): Array<MemberDTO?> {
            return arrayOfNulls(size)
        }
    }
}