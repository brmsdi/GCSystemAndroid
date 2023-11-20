package com.brmsdi.gcsystem.data.repository

import androidx.paging.PagingSource
import com.brmsdi.gcsystem.data.dto.PagingDebtModel

/**
 *
 * @author Wisley Bruno Marques França
 * @since 1
 */
interface DebtRepository : CallRepository {
    fun pagingSource(search: String?) : PagingSource<Int, PagingDebtModel>
}