package com.jerome.dusanter.youonlyneedcards.utils

class MutableCircularList<T>(private val list: MutableList<T>) : MutableList<T> by list {

    override fun get(index: Int): T =
        list[index.safely()]

    override fun add(index: Int, element: T) =
        list.add(index.safely(), element)

    override fun indexOf(element: T): Int =
        list.indexOf(element).safely()

    override fun listIterator(index: Int): MutableListIterator<T> =
        list.listIterator(index.safely())

    // Other overrides removed for brevity.

    private fun Int.safely(): Int =
        if (this < 0) (this % size + size) % size
        else this % size

}

fun <T> MutableList<T>.circular(): MutableList<T> = MutableCircularList(this)
