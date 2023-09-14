package com.brmsdi.gcsystem.data.dto

import com.google.gson.annotations.SerializedName


/**
 *
 * @author Wisley Bruno Marques França
 * @since 1
 */
data class ResponseDTO(
    @SerializedName("key")
    val key: String,
    @SerializedName("message")
    val message: String
) {
}