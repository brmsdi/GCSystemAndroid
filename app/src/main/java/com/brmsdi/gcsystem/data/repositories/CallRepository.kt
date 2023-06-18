package com.brmsdi.gcsystem.data.repositories

import com.brmsdi.gcsystem.data.listeners.APIEvent
import com.brmsdi.gcsystem.data.listeners.APIEventStringAndJSON
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.net.ConnectException
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

    fun <T> callStringAndJson(call: Call<T>, event: APIEventStringAndJSON) {
        try {
            val response  = call.execute()
            if (response.isSuccessful) {
                event.onSuccess()

            } else {
                response.errorBody()?.string()?.let { event.onError(it) }
            }
        } catch (exception : ConnectException) {
            event.onConnectFailure()
        }
    }
}