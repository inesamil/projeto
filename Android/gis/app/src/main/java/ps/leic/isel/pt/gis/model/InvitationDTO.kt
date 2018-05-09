package ps.leic.isel.pt.gis.model

import android.os.Parcel
import android.os.Parcelable

data class InvitationDTO(
        val requesterUser: String,
        val houseId: Long,
        val houseName: String
) : Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readLong(),
            parcel.readString()) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(requesterUser)
        parcel.writeLong(houseId)
        parcel.writeString(houseName)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<InvitationDTO> {
        override fun createFromParcel(parcel: Parcel): InvitationDTO {
            return InvitationDTO(parcel)
        }

        override fun newArray(size: Int): Array<InvitationDTO?> {
            return arrayOfNulls(size)
        }
    }
}