package com.brmsdi.gcsystem.ui.fragment.sendCode

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
import androidx.fragment.app.Fragment
import com.brmsdi.gcsystem.R
import com.brmsdi.gcsystem.data.constants.Constant.AUTH.CHANGE_PASSWORD_DATA
import com.brmsdi.gcsystem.databinding.FragmentSendCodeBinding
import com.brmsdi.gcsystem.ui.utils.AuthType.*
import com.brmsdi.gcsystem.data.dto.ChangePasswordDataDTO
import com.brmsdi.gcsystem.data.repository.AuthenticableRepository
import com.brmsdi.gcsystem.ui.fragment.newPassword.NewPasswordFragment
import com.brmsdi.gcsystem.ui.fragment.TypedFragment
import com.brmsdi.gcsystem.ui.utils.LoadChangePasswordData
import com.brmsdi.gcsystem.ui.utils.ProgressBarOnApp
import com.brmsdi.gcsystem.ui.utils.TextUtils.Companion.assembleCode
import com.brmsdi.gcsystem.ui.utils.TextUtils.Companion.displayMessage
import com.brmsdi.gcsystem.ui.utils.TextUtils.Companion.fieldsIsNotEmpty
import com.brmsdi.gcsystem.ui.utils.TextUtils.Companion.setMaxLength
import java.util.TreeMap

class SendCodeFragment : TypedFragment(), View.OnClickListener, LoadChangePasswordData,
    ProgressBarOnApp {
    companion object {
        fun newInstance() = SendCodeFragment()
    }

    private lateinit var _binding: FragmentSendCodeBinding
    private lateinit var viewModel: SendCodeViewModel
    private var changePasswordDataDTO: ChangePasswordDataDTO? = null
    private var code1 = ""
    private var code2 = ""
    private var code3 = ""
    private var code4 = ""
    private var code5 = ""
    private var code6 = ""

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSendCodeBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this)[SendCodeViewModel::class.java]
        changePasswordDataDTO = loadChangePasswordData(arguments)
        addTypes()
        actionsEditCode()
        addAction()
        observe()
        return _binding.root
    }

    private fun observe() {
        viewModel.validateModel.observe(viewLifecycleOwner) { validationModelWithToken ->
            if (!validationModelWithToken.status()) {
                displayMessage(this.requireContext(), validationModelWithToken.message())
            } else {
                validationModelWithToken.token()?.let {
                    changePasswordDataDTO?.token = it.token
                    val fragment = NewPasswordFragment.newInstance()
                    val bundle = Bundle()
                    bundle.putParcelable(CHANGE_PASSWORD_DATA, changePasswordDataDTO)
                    initializeNewPasswordFragment(fragment, bundle)
                }
            }
            showOrHideView(_binding.progressSendCode, false)
            showOrHideView(_binding.buttonSendCode, true)
        }
    }

    override fun onClick(view: View) {
        when (view.id) {
            _binding.buttonSendCode.id -> {
                getFields()
                if (fieldsIsCorrect(code1, code2, code3, code4, code5, code6)) sendCode()
            }
        }
    }

    private fun addAction() {
        _binding.buttonSendCode.setOnClickListener(this)
    }

    private fun addTypes() {
        val newTypes: TreeMap<String, String> = TreeMap()
        newTypes[getString(R.string.employee)] = EMPLOYEE.type
        newTypes[getString(R.string.lessee)] = LESSEE.type
        setTypes(newTypes)
    }

    private fun sendCode() {
        changePasswordDataDTO?.let {
            it.code = assembleCode(code1, code2, code3, code4, code5, code6).toString()
            val repository = getRepositoryTypeAuth(it.typeAuth)
            showOrHideView(_binding.buttonSendCode, false)
            showOrHideView(_binding.progressSendCode, true)
            sendCode(it, repository)
        }
    }

    private fun sendCode(
        changePasswordDataDTO: ChangePasswordDataDTO,
        repository: AuthenticableRepository
    ) {
        viewModel.sendCode(changePasswordDataDTO, repository)
    }

    private fun getFields() {
        code1 = _binding.editCode1.text.toString()
        code2 = _binding.editCode2.text.toString()
        code3 = _binding.editCode3.text.toString()
        code4 = _binding.editCode4.text.toString()
        code5 = _binding.editCode5.text.toString()
        code6 = _binding.editCode6.text.toString()
    }

    private fun fieldsIsCorrect(vararg codes: String): Boolean {
        for (code in codes) {
            if (!fieldsIsNotEmpty(code)) {
                displayMessage(this.requireContext(), getString(R.string.fields_empty))
                return false
            }
        }
        return true
    }

    private fun initializeNewPasswordFragment(fragment: Fragment, bundle: Bundle) {
        fragment.arguments = bundle
        replaceFragment(R.id.fragment_container, fragment)
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
        setMaxLength(_binding.editCode5, 1)
        setMaxLength(_binding.editCode6, 1)
        addWatcher(_binding.editCode1, _binding.editCode2)
        addWatcher(_binding.editCode2, _binding.editCode3)
        addWatcher(_binding.editCode3, _binding.editCode4)
        addWatcher(_binding.editCode4, _binding.editCode5)
        addWatcher(_binding.editCode5, _binding.editCode6)
        addWatcher(_binding.editCode6, null)
        editor(_binding.editCode1)
        editor(_binding.editCode2, _binding.editCode1)
        editor(_binding.editCode3, _binding.editCode2)
        editor(_binding.editCode4, _binding.editCode3)
        editor(_binding.editCode5, _binding.editCode4)
        editor(_binding.editCode6, _binding.editCode5)
    }
}