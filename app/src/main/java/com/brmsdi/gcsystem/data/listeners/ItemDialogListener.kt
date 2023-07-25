package com.brmsdi.gcsystem.data.listeners


/**
 *
 * @author Wisley Bruno Marques França
 * @since 1
 */

interface ItemDialogListener {

    fun add(description: String, quantity: String, value: String)

    fun cancel()
}