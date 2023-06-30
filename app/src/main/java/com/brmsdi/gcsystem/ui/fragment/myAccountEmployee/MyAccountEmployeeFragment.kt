package com.brmsdi.gcsystem.ui.fragment.myAccountEmployee

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.brmsdi.gcsystem.databinding.FragmentMyAccountEmployeeBinding

class MyAccountEmployeeFragment : Fragment() {

    private lateinit var viewModel: MyAccountEmployeeViewModel
    private lateinit var binding: FragmentMyAccountEmployeeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMyAccountEmployeeBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this)[MyAccountEmployeeViewModel::class.java]
        mockData()
        return binding.root
    }

    private fun mockData() {
        binding.editId.setText("1234 (mock)")
        binding.editName.setText("Wisley Bruno Marques França (mock)")
        binding.editCpf.setText("000.000.002-51 (mock)")
        binding.editBirthDate.setText("23/06/2023 (mock)")
        binding.editEmail.setText("srmarquesms@gmail.com (mock)")
        binding.editRule.setText("Pessoa desenvolvedora (mock)")
        binding.editHiringDate.setText("23/06/2023 (mock)")
    }

}