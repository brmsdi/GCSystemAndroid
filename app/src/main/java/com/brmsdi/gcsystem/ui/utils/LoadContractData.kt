package com.brmsdi.gcsystem.ui.utils

import android.os.Build
import android.os.Bundle
import com.brmsdi.gcsystem.data.constants.Constant
import com.brmsdi.gcsystem.data.model.Contract

/**
 *
 * @author Wisley Bruno Marques França
 * @since 1
 */

interface LoadContractData {

    fun loadContractData(arguments: Bundle?) : Contract? {
        arguments.let {
            return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                it!!.getParcelable(
                    Constant.CONTRACT.CONTRACT_DATA,
                    Contract::class.java
                )
            } else {
                it!!.getParcelable(Constant.CONTRACT.CONTRACT_DATA)
            }
        }
    }
}