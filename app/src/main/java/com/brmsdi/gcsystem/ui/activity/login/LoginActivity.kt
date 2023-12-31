package com.brmsdi.gcsystem.ui.activity.login

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.View.OnClickListener
import android.widget.ArrayAdapter
import androidx.annotation.RequiresApi
import androidx.appcompat.widget.Toolbar
import androidx.core.app.ActivityCompat
import androidx.lifecycle.ViewModelProvider
import com.brmsdi.gcsystem.R
import com.brmsdi.gcsystem.data.constants.Constant
import com.brmsdi.gcsystem.data.firebase.fcm.TokenService
import com.brmsdi.gcsystem.data.helper.BiometricHelper
import com.brmsdi.gcsystem.data.helper.BiometricHelper.Companion.isBiometricAvailable
import com.brmsdi.gcsystem.data.listeners.AuthenticationListener
import com.brmsdi.gcsystem.data.repository.AuthenticableRepository
import com.brmsdi.gcsystem.data.security.SecurityPreferences
import com.brmsdi.gcsystem.databinding.ActivityLoginBinding
import com.brmsdi.gcsystem.ui.activity.changePassword.ChangePasswordActivity
import com.brmsdi.gcsystem.ui.activity.mainEmployee.MainEmployeeActivity
import com.brmsdi.gcsystem.ui.activity.TypedActivity
import com.brmsdi.gcsystem.ui.activity.mainLessee.MainLesseeActivity
import com.brmsdi.gcsystem.ui.utils.AuthType.*
import com.brmsdi.gcsystem.ui.utils.ProgressBarOnApp
import com.brmsdi.gcsystem.ui.utils.TextUtils.Companion.cpfIsValid
import com.brmsdi.gcsystem.ui.utils.TextUtils.Companion.displayMessage
import com.brmsdi.gcsystem.ui.utils.TextUtils.Companion.fieldsIsNotEmpty
import com.brmsdi.gcsystem.ui.utils.TextUtils.Companion.permissionsNotGranted
import com.brmsdi.gcsystem.ui.utils.TextUtils.Companion.setMaxLength
import java.util.TreeMap

/**
 *
 * @author Wisley Bruno Marques França
 * @since 1
 */

class LoginActivity : TypedActivity(), OnClickListener, ProgressBarOnApp {
    private lateinit var binding: ActivityLoginBinding
    private val itemList = mutableListOf<String>()
    private lateinit var loginViewModel: LoginViewModel
    private lateinit var securityPreferences: SecurityPreferences
    private var cpf = ""
    private var password = ""
    private var typeAuth = ""

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        toolbar.setTitle(R.string.authentication)
        setSupportActionBar(toolbar)
        loginViewModel = ViewModelProvider(this)[LoginViewModel::class.java]
        securityPreferences = SecurityPreferences(this)
        setMaxLength(binding.editUsername, 11)
        addTypes()
        loadData()
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, itemList)
        binding.spinnerTypeAuth.adapter = adapter
        observe()
        addAction()
        // TESTE
        binding.editUsername.setText("12345678909")
        binding.editPassword.setText("12345678909")
        getFields()
        typeAuth = getString(R.string.lessee)
        checkPermissions()
        authHandler()
        TokenService.getToken() // Tratar exceção
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    private fun observe() {
        loginViewModel.login.observe(this) {
            if (it.status()) {
                initializeMain(typeAuth)
            } else {
                displayMessage(this, it.message())
            }
            showOrHideView(binding.buttonSend, true)
            postExecution(binding.progressLogin)
        }
    }

    override fun onClick(view: View) {
        when (view.id) {
            binding.buttonSend.id -> {
                getFields()
                if (fieldsIsCorrect(cpf, password, typeAuth)) loginHandle(typeAuth)
            }
            binding.textChangePassword.id -> initializeChangePassword()
        }
    }

    private fun fieldsIsCorrect(cpf: String, password: String, typeAuth: String): Boolean {
        if (!fieldsIsNotEmpty(
                cpf,
                password,
                typeAuth
            )
        ) {
            displayMessage(applicationContext, getString(R.string.fields_empty))
            return false
        }

        if (!cpfIsValid(cpf)) {
            displayMessage(applicationContext, getString(R.string.cpf_invalid))
            return false
        }

        return true
    }

    private fun getFields() {
        cpf = binding.editUsername.text.toString()
        password = binding.editPassword.text.toString()
        typeAuth = binding.spinnerTypeAuth.selectedItem.toString()
    }

//    private fun loginHandle() {
//        val repository = getRepositoryTypeAuth(typeAuth)
//        showOrHideView(binding.buttonSend, false)
//        onProgress(binding.progressLogin)
//        authenticate(cpf, password, repository)
//    }

    private fun loginHandle(typeAuth: String) {
        securityPreferences.story(Constant.AUTH.TOKEN, "12345678sdfkjkdjflkjsdkljfksjdf")
        securityPreferences.story(Constant.AUTH.TYPE_AUTH, typeAuth)
        initializeMain(typeAuth)
    }

    private fun addAction() {
        binding.buttonSend.setOnClickListener(this)
        binding.textChangePassword.setOnClickListener(this)
    }

    private fun addTypes() {
        val newTypes: TreeMap<String, String> = TreeMap()
        newTypes[getString(R.string.employee)] = EMPLOYEE.type
        newTypes[getString(R.string.lessee)] = LESSEE.type
        setTypes(newTypes)
    }

    private fun loadData() {
        getTypes().forEach {
            itemList.add(it.key)
        }
    }

    private fun initializeChangePassword() {
        startActivity(Intent(this, ChangePasswordActivity::class.java))
    }

    private fun authenticate(cpf: String, password: String, repository: AuthenticableRepository) {
        loginViewModel.authenticate(cpf, password, repository)
    }

    private fun initializeMain(type: String) {
        if (getString(R.string.employee) == type) {
            startActivity(Intent(this, MainEmployeeActivity::class.java))
        } else if (getString(R.string.lessee) == type) {
            startActivity(Intent(this, MainLesseeActivity::class.java))
        }
        finish()
    }

    private fun authHandler() {
        val token = securityPreferences.get(Constant.AUTH.TOKEN)
        val finger = securityPreferences.get(Constant.AUTH.FINGERPRINT)
        if (token.isEmpty()) {
            return
        }

        if (finger == Constant.AUTH.FINGERPRINT_ON) {
            if (!isBiometricAvailable(this)) {
                securityPreferences.remove(Constant.AUTH.TOKEN)
                securityPreferences.remove(Constant.AUTH.FINGERPRINT)
                securityPreferences.remove(Constant.AUTH.TYPE_AUTH)
            } else {
                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.P) {
                    BiometricHelper.authPassword(this, this)
                    return
                }
                val authenticationListener = object : AuthenticationListener {
                    override fun onSuccess() {
                        loginHandle(typeAuth = securityPreferences.get(Constant.AUTH.TYPE_AUTH))
                    }

                    override fun error() {}

                    override fun failed() {}
                }
                BiometricHelper.biometric(this, this, authenticationListener)
            }
        } else {
            loginHandle(typeAuth = securityPreferences.get(Constant.AUTH.TYPE_AUTH))
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == Constant.AUTH.REQUEST_CODE_UNLOCK) {
            if (resultCode == Activity.RESULT_OK) {
                loginHandle(typeAuth = securityPreferences.get(Constant.AUTH.TYPE_AUTH))
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    private fun checkPermissions() {
        val permissionsNotGranted = permissionsNotGranted(
            this, listOf(
                Manifest.permission.INTERNET,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.USE_BIOMETRIC,
                Manifest.permission.USE_FINGERPRINT,
                Manifest.permission.POST_NOTIFICATIONS,
                Manifest.permission.VIBRATE
            )
        )

        if (permissionsNotGranted.isNotEmpty()) {
            ActivityCompat.requestPermissions(
                this,
                permissionsNotGranted,
                Constant.PERMISSION.ALL
            )
        }
    }
}