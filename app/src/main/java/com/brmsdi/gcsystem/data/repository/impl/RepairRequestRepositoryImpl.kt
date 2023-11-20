package com.brmsdi.gcsystem.data.repository.impl

import androidx.paging.PagingSource
import com.brmsdi.gcsystem.data.dto.PaginationRepairRequestDTO
import com.brmsdi.gcsystem.data.dto.PagingRepairRequestModel
import com.brmsdi.gcsystem.data.dto.RepairRequestRegisterDTO
import com.brmsdi.gcsystem.data.dto.RepairRequestRegisterDataDTO
import com.brmsdi.gcsystem.data.dto.ResponseDTO
import com.brmsdi.gcsystem.data.listeners.APIEvent
import com.brmsdi.gcsystem.data.model.Item
import com.brmsdi.gcsystem.data.model.RepairRequest
import com.brmsdi.gcsystem.data.pagingsource.impl.RepairRequestPagingSource
import com.brmsdi.gcsystem.data.remote.retrofit.RetrofitClient
import com.brmsdi.gcsystem.data.repository.RepairRequestRepository
import com.brmsdi.gcsystem.data.service.RepairRequestService

class RepairRequestRepositoryImpl : RepairRequestRepository {
    private lateinit var repairRequestService: RepairRequestService
    override fun loadDataNewRepairRequest(event: APIEvent<RepairRequestRegisterDataDTO>) {
        repairRequestService = RetrofitClient.createService(RepairRequestService::class.java)
        call(repairRequestService.loadDataNewRepairRequest(), event)
    }

    override fun save(
        repairRequestRegisterDTO: RepairRequestRegisterDTO,
        event: APIEvent<RepairRequest>
    ) {
        repairRequestService = RetrofitClient.createService(RepairRequestService::class.java)
        call(repairRequestService.save(repairRequestRegisterDTO), event)
    }

    override fun update(repairRequest: RepairRequest, event: APIEvent<RepairRequest>) {
        repairRequestService = RetrofitClient.createService(RepairRequestService::class.java)
        call(repairRequestService.update(repairRequest), event)
    }

    override fun delete(id: Int, event: APIEvent<ResponseDTO>) {
        repairRequestService = RetrofitClient.createService(RepairRequestService::class.java)
        call(repairRequestService.delete(id), event)
    }

    override fun loadRepairRequests(
        params: Map<String, String>,
        event: APIEvent<PaginationRepairRequestDTO>
    ) {
        repairRequestService = RetrofitClient.createService(RepairRequestService::class.java)
//        call(repairRequestService.loadRepairRequests(params), event)
    }

    override fun pagingSource(search: String?): PagingSource<Int, PagingRepairRequestModel> {
        repairRequestService = RetrofitClient.createService(RepairRequestService::class.java)
        return RepairRequestPagingSource(search, repairRequestService)
    }

    override fun search(params: Map<String, String>, event: APIEvent<PaginationRepairRequestDTO>) {
        repairRequestService = RetrofitClient.createService(RepairRequestService::class.java)
       // call(repairRequestService.search(params), event)
    }

    override fun getById(id: Int, event: APIEvent<RepairRequest>) {
        repairRequestService = RetrofitClient.createService(RepairRequestService::class.java)
        call(repairRequestService.details(id), event)
    }

    override fun addItem(idRepairRequest: Int, item: Item, event: APIEvent<Item>) {
        repairRequestService = RetrofitClient.createService(RepairRequestService::class.java)
        call(repairRequestService.addItem(idRepairRequest, item), event)
    }

    override fun removeItem(idRepairRequest: Int, idItem: Int, event: APIEvent<ResponseDTO>) {
        repairRequestService = RetrofitClient.createService(RepairRequestService::class.java)
        call(repairRequestService.removeItem(idRepairRequest, idItem), event)
    }
}