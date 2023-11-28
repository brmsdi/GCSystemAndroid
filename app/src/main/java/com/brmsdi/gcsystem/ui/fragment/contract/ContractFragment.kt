package com.brmsdi.gcsystem.ui.fragment.contract

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.brmsdi.gcsystem.R
import com.brmsdi.gcsystem.data.adapter.PagingDataContractsAdapter
import com.brmsdi.gcsystem.data.constants.Constant.CONTRACT.CONTRACT_ID
import com.brmsdi.gcsystem.data.listeners.ItemRecyclerClickListener
import com.brmsdi.gcsystem.data.model.Contract
import com.brmsdi.gcsystem.databinding.FragmentContractBinding
import com.brmsdi.gcsystem.ui.activity.detailContract.DetailContractActivity
import com.brmsdi.gcsystem.ui.utils.ColorUtils
import com.brmsdi.gcsystem.ui.utils.NumberUtils.Companion.getSystemLocale
import com.brmsdi.gcsystem.ui.utils.ProgressBarOnApp
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class ContractFragment : Fragment(), ItemRecyclerClickListener<Contract>, ProgressBarOnApp {
    private val viewModel by viewModel<ContractViewModel>()
    private lateinit var binding: FragmentContractBinding
    private lateinit var adapter: PagingDataContractsAdapter
    private var search = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentContractBinding.inflate(layoutInflater, container, false)
        adapter = PagingDataContractsAdapter(locale = getSystemLocale(this.requireContext()), listener = this)
        binding.recyclerContracts.layoutManager = LinearLayoutManager(this.requireContext())
        binding.recyclerContracts.adapter = adapter
        binding.progressContracts.root.visibility = View.VISIBLE
        observe()
        addAction()
        ColorUtils.getColorSwipeRefreshLayout(resources, binding.swipeRefreshLayout)
        viewModel.load(search)
        return binding.root
    }

    override fun onClick(model: Contract) {
        initDetailsContract(model)
    }

    private fun observe() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                adapter.loadStateFlow.collect {
                    binding.prependProgress.isVisible = it.source.prepend is LoadState.Loading
                    binding.appendProgress.isVisible = it.source.append is LoadState.Loading
                }
            }
        }
        viewModel.loadData.observe(viewLifecycleOwner) {
            adapter.submitData(viewLifecycleOwner.lifecycle, it)
        }
    }

    private fun initDetailsContract(contract: Contract) {
        val bundle = Bundle()
        bundle.putInt(CONTRACT_ID, contract.id)
        val intent = Intent(this.requireContext(), DetailContractActivity::class.java)
        intent.putExtras(bundle)
        startActivity(intent)
    }

    private fun addAction() {
        adapter.addOnPagesUpdatedListener {
            verifyData()
        }
        binding.swipeRefreshLayout.setOnRefreshListener {
            viewModel.load(search)
        }
    }

    private fun verifyData() {
        removeLoadingComponent()
        if (adapter.itemCount <= 0) {
            binding.textInfo.text = getString(R.string.search_is_empty)
            binding.textInfo.isVisible = true
            return
        }
        binding.textInfo.isVisible = false
    }

    private fun removeLoadingComponent() {
        showOrHideView(binding.progressContracts.root, false)
        binding.swipeRefreshLayout.isRefreshing = false
    }
}