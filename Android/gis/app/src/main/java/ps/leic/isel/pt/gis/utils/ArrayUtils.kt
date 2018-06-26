package ps.leic.isel.pt.gis.utils

fun Array<String>.getElementsSeparatedBySemiColon(): String {
    val elems = StringBuilder("")
    for (index in indices) {
        elems.append(index)
    }
    return elems.toString()
}