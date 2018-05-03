package ps.leic.isel.pt.gis.model

import android.os.Parcel
import android.os.Parcelable

data class StockItemDTO(
        val houseId: Long,
        val sku: String,
        val categoryId: Int,
        val productId: Int,
        val productName: String,
        val brand: String,
        val segment: String,
        val variety: String,
        val quantity: Short,
        val description: String,
        val conservationStorage: String,
        val allergens: Array<StockItemAllergenDTO>,
        val expirationDates: Array<ExpirationDateDTO>,
        val storages: Array<StorageDTO>,
        val movements: Array<MovementDTO>
) : Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readLong(),
            parcel.readString(),
            parcel.readInt(),
            parcel.readInt(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readInt().toShort(),
            parcel.readString(),
            parcel.readString(),
            parcel.createTypedArray(StockItemAllergenDTO),
            parcel.createTypedArray(ExpirationDateDTO),
            parcel.createTypedArray(StorageDTO),
            parcel.createTypedArray(MovementDTO)) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeLong(houseId)
        parcel.writeString(sku)
        parcel.writeInt(categoryId)
        parcel.writeInt(productId)
        parcel.writeString(productName)
        parcel.writeString(brand)
        parcel.writeString(segment)
        parcel.writeString(variety)
        parcel.writeInt(quantity.toInt())
        parcel.writeString(description)
        parcel.writeString(conservationStorage)
        parcel.writeTypedArray(allergens, flags)
        parcel.writeTypedArray(expirationDates, flags)
        parcel.writeTypedArray(storages, flags)
        parcel.writeTypedArray(movements, flags)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<StockItemDTO> {
        override fun createFromParcel(parcel: Parcel): StockItemDTO {
            return StockItemDTO(parcel)
        }

        override fun newArray(size: Int): Array<StockItemDTO?> {
            return arrayOfNulls(size)
        }
    }
}