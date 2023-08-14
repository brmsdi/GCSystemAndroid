package com.brmsdi.gcsystem.data.helper

import android.app.KeyguardManager
import android.content.Context
import android.os.Build
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricManager.Authenticators.BIOMETRIC_STRONG
import androidx.biometric.BiometricManager.BIOMETRIC_SUCCESS
import androidx.biometric.BiometricPrompt
import androidx.biometric.BiometricPrompt.AuthenticationCallback
import androidx.biometric.BiometricPrompt.PromptInfo
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import com.brmsdi.gcsystem.R
import com.brmsdi.gcsystem.data.constants.Constant.AUTH.REQUEST_CODE_UNLOCK
import com.brmsdi.gcsystem.data.listeners.AuthenticationListener

/**
 *
 * @author Wisley Bruno Marques França
 * @since 1
 */
class BiometricHelper private constructor() {
    companion object {
        fun isBiometricAvailable(context: Context): Boolean {
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.P) {
                val keyguardManager = ContextCompat.getSystemService(context, KeyguardManager::class.java) as KeyguardManager
                return keyguardManager.isKeyguardSecure
            }
            return BiometricManager.from(context).canAuthenticate(BIOMETRIC_STRONG) == BIOMETRIC_SUCCESS
        }

        fun biometric(
            context: Context,
            activity: FragmentActivity,
            authenticationListener: AuthenticationListener
        ) {
            val executor = ContextCompat.getMainExecutor(context)
            val biometricPrompt =
                BiometricPrompt(activity, executor, object : AuthenticationCallback() {
                    override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                        super.onAuthenticationSucceeded(result)
                        authenticationListener.onSuccess()
                    }

                    override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                        super.onAuthenticationError(errorCode, errString)
                        authenticationListener.error()
                    }

                    override fun onAuthenticationFailed() {
                        super.onAuthenticationFailed()
                        authenticationListener.failed()
                    }
                })
            val promptInfo = PromptInfo.Builder()
                .setTitle(context.getString(R.string.title_settings_biometric))
                .setDescription(context.getString(R.string.info_biometric))
                .setNegativeButtonText(context.getString(R.string.cancel))
                .build()
            biometricPrompt.authenticate(promptInfo)
        }

        fun authPassword(context: Context, fragmentActivity: FragmentActivity) {
            val keyguardManager = ContextCompat.getSystemService(context, KeyguardManager::class.java) as KeyguardManager
            val intent = keyguardManager.createConfirmDeviceCredentialIntent(
                context.getString(R.string.unlock_device),
                context.getString(R.string.input_password)
            )

            if (intent != null) {
                fragmentActivity.startActivityForResult(intent, REQUEST_CODE_UNLOCK)
            }
        }
    }
}