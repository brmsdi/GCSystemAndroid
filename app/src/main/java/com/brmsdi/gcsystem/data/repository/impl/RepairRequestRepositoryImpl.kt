package com.brmsdi.gcsystem.data.repository.impl

import com.brmsdi.gcsystem.data.dto.RepairRequestRegisterDataDTO
import com.brmsdi.gcsystem.data.listeners.APIEvent
import com.brmsdi.gcsystem.data.remote.retrofit.RetrofitClient
import com.brmsdi.gcsystem.data.repository.RepairRequestRepository
import com.brmsdi.gcsystem.data.service.RepairRequestService

class RepairRequestRepositoryImpl : RepairRequestRepository {
    private lateinit var repairRequestService : RepairRequestService
    override fun loadDataNewRepairRequest(event: APIEvent<RepairRequestRegisterDataDTO>) {
        repairRequestService = RetrofitClient.createService(RepairRequestService::class.java)
        call(repairRequestService.loadDataNewRepairRequest(), event)
    }
}