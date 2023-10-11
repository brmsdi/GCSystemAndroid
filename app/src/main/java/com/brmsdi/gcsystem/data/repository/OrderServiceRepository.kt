package com.brmsdi.gcsystem.data.repository

import com.brmsdi.gcsystem.data.dto.PaginationOrderServiceDTO
import com.brmsdi.gcsystem.data.listeners.APIEvent


/**
 *
 * @author Wisley Bruno Marques França
 * @since 1
 */

interface OrderServiceRepository : CallRepository {
    fun loadOrderServices(params: Map<String, String>, event: APIEvent<PaginationOrderServiceDTO>)

    fun search(params: Map<String, String>, event: APIEvent<PaginationOrderServiceDTO>)
}