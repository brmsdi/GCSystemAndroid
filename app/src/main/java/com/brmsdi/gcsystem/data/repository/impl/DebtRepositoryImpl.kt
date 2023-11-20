package com.brmsdi.gcsystem.data.repository.impl

import androidx.paging.PagingSource
import com.brmsdi.gcsystem.data.dto.PagingDebtModel
import com.brmsdi.gcsystem.data.pagingsource.impl.DebtPagingSource
import com.brmsdi.gcsystem.data.remote.retrofit.RetrofitClient
import com.brmsdi.gcsystem.data.repository.DebtRepository
import com.brmsdi.gcsystem.data.service.DebtService

class DebtRepositoryImpl : DebtRepository {
    private lateinit var debtService : DebtService

    override fun pagingSource(search: String?): PagingSource<Int, PagingDebtModel> {
        debtService = RetrofitClient.createService(DebtService::class.java)
        return DebtPagingSource(search, debtService)
    }
}