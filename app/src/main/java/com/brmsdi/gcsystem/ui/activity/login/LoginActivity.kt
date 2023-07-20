package com.brmsdi.gcsystem.ui.activity.login

import android.content.Intent
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import android.view.View.OnClickListener
import android.widget.ArrayAdapter
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.brmsdi.gcsystem.R
import com.brmsdi.gcsystem.data.repository.AuthenticableRepository
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
    private var cpf = ""
    private var password = ""
    private var typeAuth = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        loginViewModel = ViewModelProvider(this)[LoginViewModel::class.java]
        setMaxLength(binding.editUsername, 11)
        addTypes()
        loadData()
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, itemList)
        binding.spinnerTypeAuth.adapter = adapter
        observe()
        addAction()
        binding.editUsername.setText("12345678909")
        binding.editPassword.setText("12345678909")
        getFields()
        loginHandle()
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
                if (fieldsIsCorrect(cpf, password, typeAuth)) loginHandle()
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

    private fun loginHandle() {
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
}