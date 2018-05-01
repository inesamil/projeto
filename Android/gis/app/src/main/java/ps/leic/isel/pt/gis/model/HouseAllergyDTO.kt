package ps.leic.isel.pt.gis.model

import android.os.Parcel
import android.os.Parcelable

data class HouseAllergyDTO(
        val houseId: Long,
        val allergyAllergen: String,
        val allergicsNumber: Short
) : Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readLong(),
            parcel.readString(),
            parcel.readInt().toShort()) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeLong(houseId)
        parcel.writeString(allergyAllergen)
        parcel.writeInt(allergicsNumber.toInt())
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<HouseAllergyDTO> {
        override fun createFromParcel(parcel: Parcel): HouseAllergyDTO {
            return HouseAllergyDTO(parcel)
        }

        override fun newArray(size: Int): Array<HouseAllergyDTO?> {
            return arrayOfNulls(size)
        }
    }
}