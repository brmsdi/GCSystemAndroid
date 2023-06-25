package com.brmsdi.gcsystem.ui.utils

import android.os.Build
import android.os.Bundle
import com.brmsdi.gcsystem.data.constants.Constant
import com.brmsdi.gcsystem.data.dto.ChangePasswordDataDTO

/**
 *
 * @author Wisley Bruno Marques França
 * @since 1
 */

interface LoadChangePasswordData {
    fun loadChangePasswordData(arguments: Bundle?) : ChangePasswordDataDTO? {
        arguments.let {
            return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                it!!.getParcelable(
                    Constant.AUTH.CHANGE_PASSWORD_DATA,
                    ChangePasswordDataDTO::class.java
                )
            } else {
                it!!.getParcelable(Constant.AUTH.CHANGE_PASSWORD_DATA)
            }
        }
    }
}