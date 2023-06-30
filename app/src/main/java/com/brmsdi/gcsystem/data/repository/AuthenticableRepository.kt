package com.brmsdi.gcsystem.data.repository

import com.brmsdi.gcsystem.data.dto.TokenChangePasswordDTO
import com.brmsdi.gcsystem.data.listeners.APIEvent
import com.brmsdi.gcsystem.data.listeners.APIEventStringAndJSON
import com.brmsdi.gcsystem.data.dto.ChangePasswordDataDTO
import com.brmsdi.gcsystem.data.dto.TokenDTO

/**
 *
 * @author Wisley Bruno Marques França
 * @since 1
 */

interface AuthenticableRepository : CallRepository {

    fun authenticate(cpf: String, password: String, apiEvent: APIEvent<TokenDTO>)

    fun requestCode(email: String, event: APIEventStringAndJSON)

    fun sendCode(changePasswordDataDTO: ChangePasswordDataDTO, apiEvent: APIEvent<TokenDTO>)

    fun changePassword(tokenChangePasswordDTO: TokenChangePasswordDTO, event:APIEventStringAndJSON)
}