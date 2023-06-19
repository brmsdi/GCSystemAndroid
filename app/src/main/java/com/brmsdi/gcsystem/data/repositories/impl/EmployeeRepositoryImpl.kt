package com.brmsdi.gcsystem.data.repositories.impl

import com.brmsdi.gcsystem.data.listeners.APIEvent
import com.brmsdi.gcsystem.data.listeners.APIEventStringAndJSON
import com.brmsdi.gcsystem.data.remote.retrofit.RetrofitClient
import com.brmsdi.gcsystem.data.repositories.EmployeeRepository
import com.brmsdi.gcsystem.data.repositories.LoginRepository
import com.brmsdi.gcsystem.data.services.EmployeeService
import com.brmsdi.gcsystem.data.services.LoginService
import com.brmsdi.gcsystem.ui.utils.Token

/**
 *
 * @author Wisley Bruno Marques França
 * @since 1
 */
class EmployeeRepositoryImpl : EmployeeRepository {
    private val employeeService = RetrofitClient.createService(EmployeeService::class.java)
    private val loginService = RetrofitClient.createService(LoginService::class.java)

    override fun requestCode(email : String, apiEvent: APIEventStringAndJSON) {
        callStringAndJson(employeeService.requestCode(email), apiEvent)
    }

    override fun authenticate(cpf: String, password: String, apiEvent: APIEvent<Token>) {
        call(loginService.loginEmployee(cpf, password), apiEvent)
    }
}