package ps.leic.isel.pt.gis.utils

import ps.leic.isel.pt.gis.model.StockItemAllergenDTO

fun Array<String>.getElementsSeparatedBySemiColon(): String {
    val elems = StringBuilder("")
    for (index in indices) {
        elems.append(index)
    }
    return elems.toString()
}