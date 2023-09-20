package com.brmsdi.gcsystem.data.repository

import com.brmsdi.gcsystem.data.listeners.APIEvent
import com.brmsdi.gcsystem.data.model.Employee


/**
 *
 * @author Wisley Bruno Marques França
 * @since 1
 */

interface EmployeeRepository : CallRepository, AuthenticableRepository {

    fun myAccount(event: APIEvent<Employee>)
}