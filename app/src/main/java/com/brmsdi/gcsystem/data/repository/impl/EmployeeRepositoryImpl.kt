package com.brmsdi.gcsystem.data.repository.impl

import com.brmsdi.gcsystem.data.dto.TokenChangePasswordDTO
import com.brmsdi.gcsystem.data.listeners.APIEvent
import com.brmsdi.gcsystem.data.listeners.APIEventStringAndJSON
import com.brmsdi.gcsystem.data.remote.retrofit.RetrofitClient
import com.brmsdi.gcsystem.data.repository.EmployeeRepository
import com.brmsdi.gcsystem.data.service.EmployeeService
import com.brmsdi.gcsystem.data.service.LoginService
import com.brmsdi.gcsystem.data.dto.ChangePasswordDataDTO
import com.brmsdi.gcsystem.data.dto.TokenDTO

/**
 *
 * @author Wisley Bruno Marques França
 * @since 1
 */
class EmployeeRepositoryImpl : EmployeeRepository {
    private val employeeService = RetrofitClient.createService(EmployeeService::class.java)
    private val loginService = RetrofitClient.createService(LoginService::class.java)

    override fun authenticate(cpf: String, password: String, apiEvent: APIEvent<TokenDTO>) {
        call(loginService.loginEmployee(cpf, password), apiEvent)
    }

    override fun requestCode(email : String, event: APIEventStringAndJSON) {
        callStringAndJson(employeeService.requestCode(email), event)
    }

    override fun sendCode(changePasswordDataDTO: ChangePasswordDataDTO, apiEvent: APIEvent<TokenDTO>) {
        call(employeeService.sendCode(changePasswordDataDTO.email, changePasswordDataDTO.code), apiEvent)
    }

    override fun changePassword(
        tokenChangePasswordDTO: TokenChangePasswordDTO,
        event: APIEventStringAndJSON
    ) {
        callStringAndJson(employeeService.changePassword(tokenChangePasswordDTO), event)
    }
}