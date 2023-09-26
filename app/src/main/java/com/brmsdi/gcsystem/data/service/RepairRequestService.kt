package com.brmsdi.gcsystem.data.service

import com.brmsdi.gcsystem.data.constants.Constant.ENDPOINT.DATA_SCREEN_NEW_REPAIR_REQUEST
import com.brmsdi.gcsystem.data.constants.Constant.ENDPOINT.NEW_REPAIR_REQUEST
import com.brmsdi.gcsystem.data.dto.RepairRequestRegisterDTO
import com.brmsdi.gcsystem.data.dto.RepairRequestRegisterDataDTO
import com.brmsdi.gcsystem.data.model.RepairRequest
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST


/**
 *
 * @author Wisley Bruno Marques França
 * @since 1
 */

interface RepairRequestService {

    @GET(DATA_SCREEN_NEW_REPAIR_REQUEST)
    fun loadDataNewRepairRequest() : Call<RepairRequestRegisterDataDTO>

    @POST(NEW_REPAIR_REQUEST)
    fun save(@Body repairRequestRegisterDTO: RepairRequestRegisterDTO) : Call<RepairRequest>
}