package com.brmsdi.gcsystem.data.repositories

import com.brmsdi.gcsystem.data.listeners.APIEventStringAndJSON


/**
 *
 * @author Wisley Bruno Marques França
 * @since 1
 */

interface EmployeeRepository : CallRepository {
    fun requestCode(email : String, apiEvent: APIEventStringAndJSON)
}