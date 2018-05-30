package ps.leic.isel.pt.gis.utils

import ps.leic.isel.pt.gis.model.StockItemAllergenDTO

fun Array<StockItemAllergenDTO>.getElementsSeparatedBySemiColon(): String {
    val allergens = StringBuilder("")
    if (iterator().hasNext())
        allergens.append(iterator().next().allergen)
    while (iterator().hasNext()) {
        allergens.append("; " + iterator().next().allergen)
    }
    return allergens.toString()
}