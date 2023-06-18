package com.brmsdi.gcsystem.ui.fragments

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.brmsdi.gcsystem.R
import com.brmsdi.gcsystem.databinding.FragmentSendEmailChangePasswordBinding
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

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSendEmailChangePasswordBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this)[SendEmailChangePasswordViewModel::class.java]
        loadData()
        addAction()
        val adapter = ArrayAdapter(this.requireContext(), android.R.layout.simple_spinner_dropdown_item, itemList)
        _binding.spinnerTypeAuth.adapter = adapter
        observe()
        return _binding.root
    }

    override fun onClick(view: View) {
        if (view.id == _binding.buttonSend.id) {
            send()
        }
    }

    private fun addAction() {
        _binding.buttonSend.setOnClickListener(this)
    }

    private fun loadData() {
        itemList.add(getString(R.string.employee))
        itemList.add(getString(R.string.lessee))
    }

    private fun send() {
        val email = _binding.editSendEmail.text.toString()
        val typeAuth = _binding.spinnerTypeAuth.selectedItem.toString()
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
                replaceFragment(R.id.fragment_container, SendCodeFragment.newInstance())
            }
        } // END responseRequest

        viewModel.errorMessage.observe(this.viewLifecycleOwner) {
            displayMessage(this.requireContext(), it)
        } // END errorMessage

    }
}