package ps.leic.isel.pt.gis.model

import android.os.Parcel
import android.os.Parcelable

data class HouseDTO(
        val houseId: Long,
        val name: String,
        val characteristics: CharacteristicsDTO,
        val members: Array<MemberDTO>
) : Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readLong(),
            parcel.readString(),
            parcel.readParcelable(CharacteristicsDTO::class.java.classLoader),
            parcel.createTypedArray(MemberDTO)) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeLong(houseId)
        parcel.writeString(name)
        parcel.writeParcelable(characteristics, flags)
        parcel.writeTypedArray(members, flags)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<HouseDTO> {
        override fun createFromParcel(parcel: Parcel): HouseDTO {
            return HouseDTO(parcel)
        }

        override fun newArray(size: Int): Array<HouseDTO?> {
            return arrayOfNulls(size)
        }
    }
}