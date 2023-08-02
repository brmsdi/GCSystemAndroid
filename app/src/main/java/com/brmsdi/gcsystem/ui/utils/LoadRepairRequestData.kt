package com.brmsdi.gcsystem.ui.utils

import android.os.Build
import android.os.Bundle
import com.brmsdi.gcsystem.data.constants.Constant
import com.brmsdi.gcsystem.data.model.RepairRequest

/**
 *
 * @author Wisley Bruno Marques França
 * @since 1
 */

interface LoadRepairRequestData {

    fun loadRepairRequestData(arguments: Bundle?) : RepairRequest? {
        arguments.let {
            return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                it!!.getParcelable(
                    Constant.REPAIR.REPAIR_REQUEST_DATA,
                    RepairRequest::class.java
                )
            } else {
                it!!.getParcelable(Constant.REPAIR.REPAIR_REQUEST_DATA)
            }
        }
    }
}