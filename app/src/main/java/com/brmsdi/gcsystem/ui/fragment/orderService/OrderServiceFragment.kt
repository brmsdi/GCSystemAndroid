package com.brmsdi.gcsystem.ui.fragment.orderService

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.brmsdi.gcsystem.R
import com.brmsdi.gcsystem.data.adapter.PagingDataOrderService
import com.brmsdi.gcsystem.data.constants.Constant
import com.brmsdi.gcsystem.data.listeners.ItemRecyclerClickListener
import com.brmsdi.gcsystem.data.listeners.OnSearchViewListener
import com.brmsdi.gcsystem.data.model.OrderService
import com.brmsdi.gcsystem.databinding.FragmentOrderServiceBinding
import com.brmsdi.gcsystem.ui.activity.detailOrderService.DetailOrderServiceActivity
import com.brmsdi.gcsystem.ui.utils.ProgressBarOnApp
import com.brmsdi.gcsystem.ui.utils.TextUtils.Companion.displayMessage
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class OrderServiceFragment : Fragment(), ItemRecyclerClickListener<OrderService>, ProgressBarOnApp {
    private lateinit var binding: FragmentOrderServiceBinding
    private val viewModel by viewModel<OrderServiceViewModel>()
    private lateinit var adapter: PagingDataOrderService
    private var search = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentOrderServiceBinding.inflate(inflater, container, false)
        binding.fragmentOrderService.isVisible = true
        binding.recyclerOrderService.layoutManager = LinearLayoutManager(context)
        adapter = PagingDataOrderService(this)
        binding.recyclerOrderService.adapter = adapter
        observe()
        addAction()
        viewModel.load(search)
        return binding.root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        val onSearchViewListener = requireActivity() as OnSearchViewListener
        onSearchViewListener.addSearchListener(addSearchEventListener())
    }

    override fun onClick(model: OrderService) {
        detailsOrderService(model)
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
        viewModel.loadData.observe(this.viewLifecycleOwner) {
            adapter.submitData(viewLifecycleOwner.lifecycle, it)
        }
        viewModel.error.observe(this.viewLifecycleOwner) {
            if (!it.status()) {
                showOrHideView(binding.progressOrderService.root, false)
                binding.textInfo.text = it.message()
                showOrHideView(binding.textInfo, true)
                displayMessage(this.requireContext(), it.message())
            }
        }
    }

    private fun addAction() {
        adapter.addOnPagesUpdatedListener {
            verifyData()
        }
    }

    private fun verifyData() {
        showOrHideView(binding.progressOrderService.root, false)
        if (adapter.itemCount <= 0) {
            binding.textInfo.text = getString(R.string.search_is_empty)
            binding.textInfo.isVisible = true
            return
        }
        binding.textInfo.isVisible = false
    }

    private fun addSearchEventListener(): SearchView.OnQueryTextListener {
        return object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                search = query
                if (search.isNotEmpty()) viewModel.load(search)
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                search = newText
                if (search.isEmpty()) viewModel.load(search)
                return true
            }
        }
    }

    private val startForResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            if (result.resultCode == Activity.RESULT_OK || result.resultCode == Activity.RESULT_CANCELED) {
                adapter.refresh()
            }
        }

    private fun detailsOrderService(orderService: OrderService) {
        val bundle = Bundle()
        bundle.putInt(Constant.OS.ID_ORDER_SERVICE, orderService.id)
        val intent = Intent(this.requireContext(), DetailOrderServiceActivity::class.java)
        intent.putExtras(bundle)
        startForResult.launch(intent)
    }
}