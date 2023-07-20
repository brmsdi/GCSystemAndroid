package com.brmsdi.gcsystem.data.listeners

import com.brmsdi.gcsystem.data.model.RepairRequest

/**
 *
 * @author Wisley Bruno Marques França
 * @since 1
 */

interface ItemRecyclerListener<T> {

    fun onClick(model: T)

    fun onLongClick(model: T): Boolean

    fun deleteItem(position: Int)
}