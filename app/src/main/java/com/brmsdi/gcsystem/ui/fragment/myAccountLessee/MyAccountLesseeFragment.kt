package com.brmsdi.gcsystem.ui.fragment.myAccountLessee

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.brmsdi.gcsystem.databinding.FragmentMyAccountLesseeBinding

class MyAccountLesseeFragment : Fragment() {

    private lateinit var viewModel: MyAccountLesseeViewModel
    private lateinit var binding: FragmentMyAccountLesseeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMyAccountLesseeBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this)[MyAccountLesseeViewModel::class.java]
        mockData()
        return binding.root
    }

    private fun mockData() {
        binding.editId.setText("1234 (mock)")
        binding.editName.setText("Wisley Bruno Marques França (mock)")
        binding.editCpf.setText("000.000.002-51 (mock)")
        binding.editBirthDate.setText("23/06/2023 (mock)")
        binding.editEmail.setText("srmarquesms@gmail.com (mock)")
        binding.editContact.setText("(92) 99107-1491")
    }
}