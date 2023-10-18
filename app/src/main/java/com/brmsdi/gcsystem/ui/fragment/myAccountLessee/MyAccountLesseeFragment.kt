package com.brmsdi.gcsystem.ui.fragment.myAccountLessee

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.brmsdi.gcsystem.data.model.Lessee
import com.brmsdi.gcsystem.data.repository.LesseeRepository
import com.brmsdi.gcsystem.databinding.FragmentMyAccountLesseeBinding
import com.brmsdi.gcsystem.ui.utils.AuthType
import com.brmsdi.gcsystem.ui.utils.DateUtils
import com.brmsdi.gcsystem.ui.utils.ProgressBarOnApp
import com.brmsdi.gcsystem.ui.utils.TextUtils.Companion.displayMessage
import org.koin.android.ext.android.get
import org.koin.core.qualifier.named

class MyAccountLesseeFragment : Fragment(), ProgressBarOnApp {

    private lateinit var viewModel: MyAccountLesseeViewModel
    private lateinit var binding: FragmentMyAccountLesseeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMyAccountLesseeBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this)[MyAccountLesseeViewModel::class.java]
        getDataMyAccount()
        observe()
        return binding.root
    }

    private fun observe() {
        viewModel.account.observe(this.viewLifecycleOwner) {
            fillData(it)
            showOrHideView(binding.progress.root, false)
            showOrHideView(binding.layoutInputData, true)
        }

        viewModel.validation.observe(this.viewLifecycleOwner) {
            displayMessage(this.requireContext(), it.message())
            showOrHideView(binding.progress.root, false)
            showOrHideView(binding.layoutInputData, true)
        }
    }

    private fun getDataMyAccount() {
        val repository = get<LesseeRepository>(named(AuthType.LESSEE.type))
        showOrHideView(binding.layoutInputData, false)
        showOrHideView(binding.progress.root, true)
        viewModel.getDataAccount(repository)
    }

    private fun fillData(lesseeData: Lessee) {
        binding.editId.setText(lesseeData.id.toString())
        binding.editName.setText(lesseeData.name)
        binding.editCpf.setText(lesseeData.cpf)
        binding.editBirthDate.setText(DateUtils.dateFormattedToView(lesseeData.birthDate))
        binding.editEmail.setText(lesseeData.email)
        binding.editContact.setText(lesseeData.contactNumber)
    }
}