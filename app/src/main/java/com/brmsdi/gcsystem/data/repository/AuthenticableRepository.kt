package com.brmsdi.gcsystem.data.repository

import com.brmsdi.gcsystem.data.dto.TokenChangePasswordDTO
import com.brmsdi.gcsystem.data.listeners.APIEvent
import com.brmsdi.gcsystem.data.dto.ChangePasswordDataDTO
import com.brmsdi.gcsystem.data.dto.ResponseDTO
import com.brmsdi.gcsystem.data.dto.TokenDTO
import com.brmsdi.gcsystem.data.dto.UserAuthenticatedDTO

/**
 *
 * @author Wisley Bruno Marques França
 * @since 1
 */

interface AuthenticableRepository : CallRepository {

    fun authenticate(cpf: String, password: String, typeAuth: String, apiEvent: APIEvent<TokenDTO>)

    fun requestCode(email: String, event: APIEvent<ResponseDTO>)

    fun sendCode(changePasswordDataDTO: ChangePasswordDataDTO, apiEvent: APIEvent<TokenDTO>)

    fun changePassword(tokenChangePasswordDTO: TokenChangePasswordDTO, event: APIEvent<ResponseDTO>)

    fun verifyToken(event : APIEvent<UserAuthenticatedDTO>)

}