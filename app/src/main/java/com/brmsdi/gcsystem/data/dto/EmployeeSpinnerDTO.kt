package com.brmsdi.gcsystem.data.dto

import com.brmsdi.gcsystem.data.model.Employee

/**
 *
 * @author Wisley Bruno Marques França
 * @since 1
 */
class EmployeeSpinnerDTO(employee: Employee) {
    private var _name : String = ""
    init {
        _name = employee.name
    }

    override fun toString(): String {
        if (_name.isEmpty()) {
            return super.toString()
        }
        return _name
    }
}