package com.brmsdi.gcsystem.data.repository.impl

import com.brmsdi.gcsystem.data.model.Debt
import com.brmsdi.gcsystem.data.pagingsource.GenericPagingSource
import com.brmsdi.gcsystem.data.remote.retrofit.RetrofitClient
import com.brmsdi.gcsystem.data.repository.DebtRepository
import com.brmsdi.gcsystem.data.service.DebtService

class DebtRepositoryImpl : DebtRepository {
    private lateinit var debtService : DebtService

    override fun pagingSource(): GenericPagingSource<Debt> {
        debtService = RetrofitClient.createService(DebtService::class.java)
        return GenericPagingSource(debtService)
    }
}