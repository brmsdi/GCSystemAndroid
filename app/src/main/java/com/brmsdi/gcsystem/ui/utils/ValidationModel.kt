package com.brmsdi.gcsystem.ui.utils

class ValidationModel(messageError: String = "") {
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