package com.brmsdi.gcsystem.ui.activity.mainLessee

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.brmsdi.gcsystem.data.constants.Constant
import com.brmsdi.gcsystem.data.security.SecurityPreferences

/**
 *
 * @author Wisley Bruno Marques França
 * @since 1
 */
class MainLesseeViewModel(application: Application) : AndroidViewModel(application) {
    private val securityPreferences = SecurityPreferences(application.applicationContext)

    fun logout() {
        securityPreferences.remove(Constant.AUTH.TOKEN)
        securityPreferences.remove(Constant.AUTH.FINGERPRINT)
        securityPreferences.remove(Constant.AUTH.TYPE_AUTH)
    }
}