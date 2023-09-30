package com.brmsdi.gcsystem.data.service

import com.brmsdi.gcsystem.data.constants.Constant.ENDPOINT.DATA_SCREEN_NEW_REPAIR_REQUEST
import com.brmsdi.gcsystem.data.constants.Constant.ENDPOINT.NEW_REPAIR_REQUEST
import com.brmsdi.gcsystem.data.constants.Constant.ENDPOINT.REPAIR_REQUESTS_LESSEE
import com.brmsdi.gcsystem.data.constants.Constant.ENDPOINT.SEARCH_REPAIR_REQUEST
import com.brmsdi.gcsystem.data.dto.PaginationRepairRequestDTO
import com.brmsdi.gcsystem.data.dto.RepairRequestRegisterDTO
import com.brmsdi.gcsystem.data.dto.RepairRequestRegisterDataDTO
import com.brmsdi.gcsystem.data.model.RepairRequest
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.QueryMap


/**
 *
 * @author Wisley Bruno Marques França
 * @since 1
 */

interface RepairRequestService {

    @GET(DATA_SCREEN_NEW_REPAIR_REQUEST)
    fun loadDataNewRepairRequest(): Call<RepairRequestRegisterDataDTO>

    @POST(NEW_REPAIR_REQUEST)
    fun save(@Body repairRequestRegisterDTO: RepairRequestRegisterDTO): Call<RepairRequest>

    @GET(REPAIR_REQUESTS_LESSEE)
            /**
             * Carrega uma lista de solicitações de reparo com paginação.
             *
             * @param params Um mapa de parâmetros de consulta opcionais.
             *               Pode incluir os seguintes dados:
             *               - "page": Número da página desejada (opcional).
             *               - "size": Tamanho da página (número de itens por página) (opcional).
             * @return Um objeto [Call] que pode ser usado para fazer a solicitação HTTP.
             */
    fun loadRepairRequests(@QueryMap params: Map<String, String> = mapOf()): Call<PaginationRepairRequestDTO>

    /**
     * Realiza uma busca na lista de solicitações de reparo com paginação.
     *
     * @param params Um mapa de parâmetros de consulta opcionais.
     *               Pode incluir os seguintes dados:
     *               - "page": Número da página desejada (opcional).
     *               - "size": Tamanho da página (número de itens por página) (opcional).
     *               - "keySearch": Parâmetro de pesquisa (Obrigatório)
     * @return Um objeto [Call] que pode ser usado para fazer a solicitação HTTP.
     */
    @GET(SEARCH_REPAIR_REQUEST)
    fun search(@QueryMap params: Map<String, String> = mapOf()): Call<PaginationRepairRequestDTO>
}