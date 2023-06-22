package com.brmsdi.gcsystem.ui.fragments

import android.os.Build
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.annotation.RequiresApi
import com.brmsdi.gcsystem.R
import com.brmsdi.gcsystem.data.constants.Constant.AUTH.CHANGE_PASSWORD_DATA
import com.brmsdi.gcsystem.data.repositories.AuthenticableRepository
import com.brmsdi.gcsystem.databinding.FragmentSendCodeBinding
import com.brmsdi.gcsystem.ui.utils.AuthType.*
import com.brmsdi.gcsystem.ui.utils.ChangePasswordData
import com.brmsdi.gcsystem.ui.utils.TextUtils.Companion.displayMessage
import com.brmsdi.gcsystem.ui.utils.TextUtils.Companion.fieldsIsNotEmpty
import com.brmsdi.gcsystem.ui.utils.TextUtils.Companion.setMaxLength
import com.brmsdi.gcsystem.ui.viewmodels.SendCodeViewModel
import org.koin.android.ext.android.get
import org.koin.core.qualifier.named

class SendCodeFragment : BaseFragment(), View.OnClickListener {

    companion object {
        fun newInstance() = SendCodeFragment()
    }

    private lateinit var _binding: FragmentSendCodeBinding
    private lateinit var viewModel: SendCodeViewModel
    private var changePasswordData: ChangePasswordData? = null

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSendCodeBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this)[SendCodeViewModel::class.java]
        arguments.let {
            changePasswordData = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                it!!.getParcelable(CHANGE_PASSWORD_DATA, ChangePasswordData::class.java)
            } else {
                it!!.getParcelable(CHANGE_PASSWORD_DATA)
            }
        }
        actionsEditCode()
        addAction()
        observe()
        return _binding.root
    }

    override fun onClick(view: View) {
        if (view.id == _binding.buttonSend.id) {
            //replaceFragment(R.id.fragment_container, NewPasswordFragment.newInstance())
            sendCode()
        }
    }

    private fun addWatcher(currentEdit: EditText, nextEditText: EditText?) {
        currentEdit.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun afterTextChanged(editText: Editable?) {
                if (!editText.isNullOrEmpty()) {
                    nextEditText?.requestFocus()
                }
            }
        })
    }

    private fun editor(currentEdit: EditText, previousEdit: EditText? = null) {
        currentEdit.setOnKeyListener { view, keyCode, keyEvent ->
            if (keyCode == KeyEvent.KEYCODE_DEL && keyEvent.action == KeyEvent.ACTION_DOWN) {
                val edit = view as EditText
                if (edit.text.toString().isEmpty()) {
                    previousEdit?.requestFocus()
                }
            }
            false
        }
    }

    private fun actionsEditCode() {
        setMaxLength(_binding.editCode1, 1)
        setMaxLength(_binding.editCode2, 1)
        setMaxLength(_binding.editCode3, 1)
        setMaxLength(_binding.editCode4, 1)
        addWatcher(_binding.editCode1, _binding.editCode2)
        addWatcher(_binding.editCode2, _binding.editCode3)
        addWatcher(_binding.editCode3, _binding.editCode4)
        addWatcher(_binding.editCode4, null)
        editor(_binding.editCode1)
        editor(_binding.editCode2, _binding.editCode1)
        editor(_binding.editCode3, _binding.editCode2)
        editor(_binding.editCode4, _binding.editCode3)
    }

    private fun addAction() {
        _binding.buttonSend.setOnClickListener(this)
    }

    private fun sendCode() {
        if (!fieldsIsNotEmpty(
                _binding.editCode1.text.toString(),
                _binding.editCode2.text.toString(),
                _binding.editCode3.text.toString(),
                _binding.editCode4.text.toString()
            )
        ) {
            displayMessage(this.requireContext(), getString(R.string.fields_empty))
            return
        }

        changePasswordData?.let {
            it.code = assembleCode()
            val repository = getRepositoryTypeAuth(it)
            val w = "s"
            viewModel.sendCode(it, repository)
        }
    }

    private fun assembleCode(): String {
        return String.format(
            "%s%s%s%s",
            _binding.editCode1.text.toString(),
            _binding.editCode2.text.toString(),
            _binding.editCode3.text.toString(),
            _binding.editCode4.text.toString()
        )
    }

    private fun getRepositoryTypeAuth(changePasswordData: ChangePasswordData): AuthenticableRepository {
        return when (changePasswordData.typeAuth) {
            getString(R.string.employee) -> get(named(EMPLOYEE.type))
            getString(R.string.lessee) -> get(named(LESSEE.type))
            else -> throw IllegalArgumentException("Tipo de autenticação inválido: ${changePasswordData.typeAuth}")
        }
    }

    private fun observe() {
        viewModel.validateModel.observe(viewLifecycleOwner) {
            if (!it.status()) {
                displayMessage(this.requireContext(), it.message())
            }
        }
    }
}