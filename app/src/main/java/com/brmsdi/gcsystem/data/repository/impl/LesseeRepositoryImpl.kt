package com.brmsdi.gcsystem.data.repository.impl

import com.brmsdi.gcsystem.data.dto.TokenChangePasswordDTO
import com.brmsdi.gcsystem.data.listeners.APIEvent
import com.brmsdi.gcsystem.data.remote.retrofit.RetrofitClient
import com.brmsdi.gcsystem.data.repository.LesseeRepository
import com.brmsdi.gcsystem.data.service.LesseeService
import com.brmsdi.gcsystem.data.service.LoginService
import com.brmsdi.gcsystem.data.dto.ChangePasswordDataDTO
import com.brmsdi.gcsystem.data.dto.ResponseDTO
import com.brmsdi.gcsystem.data.dto.TokenDTO
import com.brmsdi.gcsystem.data.dto.UserAuthenticatedDTO
import com.brmsdi.gcsystem.data.model.Lessee

/**
 *
 * @author Wisley Bruno Marques França
 * @since 1
 */
class LesseeRepositoryImpl : LesseeRepository {
    private lateinit var lesseeService: LesseeService
    private lateinit var loginService: LoginService
    override fun myAccount(event: APIEvent<Lessee>) {
        lesseeService = RetrofitClient.createService(LesseeService::class.java)
        call(lesseeService.myAccount(), event)
    }

    override fun authenticate(
        cpf: String,
        password: String,
        typeAuth: String,
        apiEvent: APIEvent<TokenDTO>
    ) {
        loginService = RetrofitClient.createService(LoginService::class.java)
        call(loginService.loginLessee(cpf, password), apiEvent)
    }

    override fun requestCode(email: String, event: APIEvent<ResponseDTO>) {
        lesseeService = RetrofitClient.createService(LesseeService::class.java)
        call(lesseeService.requestCode(email), event)
    }

    override fun sendCode(
        changePasswordDataDTO: ChangePasswordDataDTO,
        apiEvent: APIEvent<TokenDTO>
    ) {
        lesseeService = RetrofitClient.createService(LesseeService::class.java)
        call(
            lesseeService.sendCode(changePasswordDataDTO.email, changePasswordDataDTO.code),
            apiEvent
        )
    }

    override fun changePassword(
        tokenChangePasswordDTO: TokenChangePasswordDTO,
        event: APIEvent<ResponseDTO>
    ) {
        lesseeService = RetrofitClient.createService(LesseeService::class.java)
        call(lesseeService.changePassword(tokenChangePasswordDTO), event)
    }

    override fun verifyToken(event: APIEvent<UserAuthenticatedDTO>) {
        lesseeService = RetrofitClient.createService(LesseeService::class.java)
        call(lesseeService.verifyToken(), event)
    }
}