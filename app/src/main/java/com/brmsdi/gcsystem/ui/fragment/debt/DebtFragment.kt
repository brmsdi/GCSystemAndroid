package com.brmsdi.gcsystem.ui.fragment.debt

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.brmsdi.gcsystem.data.adapter.AdapterDebts
import com.brmsdi.gcsystem.data.model.Lessee
import com.brmsdi.gcsystem.databinding.FragmentDebtBinding
import com.brmsdi.gcsystem.ui.utils.Mock
import com.brmsdi.gcsystem.ui.utils.NumberUtils.Companion.getSystemLocale

class DebtFragment : Fragment() {
    private lateinit var binding: FragmentDebtBinding
    private lateinit var viewModel: DebtViewModel
    private lateinit var adapter: AdapterDebts
    private lateinit var lessee: Lessee
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDebtBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this)[DebtViewModel::class.java]
        lessee = Mock.lesseeList()[0]
        adapter = AdapterDebts(lessee, getSystemLocale(this.requireContext()))
        binding.recyclerDebts.layoutManager = LinearLayoutManager(this.requireContext())
        binding.recyclerDebts.adapter = adapter
        loadData(lessee)
        return binding.root
    }

    private fun loadData(lessee: Lessee) {
        adapter.updateAll(lessee.debts)
    }
}