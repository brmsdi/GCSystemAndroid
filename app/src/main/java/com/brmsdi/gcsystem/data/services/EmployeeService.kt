package com.brmsdi.gcsystem.data.services

import com.brmsdi.gcsystem.data.constants.Constant.ENDPOINT.REQUEST_CODE_EMPLOYEE
import com.brmsdi.gcsystem.ui.utils.ResponseRequest
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.POST
import retrofit2.http.Query
import retrofit2.http.Streaming


/**
 *
 * @author Wisley Bruno Marques França
 * @since 1
 */

interface EmployeeService {

    @Streaming
    @POST(REQUEST_CODE_EMPLOYEE)
    fun requestCode(@Query("email") email: String): Call<ResponseBody>
}