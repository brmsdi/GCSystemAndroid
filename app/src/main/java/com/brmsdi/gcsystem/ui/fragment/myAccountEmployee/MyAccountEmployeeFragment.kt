package com.brmsdi.gcsystem.ui.fragment.myAccountEmployee

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.brmsdi.gcsystem.data.model.Employee
import com.brmsdi.gcsystem.data.repository.EmployeeRepository
import com.brmsdi.gcsystem.databinding.FragmentMyAccountEmployeeBinding
import com.brmsdi.gcsystem.ui.utils.AuthType
import com.brmsdi.gcsystem.ui.utils.DateUtils.Companion.dateFormattedToView
import com.brmsdi.gcsystem.ui.utils.TextUtils.Companion.displayMessage
import org.koin.android.ext.android.get
import org.koin.core.qualifier.named

class MyAccountEmployeeFragment : Fragment() {
    private lateinit var binding: FragmentMyAccountEmployeeBinding
    private lateinit var viewModel: MyAccountEmployeeViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMyAccountEmployeeBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this)[MyAccountEmployeeViewModel::class.java]
        getDataMyAccount()
        observe()
        return binding.root
    }

    private fun observe() {
        viewModel.account.observe(this.viewLifecycleOwner) {
            fillData(it)
        }

        viewModel.validation.observe(this.viewLifecycleOwner) {
            displayMessage(this.requireContext(), it.message())
        }
    }

    private fun getDataMyAccount() {
        val repository = get<EmployeeRepository>(named(AuthType.EMPLOYEE.type))
        viewModel.getDataAccount(repository)
    }
    private fun fillData(employeeData: Employee) {
        binding.editId.setText(employeeData.id.toString())
        binding.editName.setText(employeeData.name)
        binding.editCpf.setText(employeeData.cpf)
        binding.editBirthDate.setText(dateFormattedToView(employeeData.birthDate))
        binding.editEmail.setText(employeeData.email)
        binding.editRole.setText(employeeData.role.name)
        binding.editHiringDate.setText(dateFormattedToView(employeeData.hiringDate))
    }

}