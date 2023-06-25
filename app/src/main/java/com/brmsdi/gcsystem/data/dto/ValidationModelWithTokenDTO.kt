package com.brmsdi.gcsystem.data.dto


/**
 *
 * @author Wisley Bruno Marques França
 * @since 1
 */
class ValidationModelWithTokenDTO(tokenDTO: TokenDTO? = null, messageError: String = "") : ValidationModelDTO(messageError = messageError) {
    private var _tokenDTO : TokenDTO? = null

    init {
        tokenDTO?.let {
            _tokenDTO = it
        }
    }

    fun token() = _tokenDTO
}