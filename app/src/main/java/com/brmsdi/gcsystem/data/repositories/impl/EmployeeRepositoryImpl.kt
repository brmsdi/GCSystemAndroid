package com.brmsdi.gcsystem.data.repositories.impl

import com.brmsdi.gcsystem.data.listeners.APIEventStringAndJSON
import com.brmsdi.gcsystem.data.remote.retrofit.RetrofitClient
import com.brmsdi.gcsystem.data.repositories.EmployeeRepository
import com.brmsdi.gcsystem.data.services.EmployeeService

/**
 *
 * @author Wisley Bruno Marques França
 * @since 1
 */
class EmployeeRepositoryImpl : EmployeeRepository {
    private val employeeService = RetrofitClient.createService(EmployeeService::class.java)

    override fun requestCode(email : String, apiEvent: APIEventStringAndJSON) {
        callStringAndJson(employeeService.requestCode(email), apiEvent)
    }
}