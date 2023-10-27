package com.brmsdi.gcsystem.data.service

import com.brmsdi.gcsystem.data.constants.Constant.ENDPOINT.DEBTS_LESSEE
import com.brmsdi.gcsystem.data.dto.PaginationDebtDTO
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.QueryMap

/**
 *
 * @author Wisley Bruno Marques França
 * @since 1
 */

interface DebtService {

    /**
     * Carrega uma lista de debitos com paginação.
     *
     * @param params Um mapa de parâmetros de consulta opcionais.
     *               Pode incluir os seguintes dados:
     *               - "page": Número da página desejada (opcional).
     *               - "size": Tamanho da página (número de itens por página) (opcional).
     * @return Um objeto [Call] que pode ser usado para fazer a solicitação HTTP.
     */
    @GET(DEBTS_LESSEE)
    fun loadDebts(@QueryMap params: Map<String, String> = mapOf()) : Call<PaginationDebtDTO>

    @GET(DEBTS_LESSEE)
    suspend fun load(@QueryMap params: Map<String, String> = mapOf()) : Response<PaginationDebtDTO>
}