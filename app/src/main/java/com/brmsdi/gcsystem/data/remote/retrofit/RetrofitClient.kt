package com.brmsdi.gcsystem.data.remote.retrofit

import com.brmsdi.gcsystem.data.constants.Constant.API.BASE_URL
import com.brmsdi.gcsystem.data.constants.Constant.HTTP.Bearer
import com.brmsdi.gcsystem.data.constants.Constant.HTTP.HEADER_AUTHORIZATION
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 *
 * @author Wisley Bruno Marques França
 * @since 1
 */
class RetrofitClient private constructor() {

    companion object {
        @Volatile
        private var INSTANCE: Retrofit? = null
        private var token = ""

        @Synchronized
        private fun getRetrofitInstance(): Retrofit {
            val httpClient = OkHttpClient.Builder()
            if (INSTANCE == null) {
                if (token.isNotEmpty()) addInterceptor(httpClient)
                INSTANCE = Retrofit
                    .Builder()
                    .baseUrl(BASE_URL)
                    .client(httpClient.build())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
            }
            return INSTANCE!!
        }

        private fun addInterceptor(httpClient: OkHttpClient.Builder) {
            httpClient.addInterceptor {
                val request = it
                    .request()
                    .newBuilder()
                    .header(HEADER_AUTHORIZATION, token)
                    .build()
                it.proceed(request)
            }
        } // END addInterceptor

        fun addToken(token: String) {
            if (token.isEmpty()) return
            this.token = String.format("%s %s", Bearer, token)
            INSTANCE = null
        }

        fun removeToken() {
            this.token = ""
            INSTANCE = null
        }

        fun <S> createService(service: Class<S>): S {
            return getRetrofitInstance().create(service)
        }
    }
}