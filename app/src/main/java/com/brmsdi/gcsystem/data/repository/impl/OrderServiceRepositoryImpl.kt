package com.brmsdi.gcsystem.data.repository.impl

import androidx.paging.PagingSource
import com.brmsdi.gcsystem.data.dto.PagingOrderServiceModel
import com.brmsdi.gcsystem.data.dto.ResponseDTO
import com.brmsdi.gcsystem.data.listeners.APIEvent
import com.brmsdi.gcsystem.data.model.OrderService
import com.brmsdi.gcsystem.data.pagingsource.impl.OrderServiceSource
import com.brmsdi.gcsystem.data.remote.retrofit.RetrofitClient
import com.brmsdi.gcsystem.data.repository.OrderServiceRepository
import com.brmsdi.gcsystem.data.service.OrderServiceService

class OrderServiceRepositoryImpl : OrderServiceRepository {
    private lateinit var orderServiceService: OrderServiceService

    override fun details(id: Int, event: APIEvent<OrderService>) {
        orderServiceService = RetrofitClient.createService(OrderServiceService::class.java)
        call(orderServiceService.details(id), event)
    }

    override fun close(id: Int, event: APIEvent<ResponseDTO>) {
        orderServiceService = RetrofitClient.createService(OrderServiceService::class.java)
        call(orderServiceService.close(id), event)
    }

    override fun pagingSource(search: String?): PagingSource<Int, PagingOrderServiceModel> {
        orderServiceService = RetrofitClient.createService(OrderServiceService::class.java)
        return OrderServiceSource(search, orderServiceService)
    }

}