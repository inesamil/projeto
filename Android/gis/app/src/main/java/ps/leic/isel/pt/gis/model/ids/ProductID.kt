package ps.leic.isel.pt.gis.model.ids

import android.os.Parcel
import android.os.Parcelable

data class ProductID(
        val categoryId: Int,
        val productId: Int
) : Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readInt(),
            parcel.readInt()) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(categoryId)
        parcel.writeInt(productId)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ProductID> {
        override fun createFromParcel(parcel: Parcel): ProductID {
            return ProductID(parcel)
        }

        override fun newArray(size: Int): Array<ProductID?> {
            return arrayOfNulls(size)
        }
    }
}