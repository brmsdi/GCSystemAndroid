package com.brmsdi.gcsystem.data.repository.impl

import com.brmsdi.gcsystem.data.dto.TokenChangePasswordDTO
import com.brmsdi.gcsystem.data.listeners.APIEvent
import com.brmsdi.gcsystem.data.listeners.APIEventStringAndJSON
import com.brmsdi.gcsystem.data.remote.retrofit.RetrofitClient
import com.brmsdi.gcsystem.data.repository.LesseeRepository
import com.brmsdi.gcsystem.data.service.LesseeService
import com.brmsdi.gcsystem.data.service.LoginService
import com.brmsdi.gcsystem.data.dto.ChangePasswordDataDTO
import com.brmsdi.gcsystem.data.dto.TokenDTO

/**
 *
 * @author Wisley Bruno Marques França
 * @since 1
 */
class LesseeRepositoryImpl : LesseeRepository {
    private val lesseeService = RetrofitClient.createService(LesseeService::class.java)
    private val loginService = RetrofitClient.createService(LoginService::class.java)
    override fun authenticate(cpf: String, password: String, apiEvent: APIEvent<TokenDTO>) {
        call(loginService.loginLessee(cpf, password), apiEvent)
    }
    override fun requestCode(email: String, event: APIEventStringAndJSON) {
        callStringAndJson(lesseeService.requestCode(email), event)
    }

    override fun sendCode(changePasswordDataDTO: ChangePasswordDataDTO, apiEvent: APIEvent<TokenDTO>) {
        call(lesseeService.sendCode(changePasswordDataDTO.email, changePasswordDataDTO.code), apiEvent)
    }

    override fun changePassword(
        tokenChangePasswordDTO: TokenChangePasswordDTO,
        event: APIEventStringAndJSON
    ) {
        callStringAndJson(lesseeService.changePassword(tokenChangePasswordDTO), event)
    }
}