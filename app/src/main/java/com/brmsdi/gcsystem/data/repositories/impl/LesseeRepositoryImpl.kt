package com.brmsdi.gcsystem.data.repositories.impl

import com.brmsdi.gcsystem.data.listeners.APIEventStringAndJSON
import com.brmsdi.gcsystem.data.remote.retrofit.RetrofitClient
import com.brmsdi.gcsystem.data.repositories.LesseeRepository
import com.brmsdi.gcsystem.data.services.LesseeService

/**
 *
 * @author Wisley Bruno Marques França
 * @since 1
 */
class LesseeRepositoryImpl : LesseeRepository {
    private val lesseeService = RetrofitClient.createService(LesseeService::class.java)

    override fun requestCode(email: String, event: APIEventStringAndJSON) {
        callStringAndJson(lesseeService.requestCode(email), event)
    }
}