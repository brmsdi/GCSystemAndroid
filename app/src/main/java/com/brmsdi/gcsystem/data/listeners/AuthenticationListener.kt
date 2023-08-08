package com.brmsdi.gcsystem.data.listeners


/**
 *
 * @author Wisley Bruno Marques França
 * @since 1
 */

interface AuthenticationListener {
    fun onSuccess()
    fun error()
    fun failed()
}