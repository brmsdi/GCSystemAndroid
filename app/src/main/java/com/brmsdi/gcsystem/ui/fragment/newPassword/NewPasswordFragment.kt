package com.brmsdi.gcsystem.ui.fragment.newPassword

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import com.brmsdi.gcsystem.R
import com.brmsdi.gcsystem.data.dto.TokenChangePasswordDTO
import com.brmsdi.gcsystem.data.repository.AuthenticableRepository
import com.brmsdi.gcsystem.databinding.FragmentNewPasswordBinding
import com.brmsdi.gcsystem.ui.utils.AuthType
import com.brmsdi.gcsystem.data.dto.ChangePasswordDataDTO
import com.brmsdi.gcsystem.ui.fragment.TypedFragment
import com.brmsdi.gcsystem.ui.utils.LoadChangePasswordData
import com.brmsdi.gcsystem.ui.utils.ProgressBarOnApp
import com.brmsdi.gcsystem.ui.utils.TextUtils.Companion.displayMessage
import com.brmsdi.gcsystem.ui.utils.TextUtils.Companion.fieldsIsNotEmpty
import com.brmsdi.gcsystem.ui.utils.TextUtils.Companion.haveMinimumSize
import java.util.TreeMap
import javax.net.ssl.HttpsURLConnection

class NewPasswordFragment : TypedFragment(), OnClickListener, LoadChangePasswordData, ProgressBarOnApp {
    companion object {
        fun newInstance() = NewPasswordFragment()
    }
    private lateinit var viewModel: NewPasswordViewModel
    private lateinit var _binding: FragmentNewPasswordBinding
    private var newPassword = ""
    private var passwordRepeat = ""
    private var changePasswordDataDTO: ChangePasswordDataDTO? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel = ViewModelProvider(this)[NewPasswordViewModel::class.java]
        _binding = FragmentNewPasswordBinding.inflate(inflater, container, false)
        changePasswordDataDTO = loadChangePasswordData(arguments)
        addTypes()
        addAction()
        observe()
        return _binding.root
    }

    private fun observe() {
        viewModel.responseRequestDTO.observe(this.viewLifecycleOwner) {
            if (it.status != HttpsURLConnection.HTTP_OK) {
                displayMessage(this.requireContext(), it.errors[0].message)
            } else {
                displayMessage(this.requireContext(), getString(R.string.password_update_success))
                endActivity()
            }
            showOrHideView(_binding.progressNewPassword, false)
            showOrHideView(_binding.buttonSendNewPassword, true)
        }

        viewModel.errorMessage.observe(this.viewLifecycleOwner) {
            displayMessage(this.requireContext(), it)
            showOrHideView(_binding.buttonSendNewPassword, true)
            showOrHideView(_binding.progressNewPassword, false)
        } // END errorMessage
    }

    override fun onClick(view: View) {
        when (view.id) {
            _binding.buttonSendNewPassword.id -> {
                getFields()
                if (isCorrectPasswordFields(newPassword, passwordRepeat)) save()
            }
        }
    }

    private fun prepareToken(changePasswordDataDTO: ChangePasswordDataDTO): TokenChangePasswordDTO {
        return TokenChangePasswordDTO(
            changePasswordDataDTO.typeAuth,
            changePasswordDataDTO.token,
            newPassword
        )
    }

    private fun addAction() {
        _binding.buttonSendNewPassword.setOnClickListener(this)
    }

    private fun addTypes() {
        val newTypes: TreeMap<String, String> = TreeMap()
        newTypes[getString(R.string.employee)] = AuthType.EMPLOYEE.type
        newTypes[getString(R.string.lessee)] = AuthType.LESSEE.type
        setTypes(newTypes)
    }
    private fun getFields() {
        newPassword = _binding.editNewPassword.text.toString()
        passwordRepeat = _binding.editNewPasswordRepeat.text.toString()
    }

    private fun isCorrectPasswordFields(newPassword: String, newPasswordRepeat: String): Boolean {
        if (!fieldsIsNotEmpty(newPassword, newPasswordRepeat)) {
            displayMessage(this.requireContext(), getString(R.string.password_empty))
            return false
        }

        if (!passwordsIsEquals(newPassword, newPasswordRepeat)) {
            _binding.editNewPasswordRepeat.requestFocus()
            displayMessage(this.requireContext(), getString(R.string.passwords_not_equals))
            return false
        }

        if (!haveMinimumSize(newPassword, 8u)) {
            displayMessage(this.requireContext(), getString(R.string.minimum_size_password))
            return false
        }

        return true
    }

    private fun passwordsIsEquals(password: String, repeatPassword: String): Boolean {
        return password == repeatPassword
    }

    private fun save() {
        changePasswordDataDTO?.let {
            val tokenChangePasswordDTO = prepareToken(it)
            val repository = getRepositoryTypeAuth(it.typeAuth)
            showOrHideView(_binding.buttonSendNewPassword, false)
            showOrHideView(_binding.progressNewPassword, true)
            save(tokenChangePasswordDTO, repository)
        }
    }

    private fun save(tokenChangePasswordDTO: TokenChangePasswordDTO, repository: AuthenticableRepository) {
        viewModel.save(tokenChangePasswordDTO, repository)
    }
}