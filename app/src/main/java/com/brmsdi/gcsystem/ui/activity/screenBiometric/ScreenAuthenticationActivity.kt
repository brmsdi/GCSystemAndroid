package com.brmsdi.gcsystem.ui.activity.screenBiometric

import android.content.Context
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.View.OnClickListener
import com.brmsdi.gcsystem.data.constants.Constant.AUTH.FINGERPRINT
import com.brmsdi.gcsystem.data.constants.Constant.AUTH.FINGERPRINT_ON
import com.brmsdi.gcsystem.data.helper.BiometricHelper.Companion.biometric
import com.brmsdi.gcsystem.data.helper.BiometricHelper.Companion.isBiometricAvailable
import com.brmsdi.gcsystem.data.listeners.AuthenticationListener
import com.brmsdi.gcsystem.data.security.SecurityPreferences
import com.brmsdi.gcsystem.databinding.ActivityScreenBiometricBinding

class ScreenAuthenticationActivity : AppCompatActivity(), OnClickListener{
    private lateinit var binding: ActivityScreenBiometricBinding
    private var statusBiometric: Boolean = false
    private lateinit var securityPreferences: SecurityPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityScreenBiometricBinding.inflate(layoutInflater)
        securityPreferences = SecurityPreferences(this)
        statusBiometric = biometricAvailable(this)
        setContentView(binding.root)
    }

    override fun onClick(view: View) {
        when (view.id) {
            binding.switchBiometric.id -> toggle()
        }
    }

    private fun biometricAvailable(context: Context): Boolean {
        isBiometricAvailable(context).let {
            if (it) {
                verifyStateSwitch()
                addAction()
            } else disableButton()
            return it
        }
    }

    private fun disableButton() {
        binding.switchBiometric.isEnabled = false
        binding.textErrorBiometric.visibility = View.VISIBLE
    }

    private fun addAction() {
        binding.switchBiometric.setOnClickListener(this)
    }

    private fun toggle() {
        val authenticationListener = object : AuthenticationListener {
            override fun onSuccess() {
                if (binding.switchBiometric.isChecked) {
                    securityPreferences.story(FINGERPRINT, FINGERPRINT_ON)
                    return
                }
                securityPreferences.remove(FINGERPRINT)
            }

            override fun error() {
                binding.switchBiometric.isChecked = !binding.switchBiometric.isChecked
            }

            override fun failed() {
                binding.switchBiometric.isChecked = !binding.switchBiometric.isChecked
            }
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            biometric(this, this, authenticationListener)
        }
    }

    private fun verifyStateSwitch() {
        val fingerprint = securityPreferences.get(FINGERPRINT)
        binding.switchBiometric.isChecked = fingerprint.isNotEmpty() && fingerprint == FINGERPRINT_ON
    }
}