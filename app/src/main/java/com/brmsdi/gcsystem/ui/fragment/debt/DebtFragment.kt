package com.brmsdi.gcsystem.ui.fragment.debt

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.brmsdi.gcsystem.data.adapter.AdapterDebts
import com.brmsdi.gcsystem.databinding.FragmentDebtBinding
import com.brmsdi.gcsystem.ui.utils.NumberUtils.Companion.getSystemLocale
import com.brmsdi.gcsystem.ui.utils.TextUtils.Companion.displayMessage
import org.koin.androidx.viewmodel.ext.android.viewModel

class DebtFragment : Fragment() {
    private val viewModel by viewModel<DebtViewModel>()
    private lateinit var binding: FragmentDebtBinding
    private lateinit var adapter: AdapterDebts
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDebtBinding.inflate(inflater, container, false)
        adapter = AdapterDebts(getSystemLocale(this.requireContext()))
        binding.recyclerDebts.layoutManager = LinearLayoutManager(this.requireContext())
        binding.recyclerDebts.adapter = adapter
        loadData()
        observe()
        return binding.root
    }

    private fun observe() {
        viewModel.debts.observe(this.viewLifecycleOwner) {
            adapter.updateAll(it.content)
        }

        viewModel.error.observe(this.viewLifecycleOwner) {
            displayMessage(this.requireContext(), it.message())
        }
    }

    private fun loadData() {
        viewModel.loadDebts()
    }
}