package com.brmsdi.gcsystem.data.service

import com.brmsdi.gcsystem.data.constants.Constant.ENDPOINT.DATA_SCREEN_NEW_REPAIR_REQUEST
import com.brmsdi.gcsystem.data.constants.Constant.ENDPOINT.NEW_REPAIR_REQUEST
import com.brmsdi.gcsystem.data.constants.Constant.ENDPOINT.REPAIR_REQUESTS_LESSEE
import com.brmsdi.gcsystem.data.constants.Constant.ENDPOINT.REPAIR_REQUEST_ADD_ITEM
import com.brmsdi.gcsystem.data.constants.Constant.ENDPOINT.REPAIR_REQUEST_DELETE
import com.brmsdi.gcsystem.data.constants.Constant.ENDPOINT.REPAIR_REQUEST_DETAIL
import com.brmsdi.gcsystem.data.constants.Constant.ENDPOINT.SEARCH_REPAIR_REQUEST
import com.brmsdi.gcsystem.data.constants.Constant.ENDPOINT.UPDATE_REPAIR_REQUEST
import com.brmsdi.gcsystem.data.dto.PaginationRepairRequestDTO
import com.brmsdi.gcsystem.data.dto.RepairRequestRegisterDTO
import com.brmsdi.gcsystem.data.dto.RepairRequestRegisterDataDTO
import com.brmsdi.gcsystem.data.dto.ResponseDTO
import com.brmsdi.gcsystem.data.model.Item
import com.brmsdi.gcsystem.data.model.RepairRequest
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query
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

    @PUT(UPDATE_REPAIR_REQUEST)
    fun update(@Body repairRequest: RepairRequest): Call<RepairRequest>

    @DELETE("$REPAIR_REQUEST_DELETE/{id}")
    fun delete(@Path(value = "id") id: Int) : Call<ResponseDTO>

    /**
     * Carrega uma lista de solicitações de reparo com paginação.
     *
     * @param params Um mapa de parâmetros de consulta opcionais.
     *               Pode incluir os seguintes dados:
     *               - "page": Número da página desejada (opcional).
     *               - "size": Tamanho da página (número de itens por página) (opcional).
     * @return Um objeto [Call] que pode ser usado para fazer a solicitação HTTP.
     */
    @GET(REPAIR_REQUESTS_LESSEE)
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

    @GET(REPAIR_REQUEST_DETAIL)
    fun details(@Query("id") id: Int): Call<RepairRequest>

    @POST(REPAIR_REQUEST_ADD_ITEM)
    fun addItem(@Query("idRepairRequest") id: Int, @Body item: Item) : Call<Item>
}