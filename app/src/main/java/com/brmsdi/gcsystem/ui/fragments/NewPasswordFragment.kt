package com.brmsdi.gcsystem.ui.fragments

import android.os.Build
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import com.brmsdi.gcsystem.R
import com.brmsdi.gcsystem.data.constants.Constant
import com.brmsdi.gcsystem.data.dto.TokenChangePasswordDTO
import com.brmsdi.gcsystem.data.repositories.AuthenticableRepository
import com.brmsdi.gcsystem.databinding.FragmentNewPasswordBinding
import com.brmsdi.gcsystem.ui.utils.AuthType
import com.brmsdi.gcsystem.ui.utils.ChangePasswordData
import com.brmsdi.gcsystem.ui.utils.TextUtils.Companion.displayMessage
import com.brmsdi.gcsystem.ui.utils.TextUtils.Companion.fieldsIsNotEmpty
import com.brmsdi.gcsystem.ui.utils.TextUtils.Companion.haveMinimumSize
import com.brmsdi.gcsystem.ui.viewmodels.NewPasswordViewModel
import javax.net.ssl.HttpsURLConnection

class NewPasswordFragment : TypedFragment(), OnClickListener {
    companion object {
        fun newInstance() = NewPasswordFragment()
    }

    private lateinit var viewModel: NewPasswordViewModel
    private lateinit var _binding: FragmentNewPasswordBinding
    private var newPassword = ""
    private var passwordRepeat = ""
    private var changePasswordData: ChangePasswordData? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel = ViewModelProvider(this)[NewPasswordViewModel::class.java]
        _binding = FragmentNewPasswordBinding.inflate(inflater, container, false)
        loadChangePasswordData()
        addAction()
        observe()
        return _binding.root
    }

    private fun observe() {
        viewModel.responseRequest.observe(this.viewLifecycleOwner) {
            if (it.status != HttpsURLConnection.HTTP_OK) {
                displayMessage(this.requireContext(), it.errors[0].message)
            } else {
                displayMessage(this.requireContext(), getString(R.string.password_update_success))
                endActivity()
            }
        }

        viewModel.errorMessage.observe(this.viewLifecycleOwner) {
            displayMessage(this.requireContext(), it)
        } // END errorMessage
    }

    override fun onClick(view: View) {
        if (view.id == _binding.buttonSendNewPassword.id) {
            newPassword = _binding.editNewPassword.text.toString()
            passwordRepeat = _binding.editNewPasswordRepeat.text.toString()
            val result = filterPassword(
                newPassword,
                passwordRepeat
            )

            if (result.isNotEmpty()) {
                displayMessage(this.requireContext(), result)
                return
            }

            save()
        }
    }

    override fun onResume() {
        super.onResume()
        val newTypes : HashMap<String, String> = hashMapOf()
        newTypes[getString(R.string.employee)] = AuthType.EMPLOYEE.type
        newTypes[getString(R.string.lessee)] = AuthType.LESSEE.type
        setTypes(newTypes)
    }
    private fun prepareToken(changePasswordData: ChangePasswordData): TokenChangePasswordDTO {
        return TokenChangePasswordDTO(
            changePasswordData.typeAuth,
            changePasswordData.token,
            newPassword
        )
    }

    private fun addAction() {
        _binding.buttonSendNewPassword.setOnClickListener(this)
    }

    private fun filterPassword(newPassword: String, newPasswordRepeat: String): String {
        if (!fieldsIsNotEmpty(newPassword, newPasswordRepeat)) {
            return getString(R.string.password_empty)
        }

        if (!passwordsIsEquals(newPassword, newPasswordRepeat)) {
            _binding.editNewPasswordRepeat.requestFocus()
            return getString(R.string.passwords_not_equals)
        }

        if (!haveMinimumSize(newPassword, 8u)) {
            return getString(R.string.minimum_size_password)
        }

        return ""

    }

    private fun passwordsIsEquals(password: String, repeatPassword: String): Boolean {
        return password == repeatPassword
    }

    @Deprecated("Melhorar")
    private fun save() {
        val tokenChangePasswordDTO = changePasswordData?.let { prepareToken(it) }
        val repository = changePasswordData?.let { getRepositoryTypeAuth(it.typeAuth) }
        save(tokenChangePasswordDTO!!, repository!!)
    }

    private fun save(tokenChangePasswordDTO: TokenChangePasswordDTO, repository: AuthenticableRepository) {
        viewModel.save(tokenChangePasswordDTO, repository)
    }

    private fun loadChangePasswordData() {
        arguments.let {
            changePasswordData = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                it!!.getParcelable(
                    Constant.AUTH.CHANGE_PASSWORD_DATA,
                    ChangePasswordData::class.java
                )
            } else {
                it!!.getParcelable(Constant.AUTH.CHANGE_PASSWORD_DATA)
            }
        }
    }
}