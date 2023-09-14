package com.brmsdi.gcsystem.data.repository

import com.brmsdi.gcsystem.data.listeners.APIEvent
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.net.ssl.HttpsURLConnection

/**
 *
 * @author Wisley Bruno Marques França
 * @since 1
 */

interface CallRepository {
    /**
     * Description
     * @author Wisley Bruno Marques França
     * @param call -
     * @param apiEvent -
     */
    fun <T> call(call: Call<T>, apiEvent: APIEvent<T>) {
        call.enqueue(object : Callback<T> {
            override fun onResponse(call: Call<T>, response: Response<T>) {
                if (response.code() == HttpsURLConnection.HTTP_OK) {
                    response.body()?.let { apiEvent.onResponse(it) }
                } else {
                    apiEvent.onError(response)
                }
            }

            override fun onFailure(call: Call<T>, throwable: Throwable) {
                apiEvent.onFailure(throwable)
            }
        })
    }
}