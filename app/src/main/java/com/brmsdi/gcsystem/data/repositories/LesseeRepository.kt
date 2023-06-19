package com.brmsdi.gcsystem.data.repositories

import com.brmsdi.gcsystem.data.listeners.APIEventStringAndJSON

/**
 *
 * @author Wisley Bruno Marques França
 * @since 1
 */
interface LesseeRepository : CallRepository, LoginRepository {
    fun requestCode(email: String, event: APIEventStringAndJSON)
}
