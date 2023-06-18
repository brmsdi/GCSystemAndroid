package com.brmsdi.gcsystem.data.listeners


/**
 *
 * @author Wisley Bruno Marques França
 * @since 1
 */

interface APIEventStringAndJSON {
    fun onSuccess()
    fun onError(errorString: String)
    fun onConnectFailure()
}