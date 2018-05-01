package ps.leic.isel.pt.gis.model

import android.os.Parcel
import android.os.Parcelable

data class CategoryDTO(
        val categoryId: Int,
        val categoryName: String
) : Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readInt(),
            parcel.readString()) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(categoryId)
        parcel.writeString(categoryName)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<CategoryDTO> {
        override fun createFromParcel(parcel: Parcel): CategoryDTO {
            return CategoryDTO(parcel)
        }

        override fun newArray(size: Int): Array<CategoryDTO?> {
            return arrayOfNulls(size)
        }
    }
}