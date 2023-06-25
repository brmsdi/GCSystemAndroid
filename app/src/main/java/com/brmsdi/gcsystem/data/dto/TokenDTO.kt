package com.brmsdi.gcsystem.data.dto

import com.google.gson.annotations.SerializedName


/**
 *
 * @author Wisley Bruno Marques França
 * @since 1
 */
class TokenDTO {

    @SerializedName("type")
    lateinit var type : String

    @SerializedName("token")
    lateinit var token : String
}