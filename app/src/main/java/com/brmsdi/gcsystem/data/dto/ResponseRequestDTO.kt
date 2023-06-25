package com.brmsdi.gcsystem.data.dto

import com.google.gson.annotations.SerializedName
import java.sql.Timestamp

/**
 *
 * @author Wisley Bruno Marques França
 * @since 1
 */

class ResponseRequestDTO {

    @SerializedName("timestamp")
    lateinit var timestamp : Timestamp

    @SerializedName("status")
    var status = 0

    @SerializedName("code")
    lateinit var code : String

    @SerializedName("errors")
    lateinit var errors : List<ErrorDTO>
}