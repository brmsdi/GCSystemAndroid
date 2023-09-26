package com.brmsdi.gcsystem.data.repository

import com.brmsdi.gcsystem.data.dto.RepairRequestRegisterDTO
import com.brmsdi.gcsystem.data.dto.RepairRequestRegisterDataDTO
import com.brmsdi.gcsystem.data.listeners.APIEvent
import com.brmsdi.gcsystem.data.model.RepairRequest

/**
 *
 * @author Wisley Bruno Marques França
 * @since 1
 */

interface RepairRequestRepository : CallRepository {
    fun loadDataNewRepairRequest(event: APIEvent<RepairRequestRegisterDataDTO>)

    fun save(repairRequestRegisterDTO: RepairRequestRegisterDTO, event: APIEvent<RepairRequest>)
}