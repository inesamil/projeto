package ps.leic.isel.pt.gis.model

import android.os.Parcel
import android.os.Parcelable

data class UserDTO(
        val userUsername: String,
        val userEmail: String,
        val userAge: Short,
        val userName: String
) : Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readString(),
            parcel.readInt().toShort(),
            parcel.readString()) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(userUsername)
        parcel.writeString(userEmail)
        parcel.writeInt(userAge.toInt())
        parcel.writeString(userName)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<UserDTO> {
        override fun createFromParcel(parcel: Parcel): UserDTO {
            return UserDTO(parcel)
        }

        override fun newArray(size: Int): Array<UserDTO?> {
            return arrayOfNulls(size)
        }
    }
}