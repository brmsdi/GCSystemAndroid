package com.brmsdi.gcsystem.data.dto

import com.google.gson.annotations.SerializedName

/**
 *
 * @author Wisley Bruno Marques França
 * @since 1
 */
class ErrorDTO {

    @SerializedName("message")
    lateinit var message : String
}