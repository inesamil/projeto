package ps.leic.isel.pt.gis.model

import android.os.Parcel
import android.os.Parcelable

data class CharacteristicsDTO(
        val babiesNumber: Short,
        val childrenNumber: Short,
        val adultsNumber: Short,
        val seniorsNumber: Short
) : Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readInt().toShort(),
            parcel.readInt().toShort(),
            parcel.readInt().toShort(),
            parcel.readInt().toShort()) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(babiesNumber.toInt())
        parcel.writeInt(childrenNumber.toInt())
        parcel.writeInt(adultsNumber.toInt())
        parcel.writeInt(seniorsNumber.toInt())
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<CharacteristicsDTO> {
        override fun createFromParcel(parcel: Parcel): CharacteristicsDTO {
            return CharacteristicsDTO(parcel)
        }

        override fun newArray(size: Int): Array<CharacteristicsDTO?> {
            return arrayOfNulls(size)
        }
    }
}