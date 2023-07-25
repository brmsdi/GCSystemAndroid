package com.brmsdi.gcsystem.ui.utils

import android.os.Build
import android.os.Bundle
import com.brmsdi.gcsystem.data.constants.Constant
import com.brmsdi.gcsystem.data.model.OrderService

/**
 *
 * @author Wisley Bruno Marques França
 * @since 1
 */

interface OrderServiceData {

    fun loadIOrderServiceData(arguments: Bundle?) : OrderService? {
        arguments.let {
            return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                it!!.getParcelable(
                    Constant.OS.ORDER_SERVICE_DATA,
                    OrderService::class.java
                )
            } else {
                it!!.getParcelable(Constant.OS.ORDER_SERVICE_DATA)
            }
        }
    }
}