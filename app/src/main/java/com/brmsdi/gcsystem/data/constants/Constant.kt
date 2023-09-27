package com.brmsdi.gcsystem.data.constants

import com.brmsdi.gcsystem.data.constants.Constant.API.DEFAULT_API
import com.brmsdi.gcsystem.data.constants.Constant.API.TYPE_AND_VERSION

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
        private const val TYPE = "api"
        private const val VERSION = "v1"
        private const val AGENT = "mobile"
        const val BASE_URL = "http://192.168.15.2:8080/"
        const val TYPE_AND_VERSION = "$TYPE/$VERSION"
        const val DEFAULT_API = "$TYPE_AND_VERSION/$AGENT"
    }

    object AUTH {
        const val TOKEN = "TOKEN"
        const val CHANGE_PASSWORD_DATA = "CHANGE_PASSWORD_DATA"
        const val FINGERPRINT = "FINGER"
        const val FINGERPRINT_ON = "1"
        const val REQUEST_CODE_UNLOCK = 123
        const val TYPE_AUTH = "TYPE_AUTH"
    }

    object ENDPOINT {
        const val LOGIN_EMPLOYEES = "$TYPE_AND_VERSION/login/employees"
        const val LOGIN_LESSEES = "$TYPE_AND_VERSION/login/lessees"
        const val REQUEST_CODE_EMPLOYEE = "$DEFAULT_API/employees/password/request-code"
        const val REQUEST_CODE_LESSEE = "$DEFAULT_API/lessees/password/request-code"
        const val SEND_CODE_EMPLOYEE = "$DEFAULT_API/employees/password/receive-code"
        const val SEND_CODE_LESSEES = "$DEFAULT_API/lessees/password/receive-code"
        const val CHANGE_PASSWORD_EMPLOYEE = "$DEFAULT_API/employees/password/change"
        const val CHANGE_PASSWORD_LESSEE = "$DEFAULT_API/lessees/password/change"
        const val VALIDATE_TOKEN = "$DEFAULT_API/validate/token"
        const val MY_ACCOUNT_EMPLOYEE = "$DEFAULT_API/employees/account"
        const val MY_ACCOUNT_LESSEE = "$DEFAULT_API/lessees/account"
        const val DATA_SCREEN_NEW_REPAIR_REQUEST = "$DEFAULT_API/repair-requests/screen-new-data"
        const val NEW_REPAIR_REQUEST = "$DEFAULT_API/repair-requests/lessee"
        const val REPAIR_REQUESTS_LESSEE = "$DEFAULT_API/repair-requests/lessee"
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

    object PERMISSION {
        const val ALL = 1
    }

    object NOTIFICATION {
        const val CHANNEL1 = "com.brmsdi.gcsystem"
        const val CHANNEL1_ID = "OS"
    }
}