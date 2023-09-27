package com.brmsdi.gcsystem.data.repository

import com.brmsdi.gcsystem.data.dto.PaginationRepairRequestDTO
import com.brmsdi.gcsystem.data.dto.RepairRequestRegisterDTO
import com.brmsdi.gcsystem.data.dto.RepairRequestRegisterDataDTO
import com.brmsdi.gcsystem.data.listeners.APIEvent
import com.brmsdi.gcsystem.data.model.RepairRequest
import retrofit2.Call

/**
 *
 * @author Wisley Bruno Marques França
 * @since 1
 */

interface RepairRequestRepository : CallRepository {
    fun loadDataNewRepairRequest(event: APIEvent<RepairRequestRegisterDataDTO>)

    fun save(repairRequestRegisterDTO: RepairRequestRegisterDTO, event: APIEvent<RepairRequest>)

    /**
     * Carrega uma lista de solicitações de reparo com paginação.
     *
     * @param params Um mapa de parâmetros de consulta opcionais.
     *               Pode incluir os seguintes dados:
     *               - "page": Número da página desejada (opcional).
     *               - "size": Tamanho da página (número de itens por página) (opcional).
     */
    fun loadRepairRequests(params: Map<String, String>, event: APIEvent<PaginationRepairRequestDTO>)
}