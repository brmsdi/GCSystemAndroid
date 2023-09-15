package com.brmsdi.gcsystem.data.repository.impl

import com.brmsdi.gcsystem.data.dto.TokenChangePasswordDTO
import com.brmsdi.gcsystem.data.listeners.APIEvent
import com.brmsdi.gcsystem.data.remote.retrofit.RetrofitClient
import com.brmsdi.gcsystem.data.repository.EmployeeRepository
import com.brmsdi.gcsystem.data.service.EmployeeService
import com.brmsdi.gcsystem.data.service.LoginService
import com.brmsdi.gcsystem.data.dto.ChangePasswordDataDTO
import com.brmsdi.gcsystem.data.dto.ResponseDTO
import com.brmsdi.gcsystem.data.dto.TokenDTO
import com.brmsdi.gcsystem.data.dto.UserAuthenticatedDTO

/**
 *
 * @author Wisley Bruno Marques França
 * @since 1
 */
class EmployeeRepositoryImpl : EmployeeRepository {
    private lateinit var employeeService : EmployeeService
    private lateinit var loginService : LoginService

    override fun authenticate(cpf: String, password: String, typeAuth: String, apiEvent: APIEvent<TokenDTO>) {
        loginService = RetrofitClient.createService(LoginService::class.java)
        call(loginService.loginEmployee(cpf, password), apiEvent)
    }

    override fun requestCode(email : String, event: APIEvent<ResponseDTO>) {
        employeeService = RetrofitClient.createService(EmployeeService::class.java)
        call(employeeService.requestCode(email), event)
    }

    override fun sendCode(changePasswordDataDTO: ChangePasswordDataDTO, apiEvent: APIEvent<TokenDTO>) {
        employeeService = RetrofitClient.createService(EmployeeService::class.java)
        call(employeeService.sendCode(changePasswordDataDTO.email, changePasswordDataDTO.code), apiEvent)
    }

    override fun changePassword(
        tokenChangePasswordDTO: TokenChangePasswordDTO,
        event: APIEvent<ResponseDTO>
    ) {
        employeeService = RetrofitClient.createService(EmployeeService::class.java)
        call(employeeService.changePassword(tokenChangePasswordDTO), event)
    }

    override fun verifyToken(event: APIEvent<UserAuthenticatedDTO>) {
        employeeService = RetrofitClient.createService(EmployeeService::class.java)
        call(employeeService.verifyToken(), event)
    }
}