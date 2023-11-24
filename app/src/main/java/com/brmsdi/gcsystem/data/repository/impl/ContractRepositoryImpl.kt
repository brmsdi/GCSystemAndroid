package com.brmsdi.gcsystem.data.repository.impl

import androidx.paging.PagingSource
import com.brmsdi.gcsystem.data.dto.PagingContractModel
import com.brmsdi.gcsystem.data.pagingsource.impl.ContractPagingSource
import com.brmsdi.gcsystem.data.remote.retrofit.RetrofitClient
import com.brmsdi.gcsystem.data.repository.ContractRepository
import com.brmsdi.gcsystem.data.service.ContractService

class ContractRepositoryImpl : ContractRepository {
    private lateinit var contractService: ContractService

    override fun pagingSource(search: String?): PagingSource<Int, PagingContractModel> {
        contractService = RetrofitClient.createService(ContractService::class.java)
        return ContractPagingSource(search, contractService)
    }
}