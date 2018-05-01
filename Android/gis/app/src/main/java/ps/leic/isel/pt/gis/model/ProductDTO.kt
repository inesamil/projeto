package ps.leic.isel.pt.gis.model

import android.os.Parcel
import android.os.Parcelable

data class ProductDTO(
        val categoryId: Int,
        val productId: Int,
        val name: String,
        val edible: Boolean,
        val shelflife: String
) : Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readInt(),
            parcel.readInt(),
            parcel.readString(),
            parcel.readByte() != 0.toByte(),
            parcel.readString()) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(categoryId)
        parcel.writeInt(productId)
        parcel.writeString(name)
        parcel.writeByte(if (edible) 1 else 0)
        parcel.writeString(shelflife)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ProductDTO> {
        override fun createFromParcel(parcel: Parcel): ProductDTO {
            return ProductDTO(parcel)
        }

        override fun newArray(size: Int): Array<ProductDTO?> {
            return arrayOfNulls(size)
        }
    }
}