package com.brmsdi.gcsystem.data.dto

open class ValidationModelDTO(messageError: String = "") {
    private var _status = true
    private var _messageValidation = ""

    init {
        if (messageError.isNotEmpty()) {
            _status = false
            _messageValidation = messageError
        }
    }

    fun status() = _status
    fun message() = _messageValidation
}