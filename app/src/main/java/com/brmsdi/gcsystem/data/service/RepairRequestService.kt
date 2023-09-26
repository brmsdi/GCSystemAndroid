package com.brmsdi.gcsystem.data.service

import com.brmsdi.gcsystem.data.constants.Constant.ENDPOINT.DATA_SCREEN_NEW_REPAIR_REQUEST
import com.brmsdi.gcsystem.data.dto.RepairRequestRegisterDataDTO
import retrofit2.Call
import retrofit2.http.GET


/**
 *
 * @author Wisley Bruno Marques França
 * @since 1
 */

interface RepairRequestService {

    @GET(DATA_SCREEN_NEW_REPAIR_REQUEST)
    fun loadDataNewRepairRequest() : Call<RepairRequestRegisterDataDTO>
}