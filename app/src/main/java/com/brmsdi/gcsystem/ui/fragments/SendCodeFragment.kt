package com.brmsdi.gcsystem.ui.fragments

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.KeyEvent
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnKeyListener
import android.view.ViewGroup
import android.widget.EditText
import com.brmsdi.gcsystem.R
import com.brmsdi.gcsystem.databinding.FragmentSendCodeBinding
import com.brmsdi.gcsystem.ui.utils.TextUtils.Companion.setMaxLength
import com.brmsdi.gcsystem.ui.viewmodels.SendCodeViewModel

class SendCodeFragment : BaseFragment(), View.OnClickListener {

    companion object {
        fun newInstance() = SendCodeFragment()
    }

    private lateinit var _binding : FragmentSendCodeBinding
    private lateinit var viewModel: SendCodeViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSendCodeBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this)[SendCodeViewModel::class.java]
        actionsEditCode()
        addAction()
        return _binding.root
    }

    override fun onClick(view: View) {
        if (view.id == _binding.buttonSend.id) {
            replaceFragment(R.id.fragment_container, NewPasswordFragment.newInstance())
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
}