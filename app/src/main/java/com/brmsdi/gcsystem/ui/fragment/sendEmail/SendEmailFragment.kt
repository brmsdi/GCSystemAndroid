package com.brmsdi.gcsystem.ui.fragment.sendEmail

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import com.brmsdi.gcsystem.R
import com.brmsdi.gcsystem.data.constants.Constant.AUTH.CHANGE_PASSWORD_DATA
import com.brmsdi.gcsystem.data.dto.ChangePasswordDataDTO
import com.brmsdi.gcsystem.data.repository.AuthenticableRepository
import com.brmsdi.gcsystem.databinding.FragmentSendEmailBinding
import com.brmsdi.gcsystem.ui.fragment.sendCode.SendCodeFragment
import com.brmsdi.gcsystem.ui.fragment.TypedFragment
import com.brmsdi.gcsystem.ui.utils.AuthType
import com.brmsdi.gcsystem.ui.utils.ProgressBarOnApp
import com.brmsdi.gcsystem.ui.utils.TextUtils.Companion.displayMessage
import com.brmsdi.gcsystem.ui.utils.TextUtils.Companion.fieldsIsNotEmpty
import java.util.TreeMap
import javax.net.ssl.HttpsURLConnection

class SendEmailFragment private constructor() : TypedFragment(), OnClickListener, ProgressBarOnApp {

    companion object {
        fun newInstance() = SendEmailFragment()
    }

    private lateinit var _binding: FragmentSendEmailBinding
    private lateinit var viewModel: SendEmailViewModel
    private val itemList = mutableListOf<String>()
    private var email = ""
    private var typeAuth = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSendEmailBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this)[SendEmailViewModel::class.java]
        addTypes()
        loadData()
        val adapter = ArrayAdapter(
            this.requireContext(),
            android.R.layout.simple_spinner_dropdown_item,
            itemList
        )
        _binding.spinnerTypeAuth.adapter = adapter
        observe()
        addAction()
        return _binding.root
    }

    private fun observe() {
        viewModel.responseRequestDTO.observe(this.viewLifecycleOwner) {
            if (it.status != HttpsURLConnection.HTTP_OK) {
                displayMessage(this.requireContext(), it.errors[0].message)
            } else {
                val fragment = SendCodeFragment.newInstance()
                val bundle = Bundle()
                val changePasswordDataDTO =
                    ChangePasswordDataDTO(email = this.email, typeAuth = this.typeAuth)
                bundle.putParcelable(CHANGE_PASSWORD_DATA, changePasswordDataDTO)
                initializeSendCodeFragment(fragment, bundle)
            }
            showOrHideView(_binding.progressSendEmail, false)
            showOrHideView(_binding.buttonSendEmail, true)
        } // END responseRequest

        viewModel.errorMessage.observe(this.viewLifecycleOwner) {
            displayMessage(this.requireContext(), it)
            showOrHideView(_binding.progressSendEmail, false)
            showOrHideView(_binding.buttonSendEmail, true)
        } // END errorMessage
    }

    override fun onClick(view: View) {
        when (view.id) {
            _binding.buttonSendEmail.id -> {
                getFields()
                if (fieldsIsCorrect(email, typeAuth)) send()
            }
        }
    }

    private fun addTypes() {
        val newTypes: TreeMap<String, String> = TreeMap()
        newTypes[getString(R.string.employee)] = AuthType.EMPLOYEE.type
        newTypes[getString(R.string.lessee)] = AuthType.LESSEE.type
        setTypes(newTypes)
    }

    private fun addAction() {
        _binding.buttonSendEmail.setOnClickListener(this)
    }

    private fun loadData() {
        getTypes().forEach {
            itemList.add(it.key)
        }
    }

    private fun send() {
        val repository = getRepositoryTypeAuth(typeAuth)
        showOrHideView(_binding.buttonSendEmail, false)
        showOrHideView(_binding.progressSendEmail, true)
        requestCode(email, repository)
    }

    private fun requestCode(email: String, repository: AuthenticableRepository) {
        viewModel.requestCode(email, repository)
    }

    private fun getFields() {
        email = _binding.editSendEmail.text.toString()
        typeAuth = _binding.spinnerTypeAuth.selectedItem.toString()
    }

    private fun fieldsIsCorrect(email: String, typeAuth: String): Boolean {
        if (!fieldsIsNotEmpty(email, typeAuth)) {
            displayMessage(this.requireContext(), getString(R.string.field_email_empty))
            return false
        }
        return true
    }

    private fun initializeSendCodeFragment(fragment: Fragment, bundle: Bundle) {
        fragment.arguments = bundle
        replaceFragment(R.id.fragment_container, fragment)
    }
}