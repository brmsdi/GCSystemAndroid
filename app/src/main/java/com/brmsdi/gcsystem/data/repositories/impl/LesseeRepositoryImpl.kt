package com.brmsdi.gcsystem.data.repositories.impl

import com.brmsdi.gcsystem.data.dto.TokenChangePasswordDTO
import com.brmsdi.gcsystem.data.listeners.APIEvent
import com.brmsdi.gcsystem.data.listeners.APIEventStringAndJSON
import com.brmsdi.gcsystem.data.remote.retrofit.RetrofitClient
import com.brmsdi.gcsystem.data.repositories.LesseeRepository
import com.brmsdi.gcsystem.data.services.LesseeService
import com.brmsdi.gcsystem.data.services.LoginService
import com.brmsdi.gcsystem.ui.utils.ChangePasswordData
import com.brmsdi.gcsystem.ui.utils.Token

/**
 *
 * @author Wisley Bruno Marques França
 * @since 1
 */
class LesseeRepositoryImpl : LesseeRepository {
    private val lesseeService = RetrofitClient.createService(LesseeService::class.java)
    private val loginService = RetrofitClient.createService(LoginService::class.java)
    override fun authenticate(cpf: String, password: String, apiEvent: APIEvent<Token>) {
        call(loginService.loginLessee(cpf, password), apiEvent)
    }
    override fun requestCode(email: String, event: APIEventStringAndJSON) {
        callStringAndJson(lesseeService.requestCode(email), event)
    }

    override fun sendCode(changePasswordData: ChangePasswordData, apiEvent: APIEvent<Token>) {
        call(lesseeService.sendCode(changePasswordData.email, changePasswordData.code), apiEvent)
    }

    override fun changePassword(
        tokenChangePasswordDTO: TokenChangePasswordDTO,
        event: APIEventStringAndJSON
    ) {
        callStringAndJson(lesseeService.changePassword(tokenChangePasswordDTO), event)
    }
}