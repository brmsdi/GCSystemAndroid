package com.brmsdi.gcsystem.data.listeners

import retrofit2.Response


/**
 *
 * @author Wisley Bruno Marques França
 * @since 1
 */

interface APIEvent<T> {
    fun onResponse(model : T)
    fun onError(response: Response<T>)
    fun onFailure(throwable: Throwable)
}