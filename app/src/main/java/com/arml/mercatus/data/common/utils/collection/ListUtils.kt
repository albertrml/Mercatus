package com.arml.mercatus.data.common.utils.collection

inline fun <T, K: Comparable<K>> List<T>.getMinMax(
    crossinline selector: (T) -> K,
): Pair<K, K> {
    if (isEmpty()) throw NoSuchElementException("List is empty.")
    if (size == 1) return selector(first()) to selector(first())
    var min = selector(first())
    var max = selector(first())
    drop(1).forEach { price ->
        val current = selector(price)
        if(current < min) min = current
        if(current > max) max = current
    }
    return min to max
}