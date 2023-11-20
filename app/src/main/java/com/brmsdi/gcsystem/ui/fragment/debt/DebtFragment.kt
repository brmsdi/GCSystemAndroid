package com.brmsdi.gcsystem.ui.fragment.debt

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.paging.LoadState.Loading
import androidx.recyclerview.widget.LinearLayoutManager
import com.brmsdi.gcsystem.data.adapter.PagingDataDebtsAdapter
import com.brmsdi.gcsystem.databinding.FragmentDebtBinding
import com.brmsdi.gcsystem.ui.utils.NumberUtils.Companion.getSystemLocale
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class DebtFragment : Fragment() {
    private val viewModel by viewModel<DebtViewModel>()
    private lateinit var binding: FragmentDebtBinding
    private lateinit var adapter: PagingDataDebtsAdapter
    private var search = ""
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDebtBinding.inflate(inflater, container, false)
        adapter = PagingDataDebtsAdapter(getSystemLocale(this.requireContext()))
        binding.recyclerDebts.layoutManager = LinearLayoutManager(this.requireContext())
        binding.recyclerDebts.adapter = adapter
        observe()
        viewModel.load(search)
        return binding.root
    }

    private fun observe() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                adapter.loadStateFlow.collect {
                    binding.prependProgress.isVisible = it.source.prepend is Loading
                    binding.appendProgress.isVisible = it.source.append is Loading
                }
            }
        }
        viewModel.loadData.observe(viewLifecycleOwner) {
            adapter.submitData(lifecycle, it)
        }
    }
}