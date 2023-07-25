package com.brmsdi.gcsystem.data.listeners

import com.brmsdi.gcsystem.data.model.Item


/**
 *
 * @author Wisley Bruno Marques França
 * @since 1
 */

interface RemoveItemListener {
    fun remove(item: Item)
}