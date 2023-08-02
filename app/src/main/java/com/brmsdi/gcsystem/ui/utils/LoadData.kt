package com.brmsdi.gcsystem.ui.utils

import android.os.Build
import android.os.Bundle

/**
 *
 * @author Wisley Bruno Marques França
 * @since 1
 */

interface LoadData {
    fun <S> load(arguments: Bundle?, key: String, data: Class<S>): S? {
        arguments.let {
            return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU)
                it!!.getParcelable(
                    key,
                    data
                )
            else it!!.getParcelable(key)
        }
    }
}