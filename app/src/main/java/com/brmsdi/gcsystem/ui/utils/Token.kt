package com.brmsdi.gcsystem.ui.utils

import com.google.gson.annotations.SerializedName


/**
 *
 * @author Wisley Bruno Marques França
 * @since 1
 */
class Token {

    @SerializedName("type")
    lateinit var type : String

    @SerializedName("token")
    lateinit var token : String
}