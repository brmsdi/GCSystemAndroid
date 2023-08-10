package com.brmsdi.gcsystem.data.dto

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 *
 * @author Wisley Bruno Marques França
 * @since 1
 */

@Parcelize
class ChangePasswordDataDTO(var email: String = "", var code: String = "", var typeAuth: String, var token: String = "") : Parcelable