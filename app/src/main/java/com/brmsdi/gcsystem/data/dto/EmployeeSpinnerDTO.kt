package com.brmsdi.gcsystem.data.dto

import com.brmsdi.gcsystem.data.model.Employee

/**
 *
 * @author Wisley Bruno Marques França
 * @since 1
 */
class EmployeeSpinnerDTO(private val employee: Employee) : SpinnerDTO<Employee> {

    override fun getModel(): Employee {
        return employee
    }

    override fun toString(): String {
        return employee.name
    }
}