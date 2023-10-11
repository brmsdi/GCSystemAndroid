package com.brmsdi.gcsystem.data.repository.impl

import com.brmsdi.gcsystem.data.dto.PaginationOrderServiceDTO
import com.brmsdi.gcsystem.data.listeners.APIEvent
import com.brmsdi.gcsystem.data.remote.retrofit.RetrofitClient
import com.brmsdi.gcsystem.data.repository.OrderServiceRepository
import com.brmsdi.gcsystem.data.service.OrderServiceService

class OrderServiceRepositoryImpl : OrderServiceRepository {
    private lateinit var orderServiceService: OrderServiceService
    override fun loadOrderServices(
        params: Map<String, String>,
        event: APIEvent<PaginationOrderServiceDTO>
    ) {
        orderServiceService = RetrofitClient.createService(OrderServiceService::class.java)
        call(orderServiceService.loadOrderService(params), event)
    }

}