package com.brmsdi.gcsystem.ui.utils

import com.google.gson.annotations.SerializedName


/**
 *
 * @author Wisley Bruno Marques França
 * @since 1
 */
class Error {

    @SerializedName("key")
    lateinit var key : String

    @SerializedName("message")
    lateinit var message : String
}