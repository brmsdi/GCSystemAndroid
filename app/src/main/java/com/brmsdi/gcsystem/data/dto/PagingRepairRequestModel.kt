package com.brmsdi.gcsystem.data.dto

import com.brmsdi.gcsystem.data.model.RepairRequest


/**
 *
 * @author Wisley Bruno Marques França
 * @since 1
 */
data class PagingRepairRequestModel(val key: Int, val page: Int, val repairRequest: RepairRequest)