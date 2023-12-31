package com.brmsdi.gcsystem.data.listeners

import com.brmsdi.gcsystem.data.model.RepairRequest


/**
 *
 * @author Wisley Bruno Marques França
 * @since 1
 */

interface AddItemListener {
    fun addItem(repairRequest: RepairRequest)
}