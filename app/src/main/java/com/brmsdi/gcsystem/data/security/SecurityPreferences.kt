package com.brmsdi.gcsystem.data.security

import android.content.Context
import android.content.SharedPreferences

/**
 *
 * @author Wisley Bruno Marques França
 * @since 1
 */
class SecurityPreferences(context: Context) {
    private val preferences : SharedPreferences = context.getSharedPreferences("gc", Context.MODE_PRIVATE)

    fun story(key: String, value: String) {
        preferences.edit().putString(key, value).apply()
    }

    fun remove(key: String) {
        preferences.edit().remove(key).apply()
    }

    fun get(key: String): String {
        return preferences.getString(key, "") ?: ""
    }
}