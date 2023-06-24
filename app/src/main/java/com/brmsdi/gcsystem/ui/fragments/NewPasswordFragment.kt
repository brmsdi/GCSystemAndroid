package com.brmsdi.gcsystem.ui.fragments

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import com.brmsdi.gcsystem.R
import com.brmsdi.gcsystem.databinding.FragmentNewPasswordBinding
import com.brmsdi.gcsystem.ui.utils.TextUtils.Companion.displayMessage
import com.brmsdi.gcsystem.ui.utils.TextUtils.Companion.fieldsIsNotEmpty
import com.brmsdi.gcsystem.ui.utils.TextUtils.Companion.haveMinimumSize
import com.brmsdi.gcsystem.ui.viewmodels.NewPasswordViewModel

class NewPasswordFragment : BaseFragment(), OnClickListener {

    companion object {
        fun newInstance() = NewPasswordFragment()
    }

    private lateinit var viewModel: NewPasswordViewModel
    private lateinit var _binding: FragmentNewPasswordBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel = ViewModelProvider(this)[NewPasswordViewModel::class.java]
        _binding = FragmentNewPasswordBinding.inflate(inflater, container, false)
        addAction()
        return _binding.root
    }

    override fun onClick(view: View) {
        if (view.id == _binding.buttonSend.id) {
            send(
                _binding.editNewPassword.text.toString(),
                _binding.editNewPasswordRepeat.text.toString()
            )
        }
    }

    private fun addAction() {
        _binding.buttonSend.setOnClickListener(this)
    }

    fun send(newPassword: String, newPasswordRepeat: String): String {
        if (!fieldsIsNotEmpty(newPassword, newPasswordRepeat)) {
            return getString(R.string.password_empty)
        }

        if (!haveMinimumSize(newPassword, 8u)) {
            return getString(R.string.minimum_size_password)
        }

        if (!passwordsIsEquals(newPassword, newPasswordRepeat)) {
//            displayMessage(this.requireContext(), getString(R.string.passwords_not_equals))
            _binding.editNewPasswordRepeat.requestFocus()
            return getString(R.string.passwords_not_equals)
        }

        return ""
    }

    private fun passwordsIsEquals(password: String, repeatPassword: String): Boolean {
        return password == repeatPassword
    }
}