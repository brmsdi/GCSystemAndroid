package com.brmsdi.gcsystem.ui.fragments

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
import com.brmsdi.gcsystem.databinding.FragmentSendEmailChangePasswordBinding
import com.brmsdi.gcsystem.ui.utils.ChangePasswordData
import com.brmsdi.gcsystem.ui.utils.TextUtils.Companion.displayMessage
import com.brmsdi.gcsystem.ui.utils.TextUtils.Companion.fieldsIsNotEmpty
import com.brmsdi.gcsystem.ui.viewmodels.SendEmailChangePasswordViewModel
import javax.net.ssl.HttpsURLConnection

class SendEmailChangePasswordFragment private constructor() : BaseFragment(), OnClickListener {

    companion object {
        fun newInstance() = SendEmailChangePasswordFragment()
    }

    private lateinit var _binding: FragmentSendEmailChangePasswordBinding
    private lateinit var viewModel: SendEmailChangePasswordViewModel
    private val itemList = mutableListOf<String>()
    private var email = ""
    private var typeAuth = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSendEmailChangePasswordBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this)[SendEmailChangePasswordViewModel::class.java]
        loadData()
        addAction()
        val adapter = ArrayAdapter(
            this.requireContext(),
            android.R.layout.simple_spinner_dropdown_item,
            itemList
        )
        _binding.spinnerTypeAuth.adapter = adapter
        observe()
        return _binding.root
    }

    override fun onClick(view: View) {
        when (view.id) {
            _binding.buttonSendEmail.id -> send()
        }
    }

    private fun addAction() {
        _binding.buttonSendEmail.setOnClickListener(this)
    }

    private fun loadData() {
        itemList.add(getString(R.string.employee))
        itemList.add(getString(R.string.lessee))
    }

    private fun send() {
        email = _binding.editSendEmail.text.toString()
        typeAuth = _binding.spinnerTypeAuth.selectedItem.toString()
        if (!fieldsIsNotEmpty(email, typeAuth)) {
            displayMessage(this.requireContext(), getString(R.string.field_email_empty))
            return
        }

        viewModel.requestCode(email, typeAuth)
    }

    private fun observe() {
        viewModel.responseRequest.observe(this.viewLifecycleOwner) {
            if (it.status != HttpsURLConnection.HTTP_OK) {
                displayMessage(this.requireContext(), it.errors[0].message)
            } else {
                val fragment = SendCodeFragment.newInstance()
                val bundle = Bundle()
                val changePasswordData =
                    ChangePasswordData(email = this.email, typeAuth = this.typeAuth)
                bundle.putParcelable(CHANGE_PASSWORD_DATA, changePasswordData)
                initSendCodeFragment(fragment, bundle)
            }
        } // END responseRequest

        viewModel.errorMessage.observe(this.viewLifecycleOwner) {
            displayMessage(this.requireContext(), it)
        } // END errorMessage
    }

    private fun initSendCodeFragment(fragment: Fragment, bundle: Bundle) {
        fragment.arguments = bundle
        replaceFragment(R.id.fragment_container, fragment)
    }
}