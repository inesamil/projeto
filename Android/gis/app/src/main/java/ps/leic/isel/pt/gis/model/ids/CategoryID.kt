package ps.leic.isel.pt.gis.model.ids

import android.os.Parcel
import android.os.Parcelable

data class CategoryID(
        val categoryId: Int
) : Parcelable {
    constructor(parcel: Parcel) : this(parcel.readInt()) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(categoryId)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<CategoryID> {
        override fun createFromParcel(parcel: Parcel): CategoryID {
            return CategoryID(parcel)
        }

        override fun newArray(size: Int): Array<CategoryID?> {
            return arrayOfNulls(size)
        }
    }
}