package com.brmsdi.gcsystem.ui.fragment.contract

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.brmsdi.gcsystem.data.adapter.AdapterContract
import com.brmsdi.gcsystem.data.constants.Constant.CONTRACT.CONTRACT_DATA
import com.brmsdi.gcsystem.data.listeners.ItemRecyclerClickListener
import com.brmsdi.gcsystem.data.model.Contract
import com.brmsdi.gcsystem.databinding.FragmentContractBinding
import com.brmsdi.gcsystem.ui.activity.detailContract.DetailContractActivity
import com.brmsdi.gcsystem.ui.utils.NumberUtils.Companion.getSystemLocale
import com.brmsdi.gcsystem.ui.utils.TextUtils
import org.koin.androidx.viewmodel.ext.android.viewModel

class ContractFragment : Fragment(), ItemRecyclerClickListener<Contract> {
    private val viewModel by viewModel<ContractViewModel>()
    private lateinit var binding: FragmentContractBinding
    private lateinit var adapter: AdapterContract

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentContractBinding.inflate(layoutInflater, container, false)
        adapter = AdapterContract(locale = getSystemLocale(this.requireContext()), listener = this)
        binding.recyclerContracts.layoutManager = LinearLayoutManager(this.requireContext())
        binding.recyclerContracts.adapter = adapter
        loadData()
        observe()
        return binding.root
    }

    override fun onClick(model: Contract) {
        initDetailsContract(model)
    }

    private fun observe() {
        viewModel.contracts.observe(this.viewLifecycleOwner) {
            adapter.updateAll(it.content)
        }

        viewModel.error.observe(this.viewLifecycleOwner) {
            TextUtils.displayMessage(this.requireContext(), it.message())
        }
    }

    private fun loadData() {
        viewModel.loadContract()
    }

    private fun initDetailsContract(contract: Contract) {
        val bundle = Bundle()
        bundle.putParcelable(CONTRACT_DATA, contract)
        val intent = Intent(this.requireContext(), DetailContractActivity::class.java)
        intent.putExtra(CONTRACT_DATA, bundle)
        startActivity(intent)
    }
}