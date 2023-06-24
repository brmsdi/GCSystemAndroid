package com.brmsdi.gcsystem.data.dto

/**
 *
 * @author Wisley Bruno Marques França
 * @since 1
 */
data class TokenChangePasswordDTO(private var type: String, private var token: String, private var newPassword: String) {}