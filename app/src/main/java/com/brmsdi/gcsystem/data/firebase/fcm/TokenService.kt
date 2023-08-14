package com.brmsdi.gcsystem.data.firebase.fcm

import com.google.firebase.messaging.FirebaseMessaging

/**
 *
 * @author Wisley Bruno Marques França
 * @since 1
 */

class TokenService private constructor() {

    companion object {

        /**
         * @throws Exception
         */
        fun getToken(): String {
            var token = ""
            FirebaseMessaging
                .getInstance()
                .token
                .addOnSuccessListener { token = it }
                .addOnFailureListener { throw it }
            return token
        }
    }
}