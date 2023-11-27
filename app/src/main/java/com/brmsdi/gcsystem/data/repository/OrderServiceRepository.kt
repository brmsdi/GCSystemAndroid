package com.brmsdi.gcsystem.data.repository

import androidx.paging.PagingSource
import com.brmsdi.gcsystem.data.dto.PagingOrderServiceModel
import com.brmsdi.gcsystem.data.dto.ResponseDTO
import com.brmsdi.gcsystem.data.listeners.APIEvent
import com.brmsdi.gcsystem.data.model.OrderService

/**
 *
 * @author Wisley Bruno Marques França
 * @since 1
 */

interface OrderServiceRepository : CallRepository {
    fun details(id: Int, event: APIEvent<OrderService>)

    fun close(id: Int, event: APIEvent<ResponseDTO>)

    fun pagingSource(search: String?) : PagingSource<Int, PagingOrderServiceModel>
}