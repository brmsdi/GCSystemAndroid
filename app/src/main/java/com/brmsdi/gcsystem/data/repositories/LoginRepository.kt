package com.brmsdi.gcsystem.data.repositories

import com.brmsdi.gcsystem.data.listeners.APIEvent
import com.brmsdi.gcsystem.ui.utils.Token

/**
 *
 * @author Wisley Bruno Marques França
 * @since 1
 */

interface LoginRepository : CallRepository {
    fun authenticateEmployee(cpf: String, password: String, apiEvent: APIEvent<Token>)
    fun authenticateLessee(cpf: String, password: String, apiEvent: APIEvent<Token>)
}