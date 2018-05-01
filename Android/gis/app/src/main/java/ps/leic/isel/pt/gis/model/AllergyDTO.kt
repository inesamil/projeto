package ps.leic.isel.pt.gis.model

import android.os.Parcel
import android.os.Parcelable

data class AllergyDTO(
        val allegyAllergen: String
) : Parcelable {
    constructor(parcel: Parcel) : this(parcel.readString()) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(allegyAllergen)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<AllergyDTO> {
        override fun createFromParcel(parcel: Parcel): AllergyDTO {
            return AllergyDTO(parcel)
        }

        override fun newArray(size: Int): Array<AllergyDTO?> {
            return arrayOfNulls(size)
        }
    }
}