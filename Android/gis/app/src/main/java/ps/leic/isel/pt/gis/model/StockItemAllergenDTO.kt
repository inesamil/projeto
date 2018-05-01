package ps.leic.isel.pt.gis.model

import android.os.Parcel
import android.os.Parcelable

data class StockItemAllergenDTO(
        val houseId: Long,
        val sku: String,
        val allergen: String
) : Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readLong(),
            parcel.readString(),
            parcel.readString()) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeLong(houseId)
        parcel.writeString(sku)
        parcel.writeString(allergen)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<StockItemAllergenDTO> {
        override fun createFromParcel(parcel: Parcel): StockItemAllergenDTO {
            return StockItemAllergenDTO(parcel)
        }

        override fun newArray(size: Int): Array<StockItemAllergenDTO?> {
            return arrayOfNulls(size)
        }
    }
}