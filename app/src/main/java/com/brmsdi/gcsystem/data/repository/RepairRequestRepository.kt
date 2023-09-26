package com.brmsdi.gcsystem.data.repository

import com.brmsdi.gcsystem.data.dto.RepairRequestRegisterDataDTO
import com.brmsdi.gcsystem.data.listeners.APIEvent

/**
 *
 * @author Wisley Bruno Marques França
 * @since 1
 */

interface RepairRequestRepository : CallRepository {
    fun loadDataNewRepairRequest(event: APIEvent<RepairRequestRegisterDataDTO>)
}