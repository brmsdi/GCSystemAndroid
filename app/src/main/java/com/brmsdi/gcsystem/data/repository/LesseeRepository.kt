package com.brmsdi.gcsystem.data.repository

import com.brmsdi.gcsystem.data.listeners.APIEvent
import com.brmsdi.gcsystem.data.model.Lessee

/**
 *
 * @author Wisley Bruno Marques França
 * @since 1
 */
interface LesseeRepository : CallRepository, AuthenticableRepository {
    fun myAccount(event: APIEvent<Lessee>)
}
