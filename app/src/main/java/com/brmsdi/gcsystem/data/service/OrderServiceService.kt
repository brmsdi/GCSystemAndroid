package com.brmsdi.gcsystem.data.service

import com.brmsdi.gcsystem.data.constants.Constant.ENDPOINT.ORDER_SERVICES_EMPLOYEE
import com.brmsdi.gcsystem.data.constants.Constant.ENDPOINT.ORDER_SERVICE_DETAILS
import com.brmsdi.gcsystem.data.constants.Constant.ENDPOINT.SEARCH_ORDER_SERVICE
import com.brmsdi.gcsystem.data.dto.PaginationOrderServiceDTO
import com.brmsdi.gcsystem.data.model.OrderService
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.QueryMap


/**
 *
 * @author Wisley Bruno Marques França
 * @since 1
 */

interface OrderServiceService {

    /**
     * Carrega uma lista de ordens de serviço com paginação.
     *
     * @param params Um mapa de parâmetros de consulta opcionais.
     *               Pode incluir os seguintes dados:
     *               - "page": Número da página desejada (opcional).
     *               - "size": Tamanho da página (número de itens por página) (opcional).
     * @return Um objeto [Call] que pode ser usado para fazer a solicitação HTTP.
     */
    @GET(ORDER_SERVICES_EMPLOYEE)
    fun loadOrderService(@QueryMap params: Map<String, String>) : Call<PaginationOrderServiceDTO>

    /**
     * Realiza uma busca na lista de ordem de serviço com paginação.
     *
     * @param params Um mapa de parâmetros de consulta opcionais.
     *               Pode incluir os seguintes dados:
     *               - "page": Número da página desejada (opcional).
     *               - "size": Tamanho da página (número de itens por página) (opcional).
     *               - "idOrderService": Parâmetro de pesquisa (Obrigatório)
     * @return Um objeto [Call] que pode ser usado para fazer a solicitação HTTP.
     */
    @GET(SEARCH_ORDER_SERVICE)
    fun search(@QueryMap params: Map<String, String> = mapOf()): Call<PaginationOrderServiceDTO>

    @GET(ORDER_SERVICE_DETAILS)
    fun details(@Query("id") id: Int): Call<OrderService>
}