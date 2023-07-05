package com.brmsdi.gcsystem.data.listeners

import com.brmsdi.gcsystem.data.model.RepairRequest

/**
 *
 * @author Wisley Bruno Marques França
 * @since 1
 */

interface RepairRequestListener {

    fun onClick(repairRequest: RepairRequest)

    fun onLongClick(repairRequest: RepairRequest): Boolean

    fun deleteItem(position: Int)
}