package com.brmsdi.gcsystem.data.services

import com.brmsdi.gcsystem.data.constants.Constant.ENDPOINT.REQUEST_CODE_LESSEE
import com.brmsdi.gcsystem.data.constants.Constant.ENDPOINT.SEND_CODE_LESSEES
import com.brmsdi.gcsystem.ui.utils.Token
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

interface LesseeService {

    @Streaming
    @POST(REQUEST_CODE_LESSEE)
    fun requestCode(@Query("email") email : String) : Call<ResponseBody>

    @POST(SEND_CODE_LESSEES)
    fun sendCode(@Query("email") email: String, @Query("code") code: String) : Call<Token>
}