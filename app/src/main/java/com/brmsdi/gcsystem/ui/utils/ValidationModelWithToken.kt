package com.brmsdi.gcsystem.ui.utils


/**
 *
 * @author Wisley Bruno Marques França
 * @since 1
 */
class ValidationModelWithToken(var token: Token? = null, messageError: String = "") : ValidationModel(messageError = messageError) {
    private var _token : Token? = null

    init {
        token?.let {
            _token = it
        }
    }

    fun token() = _token
}