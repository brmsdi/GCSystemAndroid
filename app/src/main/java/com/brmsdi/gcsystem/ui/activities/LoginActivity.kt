package com.brmsdi.gcsystem.ui.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.View.OnClickListener
import android.widget.ArrayAdapter
import androidx.lifecycle.ViewModelProvider
import com.brmsdi.gcsystem.R
import com.brmsdi.gcsystem.data.repositories.AuthenticableRepository
import com.brmsdi.gcsystem.databinding.ActivityLoginBinding
import com.brmsdi.gcsystem.ui.utils.AuthType.*
import com.brmsdi.gcsystem.ui.utils.TextUtils.Companion.cpfIsValid
import com.brmsdi.gcsystem.ui.utils.TextUtils.Companion.displayMessage
import com.brmsdi.gcsystem.ui.utils.TextUtils.Companion.fieldsIsNotEmpty
import com.brmsdi.gcsystem.ui.utils.TextUtils.Companion.setMaxLength
import com.brmsdi.gcsystem.ui.viewmodels.LoginViewModel
import org.koin.android.ext.android.get
import org.koin.core.qualifier.named

/**
 *
 * @author Wisley Bruno Marques França
 * @since 1
 */

class LoginActivity : TypedActivity(), OnClickListener {
    private lateinit var binding : ActivityLoginBinding
    private val itemList = mutableListOf<String>()
    private lateinit var loginViewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTitle(R.string.authentication)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        loginViewModel = ViewModelProvider(this)[LoginViewModel::class.java]
        //supportActionBar?.hide()
        loadData()
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, itemList)
        binding.spinnerTypeAuth.adapter = adapter
        addAction()
        setMaxLength(binding.editUsername, 11)
        observe()
        //
       // initChangePassword()
        setContentView(binding.root)
    }

    override fun onClick(view: View) {
        if (view.id == binding.buttonSend.id) {
            loginHandle()
        } else if (view.id == binding.textChangePassword.id)
        {
            initChangePassword()
        }
    }

    private fun loginHandle() {
        val cpf = binding.editUsername.text.toString()
        val password = binding.editPassword.text.toString()
        val typeAuth = binding.spinnerTypeAuth.selectedItem.toString()
        if (!fieldsIsNotEmpty(
            cpf,
            password,
            typeAuth
        )) {
            displayMessage(applicationContext, getString(R.string.fields_empty))
            return
        }

        if (!cpfIsValid(cpf)) {
            displayMessage(applicationContext, getString(R.string.cpf_invalid))
            return
        }
        val repository = getRepositoryTypeAuth(typeAuth)
        loginViewModel.authenticate(cpf, password, repository)
    }

    private fun addAction() {
        binding.buttonSend.setOnClickListener(this)
        binding.textChangePassword.setOnClickListener(this)
    }

    private fun observe() {
        loginViewModel.login.observe(this) {
            if (it.status()) {
                initMain()
            } else {
                displayMessage(this, it.message())
            }
        }
    }

    private fun loadData() {
        itemList.add(getString(R.string.employee))
        itemList.add(getString(R.string.lessee))
    }

    private fun initMain() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

    private fun initChangePassword() {
        startActivity(Intent(this, ChangePasswordActivity::class.java))
    }
}