package com.brmsdi.gcsystem.data.repository

import com.brmsdi.gcsystem.data.dto.PaginationDebtDTO
import com.brmsdi.gcsystem.data.listeners.APIEvent
import com.brmsdi.gcsystem.data.pagingsource.DebtPagingSource


/**
 *
 * @author Wisley Bruno Marques França
 * @since 1
 */
interface DebtRepository : CallRepository {

    fun loadDebts(params: Map<String, String>, event: APIEvent<PaginationDebtDTO>)

    fun debtPagingSource() : DebtPagingSource
}