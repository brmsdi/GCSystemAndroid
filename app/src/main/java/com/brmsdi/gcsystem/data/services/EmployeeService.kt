package com.brmsdi.gcsystem.data.services

import com.brmsdi.gcsystem.data.constants.Constant.ENDPOINT.CHANGE_PASSWORD_EMPLOYEE
import com.brmsdi.gcsystem.data.constants.Constant.ENDPOINT.REQUEST_CODE_EMPLOYEE
import com.brmsdi.gcsystem.data.constants.Constant.ENDPOINT.SEND_CODE_EMPLOYEE
import com.brmsdi.gcsystem.data.dto.TokenChangePasswordDTO
import com.brmsdi.gcsystem.ui.utils.Token
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Body
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

    @POST(SEND_CODE_EMPLOYEE)
    fun sendCode(@Query("email") email: String, @Query("code") code: String): Call<Token>

    @POST(CHANGE_PASSWORD_EMPLOYEE)
    fun changePassword(@Body tokenChangePasswordDTO: TokenChangePasswordDTO): Call<ResponseBody>
}