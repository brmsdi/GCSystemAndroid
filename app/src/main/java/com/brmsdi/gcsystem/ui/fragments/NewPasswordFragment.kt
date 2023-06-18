package com.brmsdi.gcsystem.ui.fragments

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import com.brmsdi.gcsystem.databinding.FragmentNewPasswordBinding
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

        return _binding.root
    }

    override fun onClick(view: View) {
        if (view.id == _binding.buttonSend.id) {

        }
    }

    private fun addAction() {
        _binding.buttonSend.setOnClickListener(this)
    }

}