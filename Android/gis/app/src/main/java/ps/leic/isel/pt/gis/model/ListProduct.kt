package ps.leic.isel.pt.gis.model

data class ListProduct(
        val productId: Int,
        val productName: String,
        val brand: String?,
        var quantity: Short
)
