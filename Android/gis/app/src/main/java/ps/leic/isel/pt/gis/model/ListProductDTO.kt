package ps.leic.isel.pt.gis.model

import android.os.Parcel
import android.os.Parcelable

data class ListProductDTO(
        val houseId: Long,
        val listId: Short,
        val categoryId: Int,
        val productId: Int,
        val productName: String,
        val brand: String?,
        val quantity: Short
) : Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readLong(),
            parcel.readInt().toShort(),
            parcel.readInt(),
            parcel.readInt(),
            parcel.readString(),
            parcel.readString(),
            parcel.readInt().toShort()) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeLong(houseId)
        parcel.writeInt(listId.toInt())
        parcel.writeInt(categoryId)
        parcel.writeInt(productId)
        parcel.writeString(productName)
        parcel.writeString(brand)
        parcel.writeInt(quantity.toInt())
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ListProductDTO> {
        override fun createFromParcel(parcel: Parcel): ListProductDTO {
            return ListProductDTO(parcel)
        }

        override fun newArray(size: Int): Array<ListProductDTO?> {
            return arrayOfNulls(size)
        }
    }
}