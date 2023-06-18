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
        private lateinit var INSTANCE : Retrofit
        private var token = ""

        @Synchronized
        private fun getRetrofitInstance() : Retrofit {
            val httpClient = OkHttpClient.Builder()
            addInterceptor(httpClient)
            if (!::INSTANCE.isInitialized) {
                INSTANCE = Retrofit
                    .Builder()
                    .baseUrl(BASE_URL)
                    .client(httpClient.build())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
            }
            return INSTANCE
        }

        private fun addInterceptor(httpClient: OkHttpClient.Builder) {
            if (token.isEmpty()) return
            httpClient.addInterceptor {
               val request = it
                   .request()
                   .newBuilder()
                   .header(HEADER_AUTHORIZATION, token)
                   .build()
                it.proceed(request)
            }
        } // END addInterceptor

        fun addToken(token : String) {
            this.token = String.format("%s %s", Bearer, token)
        }

        fun <S> createService(service : Class<S>): S {
            return getRetrofitInstance().create(service)
        }
    }
}