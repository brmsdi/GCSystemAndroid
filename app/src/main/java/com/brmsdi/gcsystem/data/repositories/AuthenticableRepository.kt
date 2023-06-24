package com.brmsdi.gcsystem.data.repositories

import com.brmsdi.gcsystem.data.dto.TokenChangePasswordDTO
import com.brmsdi.gcsystem.data.listeners.APIEvent
import com.brmsdi.gcsystem.data.listeners.APIEventStringAndJSON
import com.brmsdi.gcsystem.ui.utils.ChangePasswordData
import com.brmsdi.gcsystem.ui.utils.Token

/**
 *
 * @author Wisley Bruno Marques França
 * @since 1
 */

interface AuthenticableRepository : CallRepository {

    fun authenticate(cpf: String, password: String, apiEvent: APIEvent<Token>)

    fun requestCode(email: String, event: APIEventStringAndJSON)

    fun sendCode(changePasswordData: ChangePasswordData, apiEvent: APIEvent<Token>)

    fun changePassword(tokenChangePasswordDTO: TokenChangePasswordDTO, event:APIEventStringAndJSON)
}