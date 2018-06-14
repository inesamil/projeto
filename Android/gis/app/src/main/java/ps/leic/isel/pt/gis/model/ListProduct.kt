package ps.leic.isel.pt.gis.model

data class ListProduct(
        val houseId: Long,
        val listId: Short,
        val listName: String,
        val productId: Int,
        var quantity: Short
)
