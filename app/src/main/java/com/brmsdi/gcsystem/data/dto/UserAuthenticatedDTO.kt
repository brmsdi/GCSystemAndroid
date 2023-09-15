package com.brmsdi.gcsystem.data.dto

import com.google.gson.annotations.SerializedName


/**
 *
 * @author Wisley Bruno Marques França
 * @since 1
 */
data class UserAuthenticatedDTO(
    @SerializedName("name")
    val name: String,
    @SerializedName("role")
    val role: String
) {
}