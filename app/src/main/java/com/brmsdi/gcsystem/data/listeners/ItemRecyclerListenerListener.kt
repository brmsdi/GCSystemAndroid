package com.brmsdi.gcsystem.data.listeners

/**
 *
 * @author Wisley Bruno Marques França
 * @since 1
 */

interface ItemRecyclerListenerListener<T> : ItemRecyclerClickListener<T> {

    fun onLongClick(model: T): Boolean

    fun deleteItem(position: Int)
}