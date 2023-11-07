package com.brmsdi.gcsystem.data.service

import com.brmsdi.gcsystem.data.dto.PaginationDTO
import com.brmsdi.gcsystem.data.model.Debt
import retrofit2.Response
import retrofit2.http.QueryMap


/**
 *
 * @author Wisley Bruno Marques França
 * @since 1
 */
interface PagingSourceService {
    suspend fun load(@QueryMap params: Map<String, String> = mapOf()) : Response<PaginationDTO<Debt>>
}