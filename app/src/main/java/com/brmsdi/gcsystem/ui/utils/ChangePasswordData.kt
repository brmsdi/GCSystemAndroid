package com.brmsdi.gcsystem.ui.utils

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 *
 * @author Wisley Bruno Marques França
 * @since 1
 */

@Parcelize
class ChangePasswordData(var email: String = "", var code: String = "", var typeAuth: String, var token: String = "") : Parcelable {}