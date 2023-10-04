package com.brmsdi.gcsystem.data.repository.impl

import com.brmsdi.gcsystem.data.dto.PaginationContractDTO
import com.brmsdi.gcsystem.data.listeners.APIEvent
import com.brmsdi.gcsystem.data.remote.retrofit.RetrofitClient
import com.brmsdi.gcsystem.data.repository.ContractRepository
import com.brmsdi.gcsystem.data.service.ContractService

class ContractRepositoryImpl : ContractRepository {
    private lateinit var contractService: ContractService
    override fun loadContracts(
        params: Map<String, String>,
        event: APIEvent<PaginationContractDTO>
    ) {
        contractService = RetrofitClient.createService(ContractService::class.java)
        call(contractService.loadContracts(params), event)
    }
}