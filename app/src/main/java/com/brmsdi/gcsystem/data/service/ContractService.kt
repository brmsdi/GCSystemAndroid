package com.brmsdi.gcsystem.data.service

import com.brmsdi.gcsystem.data.constants.Constant.ENDPOINT.CONTRACTS_LESSEE
import com.brmsdi.gcsystem.data.dto.PaginationContractDTO
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.QueryMap

/**
 *
 * @author Wisley Bruno Marques França
 * @since 1
 */

interface ContractService {

    @GET(CONTRACTS_LESSEE)
    fun loadContracts(@QueryMap params: Map<String, String> = mapOf()): Call<PaginationContractDTO>
}