package com.brmsdi.gcsystem.data.repository

import com.brmsdi.gcsystem.data.dto.PaginationContractDTO
import com.brmsdi.gcsystem.data.listeners.APIEvent


/**
 *
 * @author Wisley Bruno Marques França
 * @since 1
 */

interface ContractRepository : CallRepository {
    fun loadContracts(params: Map<String, String> = mapOf(), event: APIEvent<PaginationContractDTO>)
}