package com.brmsdi.gcsystem.ui.fragment.debt

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.paging.map
import androidx.recyclerview.widget.LinearLayoutManager
import com.brmsdi.gcsystem.data.adapter.PagingDataDebtsAdapter
import com.brmsdi.gcsystem.databinding.FragmentDebtBinding
import com.brmsdi.gcsystem.ui.utils.NumberUtils.Companion.getSystemLocale
import com.brmsdi.gcsystem.ui.utils.TextUtils.Companion.displayMessage
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class DebtFragment : Fragment() {
    private val viewModel by viewModel<DebtViewModel>()
    private lateinit var binding: FragmentDebtBinding
    private lateinit var adapter: PagingDataDebtsAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDebtBinding.inflate(inflater, container, false)
        val items = viewModel.items
        adapter = PagingDataDebtsAdapter(getSystemLocale(this.requireContext()))
        binding.recyclerDebts.layoutManager = LinearLayoutManager(this.requireContext())
        binding.recyclerDebts.adapter = adapter

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                items.collectLatest {
                    println("RETORNO")
                    println("" + it.toString() )
                    it.map { r -> println(r.toString()) }
                    adapter.submitData(it)
                }
            }
        }

        //loadData()
        // observe()

        return binding.root
    }

    private fun observe() {
        viewModel.debts.observe(this.viewLifecycleOwner) {
            //adapter.updateAll(it.content)
        }

        viewModel.error.observe(this.viewLifecycleOwner) {
            displayMessage(this.requireContext(), it.message())
        }
    }

    private fun loadData() {
        viewModel.loadDebts()
    }
}