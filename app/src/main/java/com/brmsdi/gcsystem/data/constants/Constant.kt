package com.brmsdi.gcsystem.data.constants


/**
 *
 * @author Wisley Bruno Marques França
 * @since 1
 */
class Constant private constructor() {
    object HTTP {
        const val HEADER_AUTHORIZATION = "Authorization"
        const val Bearer = "Bearer"
    }

    object API {
        const val BASE_URL = "http://192.168.15.2:8080/"
    }

    object AUTH {
        const val TOKEN = "TOKEN"
        const val CHANGE_PASSWORD_DATA = "CHANGE_PASSWORD_DATA"
        const val FINGERPRINT = "FINGER"
        const val FINGERPRINT_ON = "1"
    }

    object ENDPOINT {
        const val LOGIN_EMPLOYEES = "login/employees"
        const val LOGIN_LESSEES = "login/lessees"
        const val REQUEST_CODE_EMPLOYEE = "employees/password/request-code"
        const val REQUEST_CODE_LESSEE = "lessees/password/request-code"
        const val SEND_CODE_EMPLOYEE = "employees/password/receive-code"
        const val SEND_CODE_LESSEES = "lessees/password/receive-code"
        const val CHANGE_PASSWORD_EMPLOYEE = "employees/password/change"
        const val CHANGE_PASSWORD_LESSEE = "lessees/password/change"
    }

    object REPAIR {
        const val REPAIR_REQUEST_DATA = "RP"
    }

    object OS {
        const val ORDER_SERVICE_DATA = "OS"
    }

    object CONTRACT {
        const val CONTRACT_DATA = "CD"
    }
}