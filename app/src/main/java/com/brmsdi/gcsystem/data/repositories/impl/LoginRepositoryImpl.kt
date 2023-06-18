package com.brmsdi.gcsystem.data.repositories.impl

import com.brmsdi.gcsystem.data.listeners.APIEvent
import com.brmsdi.gcsystem.data.remote.retrofit.RetrofitClient
import com.brmsdi.gcsystem.data.repositories.LoginRepository
import com.brmsdi.gcsystem.data.services.LoginService
import com.brmsdi.gcsystem.ui.utils.Token

/**
 *
 * @author Wisley Bruno Marques França
 * @since 1
 */
class LoginRepositoryImpl : LoginRepository {
    private val loginService = RetrofitClient.createService(LoginService::class.java)
    override fun authenticateEmployee(cpf: String, password: String, apiEvent: APIEvent<Token>) {
        call(loginService.loginEmployee(cpf, password), apiEvent)
    }

    override fun authenticateLessee(cpf: String, password: String, apiEvent: APIEvent<Token>) {
        call(loginService.loginLessee(cpf, password), apiEvent)
    }
}