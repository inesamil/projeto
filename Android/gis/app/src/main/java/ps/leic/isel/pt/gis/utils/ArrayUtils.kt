package ps.leic.isel.pt.gis.utils

inline fun <reified E> Array<E>.addElement(elem: E): Array<E> {
    val list = toMutableList()
    list.add(elem)
    return list.toTypedArray()
}

inline fun <reified E> Array<E>.removeElement(elem: E): Array<E> {
    val list = toMutableList()
    list.remove(elem)
    return list.toTypedArray()
}