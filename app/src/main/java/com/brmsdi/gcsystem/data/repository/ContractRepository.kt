package com.brmsdi.gcsystem.data.repository

import androidx.paging.PagingSource
import com.brmsdi.gcsystem.data.dto.PagingContractModel

/**
 *
 * @author Wisley Bruno Marques França
 * @since 1
 */

interface ContractRepository : CallRepository {
    fun pagingSource(search: String?) : PagingSource<Int, PagingContractModel>
}