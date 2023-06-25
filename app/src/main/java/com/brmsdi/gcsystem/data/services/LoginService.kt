package com.brmsdi.gcsystem.data.services

import com.brmsdi.gcsystem.data.constants.Constant.ENDPOINT.LOGIN_EMPLOYEES
import com.brmsdi.gcsystem.data.constants.Constant.ENDPOINT.LOGIN_LESSEES
import com.brmsdi.gcsystem.data.dto.TokenDTO
import retrofit2.Call
import retrofit2.http.POST
import retrofit2.http.Query

/**
 *
 * @author Wisley Bruno Marques França
 * @since 1
 * @version 1
 */
interface LoginService {

    @POST(LOGIN_LESSEES)
    fun loginLessee(@Query("username") username : String, @Query("password") password : String) : Call<TokenDTO>

    @POST(LOGIN_EMPLOYEES)
    fun loginEmployee(@Query("username") username : String, @Query("password") password : String) : Call<TokenDTO>
}